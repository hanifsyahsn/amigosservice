package com.amigoscode.clients.notification;

import com.amigoscode.clients.notification.request.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification", url = "${clients.notification.url}")
public interface NotificationDescriptor {
    @PostMapping("api/v1/notification/send-notification")
    void sendNotification(@RequestBody NotificationRequest notificationRequest);
}
