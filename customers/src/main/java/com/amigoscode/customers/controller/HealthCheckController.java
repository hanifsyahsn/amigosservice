package com.amigoscode.customers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers/health-check")
public class HealthCheckController {

    BuildProperties buildProperties;

    @GetMapping
    public ResponseEntity healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NO_CONTENT);
        response.put("message", "Service is up");
//        response.put("appVersion", buildProperties.getVersion());
        response.put("timestamp", new Date().getTime());
        return ResponseEntity.ok(response);
    }
}
