package com.shop.engine.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SchemaMigrator {

    private final Database database;

    public SchemaMigrator(Database database) {
        this.database = database;
    }

    @PostConstruct
    public void migrate() {
        database.createTable("products");
        database.createTable("stock");
        database.createTable("orders");
        System.out.println("[db] migrated: products, stock, orders");
    }
}