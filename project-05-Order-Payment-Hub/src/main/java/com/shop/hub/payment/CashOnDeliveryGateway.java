package com.shop.hub.payment;


import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("cod")
public class CashOnDeliveryGateway implements PaymentGateway{
    @Override
    public void pay(String orderId, int amount) {
        System.out.printf(Locale.US, "[COD] collect %,d EGP on delivery of %s%n", amount, orderId);

    }

    @Override
    public void refund(String orderId, int amount) {
        System.out.printf(Locale.US, "[COD] cash refund of %,d EGP for %s%n", amount, orderId);

    }
}
