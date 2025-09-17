package com.amigoscode.notification.controller;

import com.amigoscode.clients.notification.request.NotificationRequest;
import com.amigoscode.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@Slf4j
public record NotificationController(NotificationService notificationService) {
    @PostMapping("send-notification")
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("Sending notification:........ {}");
        notificationService.send(notificationRequest);
    }
}
