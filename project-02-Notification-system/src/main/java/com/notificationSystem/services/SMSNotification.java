package com.notificationSystem.services;

import org.springframework.stereotype.Component;

@Component("smsNotification")
public class SMSNotification implements  NotificationService{
    @Override
    public void sendNotification(String message) {
        System.out.println("[SMS] Sending: " + message);

    }

    @Override
    public String getType() {
        return "SMS";
    }
}
