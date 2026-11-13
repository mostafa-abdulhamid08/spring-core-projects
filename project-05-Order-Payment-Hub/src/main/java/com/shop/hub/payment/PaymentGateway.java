package com.shop.hub.payment;

public interface PaymentGateway {
    void pay(String orderId, int amount);
    void refund(String orderId, int amount);
}
