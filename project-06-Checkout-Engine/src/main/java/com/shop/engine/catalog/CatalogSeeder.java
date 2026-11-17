package com.shop.engine.catalog;

import com.shop.engine.inventory.InventoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("schemaMigrator")
public class CatalogSeeder {

    private final ProductRepository productRepository;
    private final InventoryService inventory;

    public CatalogSeeder(ProductRepository productRepository, InventoryService inventory) {
        this.productRepository = productRepository;
        this.inventory = inventory;
    }

    @PostConstruct
    public void seed() {
        productRepository.save(new Product("PS5-001", "PlayStation 5", 25_000));
        productRepository.save(new Product("APD-001", "AirPods Pro", 10_000));
        productRepository.save(new Product("KND-001", "Kindle", 6_000));
        inventory.setStock("PS5-001", 11);
        inventory.setStock("APD-001", 21);
        inventory.setStock("KND-001", 31);
        System.out.println("[seed] 3 products, 63 units in stock");
    }
}