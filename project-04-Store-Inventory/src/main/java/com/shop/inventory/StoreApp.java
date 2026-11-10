package com.shop.inventory;

import com.shop.inventory.report.InventoryReporter;
import com.shop.inventory.service.InsufficientStockException;
import com.shop.inventory.service.InventoryService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StoreApp {

    public static void main(String[] args) {
        System.out.println("Store Inventory starting...");

        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            InventoryService inventory = context.getBean(InventoryService.class);
            InventoryReporter reporter = context.getBean(InventoryReporter.class);
            reporter.printCatalog();


            attempt(() -> inventory.sell("LAP-001", 3));
            attempt(() -> inventory.sell("KEY-001", 1));
            attempt(() -> inventory.sell("JAC-001", 10));
            attempt(() -> inventory.restock("LAP-001", 10));
            attempt(() -> inventory.sell("TSH-001", 2));
            attempt(() -> inventory.restock("MUG-001", 0));
            attempt(() -> inventory.sell("XXX-999", 1));
        }

        System.out.println("Store Inventory stopped");
    }

    private static void attempt(Runnable operation) {
        try {
            operation.run();
        } catch (IllegalArgumentException | InsufficientStockException e) {
            System.out.println("REJECTED: " + e.getMessage());
        }
    }
}