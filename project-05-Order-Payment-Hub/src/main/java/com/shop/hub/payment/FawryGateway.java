package com.shop.hub.payment;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("fawry")
public class FawryGateway implements PaymentGateway {
    @Override
    public void pay(String orderId, int amount) {
        System.out.printf(Locale.US, "[FAWRY] pay %,d EGP at any Fawry outlet, reference FWR-%s%n", amount, orderId);

    }

    @Override
    public void refund(String orderId, int amount) {
        System.out.printf(Locale.US, "[FAWRY] refunded %,d EGP for %s%n", amount, orderId);

    }
}
