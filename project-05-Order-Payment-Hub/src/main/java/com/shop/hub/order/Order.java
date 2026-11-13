package com.shop.hub.order;

import java.util.List;

public record Order(String id, String city, String paymentMethod, List<Line> lines,
                    int subtotal, int tax, int shipping, int total, Status status)  {
    // ① constructor مختصر: بيشتغل مع كل new Order(...) قبل ما الـ fields تتملي
    public Order {
        lines = List.copyOf(lines);
    }
    public enum Status { PAID, REFUNDED }
    // ② method عادية: بترجّع Order جديد بحالة مختلفة
    public Order withStatus(Status newStatus) {
        return new Order(id, city, paymentMethod, lines, subtotal, tax, shipping, total, newStatus);
    }

    // ③ record جوه record: سطر واحد في الطلب
    public record Line(String sku, String name, int quantity, int unitPrice) {
        public int total() {
            return quantity * unitPrice;
        }
    }

}
