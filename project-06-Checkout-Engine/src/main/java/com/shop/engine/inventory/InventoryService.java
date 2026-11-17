package com.shop.engine.inventory;

import com.shop.engine.storage.Database;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InventoryService {

    private final Map<String, Integer> stock = new ConcurrentHashMap<>();
    private final Database database;

    public InventoryService(Database database) {
        this.database = database;
    }

    public void setStock(String sku, int quantity) {
        database.requireTable("stock");
        stock.put(sku, quantity);
    }

    public int remaining(String sku) {
        database.requireTable("stock");
        return stock.getOrDefault(sku, 0);
    }

    public void reserve(String sku, int quantity) {
        database.requireTable("stock");
        stock.compute(sku, (key, available) -> {
            int current = (available == null) ? 0 : available;      // ① اقرا
            if (current < quantity) {                                // ② اتأكد
                throw new IllegalStateException(sku + " is sold out");
            }
            database.roundTrip();
            return current - quantity;                               // ③ اكتب
        });                                                          // التلاتة كعملية واحدة على الـ SKU
    }
}