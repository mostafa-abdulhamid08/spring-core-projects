package com.shop.engine.order;

import com.shop.engine.invoice.Invoice;

public record Order(String id, String customer, String sku, int quantity, Invoice invoice) {}