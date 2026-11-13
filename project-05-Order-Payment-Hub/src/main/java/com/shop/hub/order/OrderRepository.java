package com.shop.hub.order;


import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderRepository {
    private final Map<String, Order> orders = new LinkedHashMap<>();
    private int nextNumber = 1001;

    public String nextId() {
        return "ORD-" + nextNumber++;
    }

    public void save(Order order) {
        orders.put(order.id(), order);      // نفس الـ ID تاني = بيستبدل القديم
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<Order> findAll() {
        return List.copyOf(orders.values());
    }
}
