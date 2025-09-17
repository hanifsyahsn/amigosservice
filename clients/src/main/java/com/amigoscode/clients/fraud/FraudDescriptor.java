package com.amigoscode.clients.fraud;

import com.amigoscode.clients.fraud.response.FraudCheckHistoryResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fraud", url = "${clients.fraud.url}")
public interface FraudDescriptor {
    @GetMapping(path = "api/v1/fraud-check/{customerId}")
    @LoadBalanced
    FraudCheckHistoryResponse isFraudster(@PathVariable("customerId") Integer customerId);
}
