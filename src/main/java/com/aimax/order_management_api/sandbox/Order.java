package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A customer's order with its lines.
 */
public record Order(long id, String customer, LocalDate date, OrderStatus status, List<OrderLine> lines) {

    /**
     * TODO (records): compact constructor.
     *  - customer, date and status must not be null  -> NullPointerException (hint: Objects.requireNonNull)
     *  - lines: store an unmodifiable copy (hint: List.copyOf)
     *    so the record is truly immutable.
     */
    public Order {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(date);
        Objects.requireNonNull(status);
        lines = List.copyOf(lines);
    }

    /**
     * TODO (streams): sum of the subtotals of all lines.
     * An order with no lines is worth BigDecimal.ZERO.
     */
    public BigDecimal total() {
        return lines.stream()
                .map(OrderLine::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
