package com.shop.engine.invoice;

import java.util.List;

public record Invoice(String orderId, String customer, List<Line> lines,
                      int subtotal, int discount, int tax, int total, int builderNumber) {

    public Invoice {
        lines = List.copyOf(lines);
    }

    public record Line(String sku, String name, int quantity, int unitPrice) {
        public int total() {
            return quantity * unitPrice;
        }
    }
}