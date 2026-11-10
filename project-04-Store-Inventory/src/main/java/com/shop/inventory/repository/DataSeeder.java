package com.shop.inventory.repository;

import com.shop.inventory.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {
    private final InventoryRepository repository;

    public DataSeeder(InventoryRepository repository) {
        this.repository = repository;
    }
    @PostConstruct
    public void seed()
    {
        repository.save(new Product("LAP-001", "Laptop", "Electronics", 25000, false), 5);
        repository.save(new Product("MOU-001", "Mouse", "Electronics", 500, false), 20);
        repository.save(new Product("KEY-001", "Keyboard", "Electronics", 1000, true), 3);
        repository.save(new Product("HEA-001", "Headphones", "Electronics", 1500, false), 8);
        repository.save(new Product("TSH-001", "T-Shirt", "Fashion", 300, false), 30);
        repository.save(new Product("JAC-001", "Jacket", "Fashion", 1200, true), 4);
        repository.save(new Product("MUG-001", "Mug", "Home", 150, false), 12);
        repository.save(new Product("LAM-001", "Desk Lamp", "Home", 800, false), 6);

        System.out.println("[seed] Loaded " + repository.findAll().size() + " products");    }
}
