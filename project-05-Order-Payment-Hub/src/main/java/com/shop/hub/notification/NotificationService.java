package com.shop.hub.notification;


import com.shop.hub.order.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class NotificationService {
    private final List<NotificationChannel> channels;

    public NotificationService(List<NotificationChannel> channels) {
        this.channels = channels;
    }
    public void orderPlaced(Order order) {
        broadcast(String.format(Locale.US, "Order %s confirmed, total %,d EGP", order.id(), order.total()));
    }

    public void orderRefunded(Order order) {
        broadcast(String.format(Locale.US, "Order %s refunded, %,d EGP", order.id(), order.total()));
    }

    private void broadcast(String message) {
        for (NotificationChannel channel : channels) {
            channel.send(message);
        }
    }
}
