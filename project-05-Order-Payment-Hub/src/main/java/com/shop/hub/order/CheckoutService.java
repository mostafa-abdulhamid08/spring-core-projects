package com.shop.hub.order;


import com.shop.hub.cart.Cart;
import com.shop.hub.catalog.Product;
import com.shop.hub.catalog.ProductRepository;
import com.shop.hub.loyalty.LoyaltyService;
import com.shop.hub.notification.NotificationService;
import com.shop.hub.payment.PaymentService;
import com.shop.hub.pricing.ShippingService;
import com.shop.hub.pricing.TaxCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class CheckoutService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final TaxCalculator taxCalculator;
    private final ShippingService shippingService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;      // field جديد
    private LoyaltyService loyaltyService;           // مش final: ممكن يفضل null



    public CheckoutService(ProductRepository productRepository, OrderRepository orderRepository, TaxCalculator taxCalculator, ShippingService shippingService, PaymentService paymentService, NotificationService notificationService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.taxCalculator = taxCalculator;
        this.shippingService = shippingService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    @Autowired(required = false)
    public void setLoyaltyService(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }

    public Order placeOrder(Cart cart, String city, String paymentMethod) {
        // ① التحقق: قبل أي ID أو دفع
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("cart is empty");
        }
        paymentService.requireSupported(paymentMethod);
        List<Order.Line> lines = toLines(cart);

        // ② الحساب
        int subtotal = lines.stream().mapToInt(Order.Line::total).sum();
        int tax = taxCalculator.taxOn(subtotal);
        int shipping = shippingService.feeFor(city, subtotal);
        int total = subtotal + tax + shipping;

        // ③ التنفيذ
        String id = orderRepository.nextId();
        paymentService.pay(paymentMethod, id, total);
        Order order = new Order(id, city, paymentMethod, lines,
                subtotal, tax, shipping, total, Order.Status.PAID);
        orderRepository.save(order);
        System.out.printf(Locale.US, "PLACED %s: subtotal %,d + tax %,d + shipping %,d = %,d EGP via %s%n",
                id, subtotal, tax, shipping, total, paymentMethod);
        notificationService.orderPlaced(order);
        if (loyaltyService != null) {
            loyaltyService.award(order);
        }
        return order;
    }
    private List<Order.Line> toLines(Cart cart) {
        List<Order.Line> lines = new ArrayList<>();
        for (Map.Entry<String, Integer> item : cart.items().entrySet()) {
            String sku = item.getKey();
            Product product = productRepository.findBySku(sku)
                    .orElseThrow(() -> new IllegalArgumentException("unknown product " + sku));
            lines.add(new Order.Line(sku, product.name(), item.getValue(), product.price()));
        }
        return lines;
    }
}
