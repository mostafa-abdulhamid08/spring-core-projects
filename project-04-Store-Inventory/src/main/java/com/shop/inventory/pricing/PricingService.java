package com.shop.inventory.pricing;


import com.shop.inventory.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    private final DiscountPolicy defaultPolicy;
    private final DiscountPolicy clearancePolicy;

    public PricingService(DiscountPolicy defaultPolicy, @Qualifier("clearance") DiscountPolicy clearancePolicy) {
        this.defaultPolicy = defaultPolicy;
        this.clearancePolicy = clearancePolicy;
    }
    public int finalPrice(Product product) {
        DiscountPolicy policy = product.clearance() ? clearancePolicy : defaultPolicy;
        return policy.apply(product.price());
    }
}
