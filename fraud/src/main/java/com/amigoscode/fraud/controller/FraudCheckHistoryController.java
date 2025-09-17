package com.amigoscode.fraud.controller;

import com.amigoscode.clients.fraud.response.FraudCheckHistoryResponse;
import com.amigoscode.fraud.service.FraudCheckHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudCheckHistoryController(FraudCheckHistoryService fraudCheckHistoryService) {
    @GetMapping(path = "{customerId}")
    public FraudCheckHistoryResponse isFraudster(@PathVariable Integer customerId) {
        Boolean isFraudulentCustomer = fraudCheckHistoryService.isFraudCustomer(customerId);
        return new FraudCheckHistoryResponse(isFraudulentCustomer);
    }
}
