package com.shop.hub.notification;

import com.shop.external.SmsClient;
import org.springframework.stereotype.Component;

@Component
public class SmsNotifier implements NotificationChannel{
    private final SmsClient smsClient;

    public SmsNotifier(SmsClient smsClient) {
        this.smsClient = smsClient;
    }


    @Override
    public void send(String message) {
        smsClient.send(message);
    }
}
