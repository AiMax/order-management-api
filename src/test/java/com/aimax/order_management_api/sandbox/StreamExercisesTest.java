package com.aimax.order_management_api.sandbox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StreamExercisesTest {

    private final List<Order> orders = TestOrders.all();

    @Nested
    @DisplayName("1. ordersByCustomer")
    class OrdersByCustomer {

        @Test
        @DisplayName("returns only the customer's orders, sorted by date")
        void filtersAndSorts() {
            var result = StreamExercises.ordersByCustomer(orders, "Ana");

            assertThat(result).extracting(Order::id).containsExactly(3L, 1L, 6L);
        }

        @Test
        @DisplayName("returns an empty list if the customer has no orders")
        void customerWithoutOrders() {
            assertThat(StreamExercises.ordersByCustomer(orders, "Pepe")).isEmpty();
        }

        @Test
        @DisplayName("does not change the order of the original list")
        void doesNotModifyOriginal() {
            StreamExercises.ordersByCustomer(orders, "Ana");

            assertThat(orders).extracting(Order::id).containsExactly(1L, 2L, 3L, 4L, 5L, 6L);
        }
    }

    @Nested
    @DisplayName("2. totalRevenue")
    class TotalRevenue {

        @Test
        @DisplayName("adds up the totals excluding cancelled orders")
        void excludesCancelled() {
            // 65.50 + 180.00 + 46.50 + 44.50 + 0 (order 4, worth 250.00, is cancelled)
            assertThat(StreamExercises.totalRevenue(orders)).isEqualByComparingTo("336.50");
        }

        @Test
        @DisplayName("an empty list has 0 revenue")
        void emptyList() {
            assertThat(StreamExercises.totalRevenue(List.of())).isEqualByComparingTo(BigDecimal.ZERO);
        }
    }

    @Nested
    @DisplayName("3. totalByCustomer")
    class TotalByCustomer {

        @Test
        @DisplayName("groups by customer and adds up their non-cancelled orders")
        void groups() {
            var result = StreamExercises.totalByCustomer(orders);

            assertThat(result).hasSize(2);
            assertThat(result.get("Ana")).isEqualByComparingTo("112.00");
            assertThat(result.get("Luis")).isEqualByComparingTo("224.50");
        }

        @Test
        @DisplayName("a customer with only cancelled orders does not appear")
        void onlyCancelled() {
            assertThat(StreamExercises.totalByCustomer(orders)).doesNotContainKey("Marta");
        }
    }

    @Nested
    @DisplayName("4. bestSellingProduct")
    class BestSellingProduct {

        @Test
        @DisplayName("is the one with the most units in non-cancelled orders")
        void mostUnits() {
            // Mouse: 1 + 3 + 1 = 5. Keyboard would have 12 if you counted the cancelled order.
            assertThat(StreamExercises.bestSellingProduct(orders)).contains("Mouse");
        }

        @Test
        @DisplayName("returns an empty Optional if there are no lines")
        void noLines() {
            assertThat(StreamExercises.bestSellingProduct(List.of())).isEmpty();
            assertThat(StreamExercises.bestSellingProduct(List.of(TestOrders.order6()))).isEmpty();
        }
    }

    @Nested
    @DisplayName("5. countByStatus")
    class CountByStatus {

        @Test
        @DisplayName("counts the orders in each status")
        void counts() {
            assertThat(StreamExercises.countByStatus(orders)).containsOnly(
                    entry(OrderStatus.PAID, 2L),
                    entry(OrderStatus.SHIPPED, 1L),
                    entry(OrderStatus.DELIVERED, 1L),
                    entry(OrderStatus.CANCELLED, 1L),
                    entry(OrderStatus.PENDING, 1L));
        }

        @Test
        @DisplayName("does not include statuses without orders")
        void onlyPresentStatuses() {
            var anaOnly = StreamExercises.ordersByCustomer(orders, "Ana");

            assertThat(StreamExercises.countByStatus(anaOnly)).containsOnly(
                    entry(OrderStatus.PAID, 2L),
                    entry(OrderStatus.DELIVERED, 1L));
        }
    }
}
