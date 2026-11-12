package com.shop.hub.catalog;


import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final Map<String, Product> products = new LinkedHashMap<>();
    public void save(Product product) {
        products.put(product.sku(), product);
    }

    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(products.get(sku));
    }

    public List<Product> findAll() {
        return List.copyOf(products.values());
    }




}
