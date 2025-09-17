package com.amigoscode.fraud.descriptor;

import com.amigoscode.clients.fraud.response.FraudCheckHistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient("fraud")
//public interface FraudDescriptor {
//    @GetMapping(path = "api/v1/fraud-check/{customerId}")
//    FraudCheckHistoryResponse isFraudster(@PathVariable("customerId") Integer customerId);
//}
