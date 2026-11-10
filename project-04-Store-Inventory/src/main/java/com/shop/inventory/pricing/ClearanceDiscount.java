package com.shop.inventory.pricing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("clearance")
public class ClearanceDiscount implements DiscountPolicy{

    private static int constant= 30;
    @Override
    public int apply(int price) {
        return price - (price * constant/100);
    }
}
