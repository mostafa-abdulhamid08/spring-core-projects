package com.shop.inventory.pricing;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class NoDiscount implements DiscountPolicy{
    @Override
    public int apply(int price) {
        return price;
    }
}
