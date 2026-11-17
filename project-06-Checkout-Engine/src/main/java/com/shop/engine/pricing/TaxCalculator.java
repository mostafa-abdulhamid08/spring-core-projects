package com.shop.engine.pricing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaxCalculator {

    private final int rate;

    public TaxCalculator(@Value("${shop.tax-rate}") int rate) {
        this.rate = rate;
    }

    public int taxOn(int amount) {
        return amount * rate / 100;
    }
}