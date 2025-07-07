package com.upm.pfg.stirshakenplatform.wc.restcontrollers;

import com.upm.pfg.stirshakenplatform.wc.messages.SigningRequest;
import com.upm.pfg.stirshakenplatform.wc.messages.VerificationRequest;
import com.upm.pfg.stirshakenplatform.wc.messages.VerificationResponse;
import com.upm.pfg.stirshakenplatform.wc.services.WcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/STIRSHAKEN")
public class RequestController {

    @Autowired
    private WcService wcService;

    @GetMapping("/signing")
    public ResponseEntity<String> signing(SigningRequest signingRequest) {

        System.out.println("Received signing request: " + signingRequest);
        String response = wcService.sign("http://localhost:8081/test/signing", signingRequest);
        System.out.println("Signing response: " + response);
        return ResponseEntity.ok(response);
    }


    public String verifing(VerificationRequest verificationRequest) {

        System.out.println("Received verifing request: " + verificationRequest);
        String response = wcService.verify("http://localhost:8081/STIRSHAKEN/verify", verificationRequest);
        System.out.println("verifing response: " + response);
        return response;
    }
}