package com.shop.hub;

import com.shop.hub.cart.Cart;
import com.shop.hub.order.CheckoutService;
import com.shop.hub.order.RefundService;
import com.shop.hub.ui.ConsoleMenu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StoreApp {

    public static void main(String[] args) {
        System.out.println("Order & Payment Hub starting...");
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            if (args.length > 0 && args[0].equals("menu")) {
                context.getBean(ConsoleMenu.class).run();
            } else {
                runScenario(context);
            }
        }
        System.out.println("Order & Payment Hub stopped");
    }

    private static void runScenario(ApplicationContext context) {
        var checkout = context.getBean(CheckoutService.class);

        var cart1 = new Cart();
        cart1.add("HEA-001", 1);
        cart1.add("MUG-001", 2);
        attempt(() -> checkout.placeOrder(cart1, "Alexandria", "card"));

        var cart2 = new Cart();
        cart2.add("TSH-001", 3);
        attempt(() -> checkout.placeOrder(cart2, "Giza", "cod"));

        var cart3 = new Cart();
        cart3.add("LAP-001", 1);
        attempt(() -> checkout.placeOrder(cart3, "Mansoura", "fawry"));

        var cart4 = new Cart();
        cart4.add("MUG-001", 1);
        attempt(() -> checkout.placeOrder(cart4, "Tanta", "cod"));

        var cart5 = new Cart();
        cart5.add("MOU-001", 1);
        attempt(() -> checkout.placeOrder(cart5, "Tanta", "visa"));

        attempt(() -> checkout.placeOrder(new Cart(), "Cairo", "cod"));

        var refunds = context.getBean(RefundService.class);
        attempt(() -> refunds.refund("ORD-1001"));
        attempt(() -> refunds.refund("ORD-1001"));
        attempt(() -> refunds.refund("ORD-9999"));
    }

    private static void attempt(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("REJECTED: " + e.getMessage());
        }
    }
}