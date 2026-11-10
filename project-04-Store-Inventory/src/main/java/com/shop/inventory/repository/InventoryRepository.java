package com.shop.inventory.repository;

import com.shop.inventory.model.Product;
import com.shop.inventory.model.StockMovement;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InventoryRepository {

    private final Map<String, Product> products = new LinkedHashMap<>();
    private final Map<String, Integer> stock = new HashMap<>();
    private final List<StockMovement> movements = new ArrayList<>();


    public void save(Product product, int quantity) {
        products.put(product.sku(), product);
        stock.put(product.sku(), quantity);
    }

    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(products.get(sku));
    }

    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    public int getStock(String sku) {
        return stock.getOrDefault(sku, 0);
    }

    public void setStock(String sku, int quantity) {
        stock.put(sku, quantity);
    }

    public void addMovement(StockMovement movement) {
        movements.add(movement);
    }

    public List<StockMovement> findAllMovements() {
        return List.copyOf(movements);
    }


}
