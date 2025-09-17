package com.amigoscode.notification.service;

import com.amigoscode.clients.notification.request.NotificationRequest;
import com.amigoscode.notification.model.Notification;
import com.amigoscode.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record NotificationService(NotificationRepository notificationRepository) {
    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(Notification
                .builder()
                .toCustomerEmail(notificationRequest.getCustomerEmail())
                .toCustomerId(notificationRequest.getCustomerId())
                .sender("amigoscode")
                .message(notificationRequest.getMessage())
                .sentAt(LocalDateTime.now())
                .build());
    }
}
