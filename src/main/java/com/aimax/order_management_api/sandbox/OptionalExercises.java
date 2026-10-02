package com.aimax.order_management_api.sandbox;

import java.util.List;
import java.util.Optional;

/**
 * Optional exercises. Using optional.get() or checking for null by hand is not allowed.
 */
public final class OptionalExercises {

    private OptionalExercises() {
    }

    /**
     * Finds an order by id.
     */
    public static Optional<Order> findById(List<Order> orders, long id) {
        return  orders.stream().filter(o -> o.id() == id).findFirst();
    }

    /**
     * Returns the customer name of the order with that id,
     * or "Unknown customer" if it does not exist.
     * Hint: map + orElse.
     */
    public static String customerOfOrder(List<Order> orders, long id) {
        return orders.stream()
                .filter(order -> order.id() == id)
                .findFirst().map(Order::customer)
                .orElse("Unknown customer");
    }

    /**
     * Returns the order with that id or throws OrderNotFoundException
     * with the message "Order 99 not found" (with the matching id).
     * Hint: orElseThrow.
     */
    public static Order getOrder(List<Order> orders, long id) {

        return orders.stream()
                .filter(order -> order.id() == id)
                .findFirst().orElseThrow(
                        ()-> new OrderNotFoundException("Order "+ id + " not found"));

    }

    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }
}
