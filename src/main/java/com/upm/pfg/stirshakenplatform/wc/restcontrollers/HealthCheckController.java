package com.upm.pfg.stirshakenplatform.wc.restcontrollers;

import com.upm.pfg.stirshakenplatform.wc.services.WcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @Autowired
    private WcService wcService;

    @GetMapping("/check")
    public ResponseEntity<String> checkHealth() {
        String response = wcService.checkHealth("http://localhost:8081/__admin/health");
        return ResponseEntity.ok(response);
    }
}