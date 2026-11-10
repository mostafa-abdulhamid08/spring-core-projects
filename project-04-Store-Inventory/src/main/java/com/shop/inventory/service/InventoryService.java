package com.shop.inventory.service;

import com.shop.inventory.model.Product;
import com.shop.inventory.model.StockMovement;
import com.shop.inventory.pricing.PricingService;
import com.shop.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {
    private static final int LOW_STOCK_THRESHOLD = 3;
    private final InventoryRepository repository;
    private final PricingService pricingService;
    private final Clock clock;
    private final NumberFormat numberFormat;

    public InventoryService(InventoryRepository inventoryRepository, PricingService pricingService, Clock clock, NumberFormat numberFormat) {
        this.repository = inventoryRepository;
        this.pricingService = pricingService;
        this.clock = clock;
        this.numberFormat = numberFormat;
    }
    public void sell(String sku, int quantity) {
        Product product = findProduct(sku);
        requirePositive(quantity, "sell");

        int available = repository.getStock(sku);
        if (quantity > available) {
            throw new InsufficientStockException(
                    "cannot sell " + quantity + " x " + product.name() + ", only " + available + " in stock");
        }

        int unitPrice = pricingService.finalPrice(product);
        int left = available - quantity;
        repository.setStock(sku, left);

        StockMovement sale = new StockMovement(
                StockMovement.Type.SALE, sku, quantity, unitPrice, LocalDateTime.now(clock));
        repository.addMovement(sale);

        System.out.println("SOLD " + quantity + " x " + product.name()
                + " = " + numberFormat.format(sale.total()) + " EGP (left: " + left + ")");

        if (left < LOW_STOCK_THRESHOLD) {
            System.out.println("LOW STOCK: " + product.name() + " has only " + left + " left");
        }
    }
    public void restock(String sku, int quantity) {
        Product product = findProduct(sku);
        requirePositive(quantity, "restock");

        int newStock = repository.getStock(sku) + quantity;
        repository.setStock(sku, newStock);
        repository.addMovement(new StockMovement(
                StockMovement.Type.RESTOCK, sku, quantity, 0, LocalDateTime.now(clock)));

        System.out.println("RESTOCKED " + quantity + " x " + product.name() + " (now: " + newStock + ")");
    }

    private Product findProduct(String sku) {
        return repository.findBySku(sku)
                .orElseThrow(() -> new IllegalArgumentException("unknown product " + sku));
    }

    private void requirePositive(int quantity, String operation) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(operation + " quantity must be positive, got " + quantity);
        }
    }
    public List<Product> allProducts() {
        return repository.findAll();
    }

    public int stockOf(String sku) {
        return repository.getStock(sku);
    }

    public List<StockMovement> movements() {
        return repository.findAllMovements();
    }

}
