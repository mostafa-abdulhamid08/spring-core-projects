package com.shop.hub.pricing;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaxCalculator {
    private final int taxRate;

    public TaxCalculator(@Value("${shop.tax-rate}") int taxRate) {
        this.taxRate = taxRate;
    }

    public int taxOn(int amount) {
        return amount * taxRate / 100;
    }
}
