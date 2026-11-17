package com.shop.engine.pricing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FlashDiscount {

    private final int percent;

    public FlashDiscount(@Value("${flash.discount-percent}") int percent) {
        this.percent = percent;
    }

    public int discountOn(int amount) {
        return amount * percent / 100;
    }
}