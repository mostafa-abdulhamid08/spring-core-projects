package com.shop.engine.invoice;

import com.shop.engine.catalog.Product;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final ObjectProvider<InvoiceBuilder> builders;

    public InvoiceService(ObjectProvider<InvoiceBuilder> builders) {
        this.builders = builders;
    }

    public Invoice issue(String orderId, String customer, Product product, int quantity) {
        return builders.getObject()                 // ✔ builder جديد مع كل فاتورة
                .forOrder(orderId, customer)
                .addLine(product, quantity)
                .build();
    }

}