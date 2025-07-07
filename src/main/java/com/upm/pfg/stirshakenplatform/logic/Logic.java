package com.upm.pfg.stirshakenplatform.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upm.pfg.stirshakenplatform.database.*;
import com.upm.pfg.stirshakenplatform.wc.messages.SigningRequest;
import com.upm.pfg.stirshakenplatform.wc.messages.VerificationRequest;
import com.upm.pfg.stirshakenplatform.wc.restcontrollers.RequestController;
import com.upm.pfg.stirshakenplatform.ws.messages.ReceivedInvite;
import com.upm.pfg.stirshakenplatform.logic.Context.OperatorType;
import com.upm.pfg.stirshakenplatform.logic.Context.AttestationLevel;
import com.upm.pfg.stirshakenplatform.logic.Context.logicAction;
import com.upm.pfg.stirshakenplatform.ws.messages.RedirectionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logic {
    // This class is a placeholder for the logic layer of the STIR/SHAKEN platform.
    // It can be used to implement business logic, data processing, and other operations
    // that are not directly related to the web service or database layers.
    private CountryRepository countryRepository;
    private EmergencyNumberRepository emergencyNumberRepository;
    private PrefixRepository prefixRepository;
    private SubscriberInfoRepository subscriberInfoRepository;
    private TrunkGroupRepository trunkGroupRepository;


    private RequestController requestController;

    public Logic(CountryRepository countryRepository, EmergencyNumberRepository emergencyNumberRepository,
                 PrefixRepository prefixRepository, SubscriberInfoRepository subscriberInfoRepository,
                 TrunkGroupRepository trunkGroupRepository, RequestController requestController) {
        this.countryRepository = countryRepository;
        this.emergencyNumberRepository = emergencyNumberRepository;
        this.prefixRepository = prefixRepository;
        this.subscriberInfoRepository = subscriberInfoRepository;
        this.trunkGroupRepository = trunkGroupRepository;
        this.requestController = requestController;
        System.out.println("Logic initialized with repositories.");
    }

    private Context.OperatorType operatorType;

    // Add methods for business logic here
    public Context processInvite(ReceivedInvite invite) throws Exception {
        // Process the invite and return a context
        System.out.println("Processing invite in Logic..." + invite);
        Context context = createContextFromInvite(invite);

        if (context == null) {
            System.out.println("No context created from invite, returning null.");
            return null; // No context created, return null
        }

        return processContext(context);
    }

    public RedirectionAction getRedirectionAction(Context context, ReceivedInvite invite) throws Exception {
        // Get the redirection action based on the context
        System.out.println("Getting redirection action for context: " + context.toString());
        if (context.getLogicAction().equals(Context.logicAction.SIGN)) {
            System.out.println("Context action is SIGN, proceeding with signing.");

            String pOriginationId = context.getSubscriberInfo() != null
                    ? context.getSubscriberInfo().getPOriginationId() : null;

            System.out.println(context.getAttestationLevel());
            SigningRequest signingRequest = new SigningRequest(context.getSubscriberInfo().getAttestationLevel(),
                    context.getToNumber(), pOriginationId,
                    context.getFromNumber(), System.currentTimeMillis() / 1000L);


            String identity = extractIdentity(requestController.signing(signingRequest));
            RedirectionAction redirectionAction = new RedirectionAction();

            redirectionAction.setResponseCode(200);
            redirectionAction.setResponseText("Accepted for signing");
            redirectionAction.setIdentity(identity);

            Map<String, String> tgrpFromUri = getUserAndTrunkGroup(invite.getReqUri());
            String user = tgrpFromUri.keySet().iterator().next();

            Map<String, String> tgrpHostMap = getUserAndTrunkGroup(invite.getContact());
            String tgrpHost = tgrpHostMap.values().iterator().next();


            redirectionAction.setContact(generateSipString(user, tgrpHost));

            return redirectionAction;

        } else if (context.getLogicAction().equals(Context.logicAction.VERIFY)) {
            VerificationRequest verificationRequest = new VerificationRequest(
                    context.getFromNumber(),
                    context.getToNumber(),
                    context.getIdentity(),
                    System.currentTimeMillis() / 1000L);

            RedirectionAction redirectionAction = new RedirectionAction();

            redirectionAction.setResponseCode(300);
            redirectionAction.setResponseText("Multiple choices for verification");

            Map<String, String> tgrpFromUri = getUserAndTrunkGroup(invite.getReqUri());
            String user = tgrpFromUri.keySet().iterator().next();

            Map<String, String> tgrpHostMap = getUserAndTrunkGroup(invite.getContact());
            String tgrpHost = tgrpHostMap.values().iterator().next();


            redirectionAction.setContact(generateValidityString(user, tgrpHost, requestController.verifing(verificationRequest)));


            return redirectionAction;
        }

        return null;
    }


    private Context processContext(Context context) {
        // Process the context and return a decision

        System.out.println("Processing context: " + context.toString());

        if (context.isEmergencyCall()) {
            System.out.println("Emergency call detected. Decision: SIGN");
            context.setLogicAction(logicAction.CONTINUE);

            return context;
        }


        if (context.getOperatorType().equals(OperatorType.ORIGINATING)) {

            if (context.isAbonado() && context.isAuthenticated()) {
                context.setAttestationLevel(AttestationLevel.A);
            }
            if (!context.isAbonado() && context.isAuthenticated()) {
                context.setAttestationLevel(AttestationLevel.B);
            }
            if (!context.isAbonado() && !context.isAuthenticated()) {
                context.setAttestationLevel(AttestationLevel.C);
            }

            context.setLogicAction(logicAction.SIGN);

        } else if (context.getOperatorType().equals(OperatorType.TERMINATING)) {
            if (context.getIdentity() == null) {
                System.out.println("No identity found for terminating trunk group. Decision: DROP");
                context.setLogicAction(logicAction.DROP);
            } else {
                context.setLogicAction(logicAction.VERIFY);
            }

        } else if (context.getOperatorType().equals(OperatorType.TRANSIT)) {
            context.setLogicAction(logicAction.CONTINUE);
        }

        if (context.getFromCountry() == null || context.getToCountry() == null) {
            System.out.println("No country information found. Decision: DROP");
            context.setLogicAction(logicAction.DROP);
            return context;
        }
        System.out.println("Processed context: " + context.toString());
        return context;
    }


    private Context createContextFromInvite(ReceivedInvite invite) {
        // Create a new context from the received invite

        System.out.println("Creating context from invite: " + invite.toString());
        System.out.println("From URI: " + invite.getReqUri());
        System.out.println("Contact: " + invite.getContact());
        // trgrp from URI is from field TO.
        Map<String, String> tgrpFromUri = getUserAndTrunkGroup(invite.getReqUri());

        // trgrp from URI is from field FROM.
        Map<String, String> tgrpContact = getUserAndTrunkGroup(invite.getContact());

        Map<String, String> from = getUserAndTrunkGroup(invite.getFrom());
        Map<String, String> to = getUserAndTrunkGroup(invite.getTo());
        boolean isAuthenticated = false;
        if (invite.getPAssertedIdentity() == null || invite.getPAssertedIdentity().isEmpty()){
            System.out.println("P-Asserted Identity is empty or null, skipping authentication check.");
        } else {
            Map<String, String> pAssertedIdentityMap = getUserAndTrunkGroup(invite.getPAssertedIdentity());

            if (!pAssertedIdentityMap.isEmpty()) {
                String assertedIdentity = pAssertedIdentityMap.values().iterator().next();
                if(assertedIdentity.equals("mysupplier.com")) {
                    isAuthenticated = true;
                }
            }
        }



        String fromNumber = "";
        String toNumber = "";
        OperatorType operatorType = OperatorType.ORIGINATING;

        TrunkGroup trunkGroupFromUriEntity;
        TrunkGroup trunkGroupContactEntity;
        if (tgrpFromUri.isEmpty() || tgrpContact.isEmpty()) {
            return null; // No trunk group information found

        } else {
            String trunkGroup = tgrpFromUri.values().iterator().next();
            trunkGroupFromUriEntity = trunkGroupRepository.findByName(trunkGroup);

            trunkGroup = tgrpContact.values().iterator().next();
            trunkGroupContactEntity = trunkGroupRepository.findByName(trunkGroup);

            System.out.println("Trunk Group From URI: " + trunkGroupFromUriEntity);
            System.out.println("Trunk Group Contact: " + trunkGroupContactEntity);
            if (trunkGroupFromUriEntity == null && trunkGroupContactEntity == null) {
                operatorType = OperatorType.TRANSIT;
            }

            if (trunkGroupContactEntity != null && trunkGroupFromUriEntity == null) {
                operatorType = OperatorType.ORIGINATING;
            }

            if (trunkGroupContactEntity == null && trunkGroupFromUriEntity != null) {
                operatorType = OperatorType.TERMINATING;
            }

        }


        if (!from.isEmpty()) {
            fromNumber = from.keySet().iterator().next();
        } else {
            System.out.println("No field from information found in FROM");
        }

        if (!to.isEmpty()) {
            toNumber = to.keySet().iterator().next();
            System.out.println("To number: " + toNumber);

        } else {
            System.out.println("No field to information found in TO");
        }

        String toPrefix = toNumber.substring(0, Math.min(2, toNumber.length()));
        String fromPrefix = fromNumber.substring(0, Math.min(2, fromNumber.length()));


        Prefix prefixToEntity = prefixRepository.findByPrefix(toPrefix);
        Country toCountry = null;
        if(prefixToEntity!= null) {
            toCountry = countryRepository.findByCountryId(prefixToEntity.getCountryId());
        }

        Prefix prefixFromEntity = prefixRepository.findByPrefix(fromPrefix);
        Country fromCountry = null;
        if(prefixFromEntity!= null) {
            fromCountry = countryRepository.findByCountryId(prefixFromEntity.getCountryId());
        }


        SubscriberInfo subscriberInfo = subscriberInfoRepository.findByNumber(fromNumber);

        boolean abonado = subscriberInfo != null;

        EmergencyNumber emergencyNumber = emergencyNumberRepository.findByEmNumber(fromCountry.getCountryId(), toNumber);

        if(emergencyNumber!= null) {
            toCountry = countryRepository.findByCountryId(emergencyNumber.getCountryId());
        }
        boolean isEmNumber = emergencyNumber != null;

        Context context = new Context(invite, fromNumber, toNumber,
                fromCountry, toCountry, subscriberInfo, isEmNumber,
                invite.getCallId(), operatorType, abonado, isAuthenticated);

        if (invite.getIdentity() != null) {
            context.setIdentity(invite.getIdentity());
        }

        return context;
    }

    public Map<String, String> getUserAndTrunkGroup(String sipString) {
        // Logic to extract trunk group from URI
        System.out.println(sipString);
        String regex = "sip:([^@]+)@([\\w\\.]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sipString);

        Map<String, String> data = new HashMap<>();
        if (matcher.find()) {
            String phoneNumber = matcher.group(1);
            String ipAddress = matcher.group(2);

            data.put(phoneNumber, ipAddress);
        } else {
            System.out.println("No se pudo extraer la información.");
        }
        return data;
    }

    private static String extractIdentity(ResponseEntity<String> responseEntity) throws Exception {
        String jsonResponse = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        return rootNode.get("identity").asText();
    }

    private static String extractValidation(String responseEntity) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseEntity);
        return rootNode.get("validity").asText();
    }

    private static String generateSipString(String user, String tgrpHost) {
        return "<sip:" + user + "@" + tgrpHost + ">";
    }

    private static String generateValidityString(String user, String tgrpHost, String validity) {
        return generateSipString(user, tgrpHost) + ";validity=" + validity;
    }
}
