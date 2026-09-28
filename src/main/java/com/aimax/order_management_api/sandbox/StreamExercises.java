package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Monday's 5 stream exercises. Solve them without for loops.
 */
public final class StreamExercises {

    private StreamExercises() {
    }

    /**
     * 1. A customer's orders, sorted by date (oldest first).
     */
    public static List<Order> ordersByCustomer(List<Order> orders, String customer) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 2. Total revenue: sum of the totals of all orders that are NOT cancelled.
     */
    public static BigDecimal totalRevenue(List<Order> orders) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 3. Total spent by each customer (excluding cancelled orders).
     * A customer who only has cancelled orders does not appear in the map.
     * Hint: Collectors.groupingBy + Collectors.reducing.
     */
    public static Map<String, BigDecimal> totalByCustomer(List<Order> orders) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 4. Name of the product with the most units sold in non-cancelled orders.
     * If there are no lines, Optional.empty().
     * Hint: flatMap over the lines.
     */
    public static Optional<String> bestSellingProduct(List<Order> orders) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 5. Number of orders in each status.
     * Only statuses with at least one order appear as keys.
     */
    public static Map<OrderStatus, Long> countByStatus(List<Order> orders) {
        throw new UnsupportedOperationException("TODO");
    }
}
