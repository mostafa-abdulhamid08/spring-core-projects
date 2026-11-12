package com.shop.hub.cart;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<String, Integer> items = new LinkedHashMap<>();

    public void add(String sku, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive, got " + quantity);
        }
        int current = items.getOrDefault(sku, 0);   // لو مش موجود: 0
        items.put(sku, current + quantity);
    }
    public Map<String, Integer> items() {
        return new LinkedHashMap<>(items);           // نسخة بنفس الترتيب
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
