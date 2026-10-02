package com.aimax.order_management_api.sandbox;

import static com.aimax.order_management_api.sandbox.TestOrders.line;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// Plain unit tests: no @SpringBootTest, so there is no need to start Spring or the database.
class RecordTests {

    @Nested
    @DisplayName("OrderLine")
    class OrderLineTests {

        @Test
        @DisplayName("a record generates equals, hashCode and toString from its components")
        void equalsHashCodeToString() {
            var a = line("Keyboard", 2, "25.00");
            var b = line("Keyboard", 2, "25.00");

            assertThat(a).isEqualTo(b);
            assertThat(a.hashCode()).isEqualTo(b.hashCode());
            assertThat(a.toString()).contains("product=Keyboard", "quantity=2");
        }

        @Test
        @DisplayName("accessors are named after the component, without 'get'")
        void accessors() {
            var l = line("Mouse", 3, "15.50");

            assertThat(l.product()).isEqualTo("Mouse");
            assertThat(l.quantity()).isEqualTo(3);
            assertThat(l.unitPrice()).isEqualByComparingTo("15.50");
        }

        @Test
        @DisplayName("subtotal = quantity x unit price")
        void subtotal() {
            assertThat(line("Keyboard", 2, "25.00").subtotal()).isEqualByComparingTo("50.00");
            assertThat(line("HDMI cable", 4, "7.25").subtotal()).isEqualByComparingTo("29.00");
        }

        @ParameterizedTest(name = "quantity {0} is not valid")
        @ValueSource(ints = {0, -1, -100})
        @DisplayName("rejects quantities less than or equal to 0")
        void invalidQuantity(int quantity) {
            assertThatThrownBy(() -> line("Keyboard", quantity, "25.00"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest(name = "product \"{0}\" is not valid")
        @ValueSource(strings = {"", "   "})
        @DisplayName("rejects a blank product")
        void blankProduct(String product) {
            assertThatThrownBy(() -> line(product, 1, "25.00"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("rejects a null product")
        void nullProduct() {
            assertThatThrownBy(() -> new OrderLine(null, 1, BigDecimal.TEN))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("rejects a negative or null price")
        void invalidPrice() {
            assertThatThrownBy(() -> line("Keyboard", 1, "-0.01"))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> new OrderLine("Keyboard", 1, null))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("accepts a price of 0 (free gift)")
        void zeroPrice() {
            assertThat(line("Sticker", 1, "0").subtotal()).isEqualByComparingTo("0");
        }
    }

    @Nested
    @DisplayName("Order")
    class OrderTests {

        @Test
        @DisplayName("total = sum of the line subtotals")
        void total() {
            assertThat(TestOrders.order1().total()).isEqualByComparingTo("65.50");
        }

        @Test
        @DisplayName("an order with no lines is worth 0")
        void totalWithoutLines() {
            assertThat(TestOrders.order6().total()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("customer, date and status are required")
        void requiredFields() {
            var today = LocalDate.now();
            List<OrderLine> lines = List.of();

            assertThatThrownBy(() -> new Order(1, null, today, OrderStatus.PAID, lines))
                    .isInstanceOf(NullPointerException.class);
            assertThatThrownBy(() -> new Order(1, "Ana", null, OrderStatus.PAID, lines))
                    .isInstanceOf(NullPointerException.class);
            assertThatThrownBy(() -> new Order(1, "Ana", today, null, lines))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("changing the original list does not change the order (defensive copy)")
        void defensiveCopy() {
            var lines = new ArrayList<OrderLine>();
            lines.add(line("Keyboard", 1, "25.00"));
            var order = new Order(1, "Ana", LocalDate.now(), OrderStatus.PENDING, lines);

            lines.add(line("Monitor", 1, "180.00"));

            assertThat(order.lines()).hasSize(1);
            assertThat(order.total()).isEqualByComparingTo("25.00");
        }

        @Test
        @DisplayName("the order lines cannot be modified from outside")
        void unmodifiableLines() {
            var order = TestOrders.order1();

            assertThatThrownBy(() -> order.lines().add(line("Monitor", 1, "180.00")))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
