package com.aimax.order_management_api.sandbox;

/**
 * Text blocks ("""...""") and String.formatted().
 */
public final class TextBlockExercises {

    private TextBlockExercises() {
    }

    /**
     * Order summary in this exact format (total with 2 decimals, ends with a line break):
     *
     *   Order #1
     *   Customer: Ana
     *   Status: PAID
     *   Lines: 2
     *   Total: 45.50 €
     */
    public static String summary(Order order) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * The order as JSON (without the lines), in this exact format
     * (2-space indentation, ends with a line break after the closing brace):
     *
     *   {
     *     "id": 1,
     *     "customer": "Ana",
     *     "status": "PAID",
     *     "total": 45.50
     *   }
     */
    public static String toJson(Order order) {
        throw new UnsupportedOperationException("TODO");
    }
}
