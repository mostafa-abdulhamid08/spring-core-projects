package com.shop.hub.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("card")
@Profile("prod")
public class PaymobCardGateway implements PaymentGateway {

    private final String apiKey;

    public PaymobCardGateway(@Value("${paymob.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void pay(String orderId, int amount) {
        System.out.printf(Locale.US, "[PAYMOB] charged %,d EGP for %s (key %s)%n", amount, orderId, masked());
    }

    @Override
    public void refund(String orderId, int amount) {
        System.out.printf(Locale.US, "[PAYMOB] refunded %,d EGP for %s (key %s)%n", amount, orderId, masked());
    }

    private String masked() {
        return "****" + apiKey.substring(Math.max(0, apiKey.length() - 4));
    }
}