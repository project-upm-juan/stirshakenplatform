package com.upm.pfg.stirshakenplatform.wc.services;

import com.upm.pfg.stirshakenplatform.wc.messages.SigningRequest;
import com.upm.pfg.stirshakenplatform.wc.messages.VerificationRequest;
import com.upm.pfg.stirshakenplatform.wc.messages.VerificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WcService {

    @Autowired
    private RestTemplate restTemplate;

    public String checkHealth(String url) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return "Error al realizar la solicitud: " + e.getMessage();
        }
    }

    public String sign(String url, SigningRequest signingRequest) {

        try {
            System.out.println("Enviando solicitud de firma a: " + url);
            return restTemplate.postForObject(url, signingRequest, String.class);
        } catch (Exception e) {
            return "Error al enviar la solicitud: " + e.getMessage();
        }
    }

    public String verify(String url, VerificationRequest verificationRequest) {

        try {
            System.out.println("Enviando solicitud de firma a: " + url);
            return restTemplate.postForObject(url, verificationRequest, String.class);
        } catch (Exception e) {
            return "Error al enviar la solicitud: " + e.getMessage();
        }
    }
}