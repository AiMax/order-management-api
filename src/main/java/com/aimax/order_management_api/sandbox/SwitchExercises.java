package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

        return switch (status) {
            case PENDING -> "Awaiting payment";
            case PAID -> "Preparing your order";
            case SHIPPED -> "On its way";
            case DELIVERED -> "Delivered";
            case CANCELLED -> "Cancelled";
        };
    }

    /**
     * An order can only be cancelled if it is PENDING or PAID.
     * Hint: several constants in the same case (case A, B -> ...).
     */
    public static boolean canBeCancelled(OrderStatus status) {
        return switch (status) {
            case PENDING, PAID -> true;
            default -> false;
        };
    }

    /**
     * Fee charged by each payment method on the amount (pattern matching in switch):
     *  Card         -> 1.5 % of the amount
     *  BankTransfer -> 0 (free)
     *  Bizum        -> fixed 0.20 €
     * Return the result with 2 decimals (HALF_UP rounding).
     */
    public static BigDecimal fee(PaymentMethod method, BigDecimal amount) {
        return switch (method){
            case PaymentMethod.Card ignored -> amount.multiply(new BigDecimal("0.015")).setScale(2, RoundingMode.HALF_UP);
            case PaymentMethod.BankTransfer ignored -> BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            case PaymentMethod.Bizum ignored -> new BigDecimal("0.20").setScale(2, RoundingMode.HALF_UP);
        };
    }

    /**
     * Text for the payment method, using the value each record carries
     * (hint: record patterns, case Card(var digits) -> ...):
     *  Card("1234")            -> "Card ending in 1234"
     *  BankTransfer("ES12...") -> "Bank transfer from ES12..."
     *  Bizum("600111222")      -> "Bizum to 600111222"
     */
    public static String describe(PaymentMethod method) {
        return switch (method) {
            case PaymentMethod.Card card -> "Card ending in " + card.last4Digits();
            case PaymentMethod.Bizum bizum -> "Bizum to " + bizum.phone();
            case PaymentMethod.BankTransfer bankTransfer -> "Bank transfer from " + bankTransfer.iban();
        };
    }
}
