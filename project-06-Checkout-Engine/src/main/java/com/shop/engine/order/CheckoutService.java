package com.shop.engine.order;

import com.shop.engine.catalog.Product;
import com.shop.engine.catalog.ProductRepository;
import com.shop.engine.fraud.FraudCheckClient;
import com.shop.engine.inventory.InventoryService;
import com.shop.engine.invoice.Invoice;
import com.shop.engine.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private final ProductRepository productRepository;
    private final InventoryService inventory;
    private final OrderRepository orderRepository;
    private final InvoiceService invoiceService;
    private final FraudCheckClient fraudCheck;
    private final int fraudThreshold;

    public CheckoutService(ProductRepository productRepository,
                           InventoryService inventory,
                           OrderRepository orderRepository,
                           InvoiceService invoiceService,
                          @Lazy FraudCheckClient fraudCheck,
                           @Value("${fraud.threshold}") int fraudThreshold) {
        this.productRepository = productRepository;
        this.inventory = inventory;
        this.orderRepository = orderRepository;
        this.invoiceService = invoiceService;
        this.fraudCheck = fraudCheck;
        this.fraudThreshold = fraudThreshold;
    }

    public Order checkout(String customer, String sku, int quantity) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new IllegalArgumentException("unknown product " + sku));   // ①
        inventory.reserve(sku, quantity);                                                    // ②
        String id = orderRepository.nextId();                                                // ③
        Invoice invoice = invoiceService.issue(id, customer, product, quantity);              // ④
        if (invoice.total() > fraudThreshold && !fraudCheck.approve(invoice)) {              // ⑤ fraud
            throw new IllegalStateException("order " + id + " rejected by fraud check");
        }
        Order order = new Order(id, customer, sku, quantity, invoice);
        orderRepository.save(order);                                                         // ⑥
        return order;
    }
}