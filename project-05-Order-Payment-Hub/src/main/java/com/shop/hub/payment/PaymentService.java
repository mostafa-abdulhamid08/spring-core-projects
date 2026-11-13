package com.shop.hub.payment;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    private final Map<String, PaymentGateway> gateways;

    public PaymentService(Map<String, PaymentGateway> gateways) {
        this.gateways = gateways;
    }
    public boolean supports(String method) {
        return gateways.containsKey(method);
    }

    public List<String> availableMethods() {
        return gateways.keySet().stream().sorted().toList();
    }

    public void pay(String method, String orderId, int amount) {
        gatewayFor(method).pay(orderId, amount);
    }

    public void refund(String method, String orderId, int amount) {
        gatewayFor(method).refund(orderId, amount);
    }

    public void requireSupported(String method) {
        if (!supports(method)) {
            throw new IllegalArgumentException("unsupported payment method: " + method);
        }
    }

    private PaymentGateway gatewayFor(String method) {
        requireSupported(method);
        return gateways.get(method);
    }
}
