package com.shop.hub.ui;

import com.shop.hub.cart.Cart;
import com.shop.hub.catalog.Product;
import com.shop.hub.catalog.ProductRepository;
import com.shop.hub.order.CheckoutService;
import com.shop.hub.order.Order;
import com.shop.hub.order.OrderRepository;
import com.shop.hub.order.RefundService;
import com.shop.hub.payment.PaymentService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component
public class ConsoleMenu {

    private final ProductRepository productRepository;
    private final PaymentService paymentService;
    private final CheckoutService checkoutService;
    private final RefundService refundService;
    private final OrderRepository orderRepository;

    public ConsoleMenu(ProductRepository productRepository,
                       PaymentService paymentService,
                       CheckoutService checkoutService,
                       RefundService refundService,
                       OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.paymentService = paymentService;
        this.checkoutService = checkoutService;
        this.refundService = refundService;
        this.orderRepository = orderRepository;
    }

    public void run() {
        Scanner in = new Scanner(System.in);   // مش بنقفله: قفله بيقفل System.in نفسه
        Cart cart = new Cart();                // local: مش field في الـ singleton
        printMenu();
        while (true) {
            try {
                int choice = Integer.parseInt(ask(in, "Choose: "));
                switch (choice) {
                    case 0 -> {
                        System.out.println("Bye");
                        return;
                    }
                    case 1 -> showProducts();
                    case 2 -> addToCart(in, cart);
                    case 3 -> showCart(cart);
                    case 4 -> checkout(in, cart);
                    case 5 -> showOrders();
                    case 6 -> refund(in);
                    default -> System.out.println("ERROR: unknown option " + choice);
                }
            } catch (NumberFormatException e) {           // لازم قبل IllegalArgumentException
                System.out.println("ERROR: please enter a number");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("REJECTED: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.print("""
                === Order & Payment Hub ===
                1) Show products
                2) Add to cart
                3) Show cart
                4) Checkout
                5) Show orders
                6) Refund an order
                0) Exit
                """);
    }

    private void showProducts() {
        for (Product product : productRepository.findAll()) {
            System.out.printf(Locale.US, "%s  %-12s%,8d EGP%n", product.sku(), product.name(), product.price());
        }
    }

    private void addToCart(Scanner in, Cart cart) {
        String sku = ask(in, "SKU: ").toUpperCase();
        int quantity = Integer.parseInt(ask(in, "Quantity: "));
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new IllegalArgumentException("unknown product " + sku));
        cart.add(sku, quantity);
        System.out.println("Added " + quantity + " x " + product.name());
    }

    private void showCart(Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        cart.items().forEach((sku, quantity) -> System.out.println(sku + " x " + quantity));
    }

    private void checkout(Scanner in, Cart cart) {
        String city = ask(in, "City: ");
        String methods = String.join(", ", paymentService.availableMethods());
        String method = ask(in, "Payment method (" + methods + "): ").toLowerCase();
        checkoutService.placeOrder(cart, city, method);
        cart.clear();                          // بنوصل هنا بس لو placeOrder نجحت
    }

    private void showOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            System.out.println("No orders yet");
            return;
        }
        for (Order order : orders) {
            System.out.printf(Locale.US, "%s  %,d EGP  %s  %s%n",
                    order.id(), order.total(), order.paymentMethod(), order.status());
        }
    }

    private void refund(Scanner in) {
        refundService.refund(ask(in, "Order id: ").toUpperCase());
    }

    private String ask(Scanner in, String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }
}