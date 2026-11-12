package com.shop.hub.catalog;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CatalogSeeder {

    private final ProductRepository repository;

    public CatalogSeeder(ProductRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void seed() {
        repository.save(new Product("LAP-001", "Laptop", 25000));
        repository.save(new Product("MOU-001", "Mouse", 500));
        repository.save(new Product("HEA-001", "Headphones", 1500));
        repository.save(new Product("TSH-001", "T-Shirt", 300));
        repository.save(new Product("MUG-001", "Mug", 150));

        System.out.println("[seed] Loaded " + repository.findAll().size() + " products");
    }
}