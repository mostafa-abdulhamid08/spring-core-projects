package com.notificationSystem.services;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("emailNotification")
@Primary
public class EmailNotification implements NotificationService{

    @Override
    public void sendNotification(String message) {
        System.out.println("[EMAIL] Sending: " + message);
    }

    @Override
    public String getType() {
        return "Email";
    }
}
