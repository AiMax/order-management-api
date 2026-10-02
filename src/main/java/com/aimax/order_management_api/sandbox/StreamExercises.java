package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

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
        return orders.stream()
                .filter(order -> customer.equals(order.customer()))
                .sorted(Comparator.comparing(Order::date))
                .toList();
    }

    /**
     * 2. Total revenue: sum of the totals of all orders that are NOT cancelled.
     */
    public static BigDecimal totalRevenue(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.status() != OrderStatus.CANCELLED)
                .map(Order::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 3. Total spent by each customer (excluding cancelled orders).
     * A customer who only has cancelled orders does not appear in the map.
     * Hint: Collectors.groupingBy + Collectors.reducing.
     */
    public static Map<String, BigDecimal> totalByCustomer(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.status() != OrderStatus.CANCELLED)
                .collect(groupingBy(Order::customer,
                        reducing(BigDecimal.ZERO, Order::total, BigDecimal::add)));
    }

    /**
     * 4. Name of the product with the most units sold in non-cancelled orders.
     * If there are no lines, Optional.empty().
     * Hint: flatMap over the lines.
     */
    public static Optional<String> bestSellingProduct(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.status() != OrderStatus.CANCELLED)
                .flatMap(order -> order.lines().stream())
                .collect(groupingBy(OrderLine::product,
                        reducing(0, OrderLine::quantity, Integer::sum)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey); // This map is from the Optional not from the stream.

    }

    /**
     * 5. Number of orders in each status.
     * Only statuses with at least one order appear as keys.
     */
    public static Map<OrderStatus, Long> countByStatus(List<Order> orders) {
        return orders.stream()
                .collect(groupingBy(Order::status, reducing(0L, order-> 1L, Long::sum)));



    }
}
