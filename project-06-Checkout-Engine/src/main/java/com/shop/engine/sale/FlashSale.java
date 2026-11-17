package com.shop.engine.sale;

import com.shop.engine.catalog.Product;
import com.shop.engine.catalog.ProductRepository;
import com.shop.engine.inventory.InventoryService;
import com.shop.engine.invoice.Invoice;
import com.shop.engine.order.CheckoutService;
import com.shop.engine.order.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
public class FlashSale {

    private final CheckoutService checkout;
    private final ProductRepository productRepository;
    private final InventoryService inventory;
    private final SaleReport report;
    private final ExecutorService checkoutPool;
    private final int customers;

    public FlashSale(CheckoutService checkout,
                     ProductRepository productRepository,
                     InventoryService inventory,
                     SaleReport report,
                     ExecutorService checkoutPool,
                     @Value("${flash.customers}") int customers) {
        this.checkout = checkout;
        this.productRepository = productRepository;
        this.inventory = inventory;
        this.report = report;
        this.checkoutPool = checkoutPool;
        this.customers = customers;
    }

    public void runVipEarlyAccess() {
        System.out.println("=== VIP early access ===");
        printInvoice(checkout.checkout("vip-1", "KND-001", 1).invoice());
        printInvoice(checkout.checkout("vip-2", "APD-001", 1).invoice());
        printInvoice(checkout.checkout("vip-3", "PS5-001", 1).invoice());
    }

    public void runFlashSale() throws InterruptedException, ExecutionException {
        List<Product> products = productRepository.findAll();
        Map<String, Integer> startStock = new LinkedHashMap<>();
        for (Product product : products) {
            startStock.put(product.sku(), inventory.remaining(product.sku()));
        }

        System.out.println("=== Flash sale: " + customers + " customers ===");
        List<Callable<Optional<Order>>> tasks = new ArrayList<>();
        for (int i = 1; i <= customers; i++) {
            String customer = "customer-" + i;
            String sku = products.get((i - 1) % products.size()).sku();
            tasks.add(() -> tryCheckout(customer, sku));
        }

        List<Order> placed = new ArrayList<>();
        for (Future<Optional<Order>> result : checkoutPool.invokeAll(tasks)) {
            result.get().ifPresent(placed::add);
        }
        report.print(customers, placed, startStock);
    }

    private Optional<Order> tryCheckout(String customer, String sku) {
        try {
            return Optional.of(checkout.checkout(customer, sku, 1));
        } catch (IllegalStateException soldOut) {
            return Optional.empty();
        }
    }

    private void printInvoice(Invoice invoice) {
        System.out.printf("--- Invoice %s for %s (builder #%d) ---%n",
                invoice.orderId(), invoice.customer(), invoice.builderNumber());
        for (Invoice.Line line : invoice.lines()) {
            System.out.printf(Locale.US, "%s  %-14s%d x %,d%n",
                    line.sku(), line.name(), line.quantity(), line.unitPrice());
        }
        System.out.printf(Locale.US, "subtotal %,d - discount %,d + tax %,d = %,d EGP%n",
                invoice.subtotal(), invoice.discount(), invoice.tax(), invoice.total());
    }
}