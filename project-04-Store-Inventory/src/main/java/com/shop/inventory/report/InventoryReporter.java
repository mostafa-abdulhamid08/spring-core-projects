package com.shop.inventory.report;


import com.shop.inventory.model.Product;
import com.shop.inventory.model.StockMovement;
import com.shop.inventory.pricing.PricingService;
import com.shop.inventory.service.InventoryService;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.List;

@Component
public class InventoryReporter {
    private final InventoryService inventoryService;
    private final PricingService pricingService;
    private final NumberFormat priceFormat;

    public InventoryReporter(InventoryService inventoryService,
                             PricingService pricingService,
                             NumberFormat priceFormat) {
        this.inventoryService = inventoryService;
        this.pricingService = pricingService;
        this.priceFormat = priceFormat;
    }
    public void printCatalog() {
        System.out.println();
        System.out.println("=== Catalog ===");
        for (Product product : inventoryService.allProducts()) {
            String price = priceFormat.format(pricingService.finalPrice(product));
            String tag = product.clearance() ? "  [CLEARANCE]" : "";
            System.out.printf("%-8s %-12s %-12s %6s EGP  stock: %d%s%n",
                    product.sku(), product.name(), product.category(), price,
                    inventoryService.stockOf(product.sku()), tag);
        }
        System.out.println();
    }

    @PreDestroy
    public void printFinalReport() {
        System.out.println();
        System.out.println("=== Final report ===");
        for (Product product : inventoryService.allProducts()) {
            System.out.printf("%-8s %-12s %3d%n",
                    product.sku(), product.name(), inventoryService.stockOf(product.sku()));
        }

        List<StockMovement> movements = inventoryService.movements();
        int totalSales = movements.stream()
                .filter(m -> m.type() == StockMovement.Type.SALE)
                .mapToInt(StockMovement::total)
                .sum();

        System.out.println("Movements recorded: " + movements.size());
        System.out.println("Total sales: " + priceFormat.format(totalSales) + " EGP");
    }

}
