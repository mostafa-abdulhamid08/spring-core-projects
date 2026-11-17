package com.shop.engine.sale;

import com.shop.engine.inventory.InventoryService;
import com.shop.engine.order.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class SaleReport {

    private final InventoryService inventory;

    public SaleReport(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void print(int customers, List<Order> placed, Map<String, Integer> startStock) {
        System.out.println("=== Flash sale report ===");
        System.out.printf("customers: %d | placed: %d | sold out: %d%n",
                customers, placed.size(), customers - placed.size());

        Map<String, Integer> sold = new LinkedHashMap<>();
        for (Order order : placed) {
            sold.merge(order.sku(), order.quantity(), Integer::sum);
        }
        startStock.forEach((sku, start) ->
                System.out.printf("%s  sold %d of %d, left %d%n",
                        sku, sold.getOrDefault(sku, 0), start, inventory.remaining(sku)));

        int revenue = placed.stream().mapToInt(order -> order.invoice().total()).sum();
        System.out.printf(Locale.US, "revenue: %,d EGP%n", revenue);

        // check 1: المخزون
        List<String> oversold = new ArrayList<>();
        startStock.forEach((sku, start) -> {
            int count = sold.getOrDefault(sku, 0);
            if (count > start) {
                oversold.add(sku + " sold " + count + " of " + start);
            }
        });
        if (oversold.isEmpty()) {
            System.out.println("[OK] no overselling");
        } else {
            oversold.forEach(line -> System.out.println("[FAIL] overselling: " + line));
        }

        // check 2: الـ IDs
        long unique = placed.stream().map(Order::id).distinct().count();
        if (unique == placed.size()) {
            System.out.printf("[OK] order IDs are unique (%d of %d)%n", unique, placed.size());
        } else {
            System.out.printf("[FAIL] duplicate order IDs: %d unique of %d%n", unique, placed.size());
        }

        // check 3: الفواتير
        long bad = placed.stream()
                .filter(order -> order.invoice().lines().size() != 1
                        || !order.invoice().orderId().equals(order.id()))
                .count();
        if (bad == 0) {
            System.out.println("[OK] every invoice has its own lines");
        } else {
            System.out.printf("[FAIL] %d invoices have lines from other orders%n", bad);
        }
    }
}