package com.shop.hub.payment;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("card")
@Profile("dev")
public class FakeCardGateway implements PaymentGateway {
    @Override
    public void pay(String orderId, int amount) {
        System.out.printf(Locale.US, "[FAKE CARD] charged %,d EGP for %s%n", amount, orderId);

    }

    @Override
    public void refund(String orderId, int amount) {
        System.out.printf(Locale.US, "[FAKE CARD] refunded %,d EGP for %s%n", amount, orderId);

    }
}
