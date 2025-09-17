package com.amigoscode.fraud.service;

import com.amigoscode.fraud.model.FraudCheckHistory;
import com.amigoscode.fraud.repository.FraudCheckHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public record FraudCheckHistoryService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {
    public boolean isFraudCustomer(Integer customerId) {
        log.info("checking fraud");
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .build();
        fraudCheckHistoryRepository.save(fraudCheckHistory);
        return false;
    }
}
