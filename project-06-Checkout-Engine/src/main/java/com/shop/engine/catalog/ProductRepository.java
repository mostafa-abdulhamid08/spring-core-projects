package com.shop.engine.catalog;

import com.shop.engine.storage.Database;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final Map<String, Product> products = new LinkedHashMap<>();
    private final Database database;

    public ProductRepository(Database database) {
        this.database = database;
    }

    public void save(Product product) {
        database.requireTable("products");
        products.put(product.sku(), product);
    }

    public Optional<Product> findBySku(String sku) {
        database.requireTable("products");
        return Optional.ofNullable(products.get(sku));
    }

    public List<Product> findAll() {
        database.requireTable("products");
        return List.copyOf(products.values());
    }
}