package com.shop.hub.order;


import com.shop.hub.notification.NotificationService;
import com.shop.hub.payment.PaymentService;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class RefundService {
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final NotificationService notificationService;


    public RefundService(OrderRepository orderRepository, PaymentService paymentService, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }
    public void refund(String orderId) {
        // ① التحقق: قبل أي فلوس ترجع
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("unknown order " + orderId));
        if (order.status() == Order.Status.REFUNDED) {
            throw new IllegalStateException("order " + orderId + " is already refunded");
        }

        // ② التنفيذ
        paymentService.refund(order.paymentMethod(), order.id(), order.total());
        Order refunded = order.withStatus(Order.Status.REFUNDED);
        orderRepository.save(refunded);
        System.out.printf(Locale.US, "REFUNDED %s: %,d EGP via %s%n",
                refunded.id(), refunded.total(), refunded.paymentMethod());
        notificationService.orderRefunded(refunded);
    }
}
