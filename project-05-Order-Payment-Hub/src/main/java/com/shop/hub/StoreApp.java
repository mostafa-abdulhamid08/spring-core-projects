package com.shop.hub;

import com.shop.hub.cart.Cart;
import com.shop.hub.catalog.ProductRepository;
import com.shop.hub.pricing.ShippingService;
import com.shop.hub.pricing.TaxCalculator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class StoreApp {

    public static void main(String[] args) {
        System.out.println("Order & Payment Hub starting...");

        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("Active profiles: "
                    + Arrays.toString(context.getEnvironment().getActiveProfiles()));

            for (String name : context.getBeanDefinitionNames()) {
                System.out.println("  bean: " + name);
            }
           /* ProductRepository products = context.getBean(ProductRepository.class);   // مؤقت
            products.findAll().forEach(System.out::println);*/

            /*TaxCalculator tax = context.getBean(TaxCalculator.class);   // مؤقت
            System.out.println("tax on 1800 = " + tax.taxOn(1800));
            System.out.println("tax on 25000 = " + tax.taxOn(25000)); */

           /* ShippingService shipping = context.getBean(ShippingService.class);   // مؤقت
            System.out.println("Alexandria/1800  = " + shipping.feeFor("Alexandria", 1800));
            System.out.println("Giza/900         = " + shipping.feeFor("Giza", 900));
            System.out.println("Mansoura/25000   = " + shipping.feeFor("Mansoura", 25000));
            System.out.println("Tanta/150        = " + shipping.feeFor("Tanta", 150));
            System.out.println("Mansoura/150     = " + shipping.feeFor("Mansoura", 150));
            System.out.println("GIZA/900         = " + shipping.feeFor("GIZA", 900));*/
            var cart = new Cart();                 // بـ new، مش من الـ context
            cart.add("HEA-001", 1);
            cart.add("MUG-001", 2);
            cart.add("HEA-001", 1);                // نفس الـ SKU تاني
            System.out.println("cart: " + cart.items());
            System.out.println("empty? " + cart.isEmpty());

            try {
                cart.add("TSH-001", 0);
            } catch (IllegalArgumentException e) {
                System.out.println("rejected: " + e.getMessage());
            }

            cart.items().put("LAP-001", 99);       // تعديل على النسخة بس
            System.out.println("after editing the copy: " + cart.items());

            cart.clear();
            System.out.println("after clear: " + cart.items() + ", empty? " + cart.isEmpty());
        }

        System.out.println("Order & Payment Hub stopped");
    }
}