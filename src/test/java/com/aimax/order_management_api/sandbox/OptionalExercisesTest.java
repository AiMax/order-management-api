package com.aimax.order_management_api.sandbox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.aimax.order_management_api.sandbox.OptionalExercises.OrderNotFoundException;

class OptionalExercisesTest {

    private final List<Order> orders = TestOrders.all();

    @Test
    @DisplayName("findById returns the order wrapped in an Optional")
    void findExisting() {
        var result = OptionalExercises.findById(orders, 2);

        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(o -> assertThat(o.customer()).isEqualTo("Luis"));
    }

    @Test
    @DisplayName("findById returns an empty Optional (never null) if it does not exist")
    void findMissing() {
        var result = OptionalExercises.findById(orders, 99);

        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("customerOfOrder returns the customer's name")
    void existingCustomer() {
        assertThat(OptionalExercises.customerOfOrder(orders, 4)).isEqualTo("Marta");
    }

    @Test
    @DisplayName("customerOfOrder returns 'Unknown customer' if the order does not exist")
    void missingCustomer() {
        assertThat(OptionalExercises.customerOfOrder(orders, 99)).isEqualTo("Unknown customer");
    }

    @Test
    @DisplayName("getOrder returns the order if it exists")
    void getExisting() {
        assertThat(OptionalExercises.getOrder(orders, 1)).isEqualTo(TestOrders.order1());
    }

    @Test
    @DisplayName("getOrder throws OrderNotFoundException if it does not exist")
    void getMissing() {
        assertThatThrownBy(() -> OptionalExercises.getOrder(orders, 99))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessage("Order 99 not found");
    }
}
