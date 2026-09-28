package com.aimax.order_management_api.sandbox;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TextBlockExercisesTest {

    @Test
    @DisplayName("summary produces the text in the exact format")
    void summary() {
        var expected = """
                Order #1
                Customer: Ana
                Status: PAID
                Lines: 2
                Total: 65.50 €
                """;

        assertThat(TextBlockExercises.summary(TestOrders.order1())).isEqualTo(expected);
    }

    @Test
    @DisplayName("summary always shows 2 decimals, also for an empty order")
    void summaryEmptyOrder() {
        assertThat(TextBlockExercises.summary(TestOrders.order6()))
                .contains("Lines: 0")
                .contains("Total: 0.00 €");
    }

    @Test
    @DisplayName("toJson produces the JSON with 2-space indentation")
    void toJson() {
        var expected = """
                {
                  "id": 1,
                  "customer": "Ana",
                  "status": "PAID",
                  "total": 65.50
                }
                """;

        assertThat(TextBlockExercises.toJson(TestOrders.order1())).isEqualTo(expected);
    }

    @Test
    @DisplayName("toJson uses the data of the order it receives")
    void toJsonAnotherOrder() {
        assertThat(TextBlockExercises.toJson(TestOrders.order6()))
                .contains("\"id\": 6,")
                .contains("\"total\": 0.00");
    }
}
