package com.upm.pfg.stirshakenplatform.ws;

import ch.qos.logback.core.CoreConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upm.pfg.stirshakenplatform.database.CountryRepository;
import com.upm.pfg.stirshakenplatform.logic.Context;
import com.upm.pfg.stirshakenplatform.ws.messages.ReceivedInvite;
import com.upm.pfg.stirshakenplatform.ws.messages.RedirectionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stsh/v1/ws")
@Validated
public class WsController {

    @Autowired
    private WsService wsService;

    @Autowired
    private CountryRepository countryRepository;

    @PostMapping(value = "/invite", consumes = {"application/json;charset=UTF-8"})
    RedirectionAction processInvite(@RequestBody ReceivedInvite receivedInvite) throws Throwable {
        try {
            System.out.println("Processing invite...");
            RedirectionAction redirectionAction = wsService.processInvite(receivedInvite);
            System.out.println("RedirectionAction: " + redirectionAction.toString());

            return redirectionAction;
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el evento: " + e.getMessage(), e);
        }
    }
}
