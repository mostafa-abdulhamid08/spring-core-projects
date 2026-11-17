package com.shop.engine;

import com.shop.engine.catalog.ProductRepository;
import com.shop.engine.inventory.InventoryService;
import com.shop.engine.invoice.Invoice;
import com.shop.engine.invoice.InvoiceService;
import com.shop.engine.order.CheckoutService;
import com.shop.engine.pricing.FlashDiscount;
import com.shop.engine.pricing.TaxCalculator;
import com.shop.engine.sale.FlashSale;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;

public class EngineApp {

    public static void main(String[] args) throws Exception {
        System.out.println("Checkout Engine starting...");
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            var sale = context.getBean(FlashSale.class);
            sale.runVipEarlyAccess();
            sale.runFlashSale();




        }
        System.out.println("Checkout Engine stopped");
    }
}

