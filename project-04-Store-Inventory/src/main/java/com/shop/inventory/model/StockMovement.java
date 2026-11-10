package com.shop.inventory.model;

import java.time.LocalDateTime;

public record StockMovement(Type type, String sku, int quantity, int unitPrice, LocalDateTime time) {
    public enum Type{SALE, RESTOCK}

    public int total()
    {
        return quantity*unitPrice;
    }
}
