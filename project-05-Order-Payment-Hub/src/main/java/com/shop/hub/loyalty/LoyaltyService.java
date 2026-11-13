package com.shop.hub.loyalty;


import com.shop.hub.order.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("loyalty")
public class LoyaltyService {
    public void award(Order order) {
        int points = order.total() / 100;
        System.out.println("[LOYALTY] +" + points + " points for " + order.id());
    }
}
