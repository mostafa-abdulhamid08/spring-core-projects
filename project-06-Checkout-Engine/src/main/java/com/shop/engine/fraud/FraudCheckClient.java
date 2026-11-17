package com.shop.engine.fraud;

import com.shop.engine.invoice.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class FraudCheckClient {

    public FraudCheckClient(@Value("${fraud.host}") String host,
                            @Value("${fraud.startup-ms}") int startupMs) {
        System.out.println("[fraud] connecting to " + host + " ...");
        try {
            Thread.sleep(startupMs);                 // تشغيل بطيء (محاكاة)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[fraud] ready");
    }

    public boolean approve(Invoice invoice) {
        return true;                                 // محاكاة: كله بيعدّي
    }
}