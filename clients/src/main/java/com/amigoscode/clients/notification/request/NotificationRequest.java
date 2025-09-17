package com.amigoscode.clients.notification.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NotificationRequest {
    private Integer customerId;
    private String customerEmail;
    private String message;
}
