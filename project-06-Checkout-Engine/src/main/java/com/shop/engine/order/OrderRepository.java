package com.shop.engine.order;

import com.shop.engine.storage.Database;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class OrderRepository {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final Database database;
    private final AtomicInteger nextNumber = new AtomicInteger(1001);

    public OrderRepository(Database database) {
        this.database = database;
    }

    public String nextId() {
        database.requireTable("orders");
        database.roundTrip();
        return "ORD-" + nextNumber.getAndIncrement();     // اقرا وزوّد في خطوة واحدة
    }

    public void save(Order order) {
        database.requireTable("orders");
        orders.put(order.id(), order);
    }
}