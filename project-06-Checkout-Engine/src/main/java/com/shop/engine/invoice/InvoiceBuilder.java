package com.shop.engine.invoice;

import com.shop.engine.catalog.Product;
import com.shop.engine.pricing.FlashDiscount;
import com.shop.engine.pricing.TaxCalculator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
public class InvoiceBuilder {

    private static final AtomicInteger CREATED = new AtomicInteger();   // للتعلّم بس

    private final TaxCalculator taxCalculator;
    private final FlashDiscount flashDiscount;
    private final int number;
    private final List<Invoice.Line> lines = new ArrayList<>();
    private String orderId;
    private String customer;

    public InvoiceBuilder(TaxCalculator taxCalculator, FlashDiscount flashDiscount) {
        this.taxCalculator = taxCalculator;
        this.flashDiscount = flashDiscount;
        this.number = CREATED.incrementAndGet();
    }

    public InvoiceBuilder forOrder(String orderId, String customer) {
        this.orderId = orderId;
        this.customer = customer;
        return this;
    }

    public InvoiceBuilder addLine(Product product, int quantity) {
        lines.add(new Invoice.Line(product.sku(), product.name(), quantity, product.price()));
        return this;
    }

    public Invoice build() {
        int subtotal = lines.stream().mapToInt(Invoice.Line::total).sum();
        int discount = flashDiscount.discountOn(subtotal);
        int tax = taxCalculator.taxOn(subtotal - discount);
        return new Invoice(orderId, customer, lines, subtotal, discount, tax,
                subtotal - discount + tax, number);
    }
}