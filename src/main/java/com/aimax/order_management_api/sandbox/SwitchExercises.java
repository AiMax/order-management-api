package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;

/**
 * Modern switch: switch as an expression, "->" arrows and pattern matching.
 */
public final class SwitchExercises {

    private SwitchExercises() {
    }

    /**
     * Customer-facing text for each status (switch expression, no break):
     *  PENDING   -> "Awaiting payment"
     *  PAID      -> "Preparing your order"
     *  SHIPPED   -> "On its way"
     *  DELIVERED -> "Delivered"
     *  CANCELLED -> "Cancelled"
     */
    public static String description(OrderStatus status) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * An order can only be cancelled if it is PENDING or PAID.
     * Hint: several constants in the same case (case A, B -> ...).
     */
    public static boolean canBeCancelled(OrderStatus status) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Fee charged by each payment method on the amount (pattern matching in switch):
     *  Card         -> 1.5 % of the amount
     *  BankTransfer -> 0 (free)
     *  Bizum        -> fixed 0.20 €
     * Return the result with 2 decimals (HALF_UP rounding).
     */
    public static BigDecimal fee(PaymentMethod method, BigDecimal amount) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Text for the payment method, using the value each record carries
     * (hint: record patterns, case Card(var digits) -> ...):
     *  Card("1234")            -> "Card ending in 1234"
     *  BankTransfer("ES12...") -> "Bank transfer from ES12..."
     *  Bizum("600111222")      -> "Bizum to 600111222"
     */
    public static String describe(PaymentMethod method) {
        throw new UnsupportedOperationException("TODO");
    }
}
