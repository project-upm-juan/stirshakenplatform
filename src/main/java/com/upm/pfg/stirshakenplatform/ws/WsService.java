package com.upm.pfg.stirshakenplatform.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upm.pfg.stirshakenplatform.database.*;
import com.upm.pfg.stirshakenplatform.logic.Context;
import com.upm.pfg.stirshakenplatform.logic.Logic;
import com.upm.pfg.stirshakenplatform.wc.messages.SigningRequest;
import com.upm.pfg.stirshakenplatform.wc.messages.VerificationRequest;
import com.upm.pfg.stirshakenplatform.wc.restcontrollers.HealthCheckController;
import com.upm.pfg.stirshakenplatform.wc.restcontrollers.RequestController;
import com.upm.pfg.stirshakenplatform.ws.messages.ReceivedInvite;
import com.upm.pfg.stirshakenplatform.ws.messages.RedirectionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WsService {

    @Autowired
    private CountryRepository  countryRepository;

    @Autowired
    private EmergencyNumberRepository emergencyNumberRepository;

    @Autowired
    private PrefixRepository prefixRepository;

    @Autowired
    private SubscriberInfoRepository subscriberInfoRepository;

    @Autowired
    private TrunkGroupRepository trunkGroupRepository;

    @Autowired
    private RequestController requestController;

    @Autowired
    public WsService() {
        // Constructor
    }

    private Logic logic;

    public void init() {
        // Initialization logic
    }

    public void destroy() {
        // Cleanup logic
    }

    public RedirectionAction processInvite(ReceivedInvite invite) throws Exception {
        // Process the invite and return a context
        System.out.println("Processing invite in WsService...");
        String value = invite.getIdentity();
        if (value != null) {
            value = value.trim();
            if(value.isEmpty()) {
                invite.setIdentity(null);
            }
        }


        Logic logic = new Logic(countryRepository, emergencyNumberRepository,
                prefixRepository, subscriberInfoRepository, trunkGroupRepository, requestController);
        Context context = logic.processInvite(invite);
        System.out.println("Context created from invite: " + context.toString());


        // Add logic to process the invite and populate the context
        return logic.getRedirectionAction(context, invite);
    }

}
