package com.shop.engine.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Database {

    private final Set<String> tables = ConcurrentHashMap.newKeySet();
    private final int latencyMs;

    public Database(@Value("${db.latency-ms}") int latencyMs) {
        this.latencyMs = latencyMs;
    }

    public void createTable(String name) {
        tables.add(name);
    }

    public void requireTable(String name) {
        if (!tables.contains(name)) {
            throw new IllegalStateException("table " + name + " does not exist");
        }
    }

    public void roundTrip() {                     // كأننا بنكلّم database حقيقية
        try {
            Thread.sleep(latencyMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();   // نرجّع علامة الـ interrupt زاي م كانت
        }
    }
}