package com.aimax.order_management_api.sandbox;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import com.aimax.order_management_api.sandbox.PaymentMethod.BankTransfer;
import com.aimax.order_management_api.sandbox.PaymentMethod.Bizum;
import com.aimax.order_management_api.sandbox.PaymentMethod.Card;

class SwitchExercisesTest {

    @Nested
    @DisplayName("switch over an enum")
    class EnumSwitch {

        @ParameterizedTest(name = "{0} -> \"{1}\"")
        @CsvSource({
                "PENDING,   Awaiting payment",
                "PAID,      Preparing your order",
                "SHIPPED,   On its way",
                "DELIVERED, Delivered",
                "CANCELLED, Cancelled"
        })
        @DisplayName("description returns the text for each status")
        void description(OrderStatus status, String expected) {
            assertThat(SwitchExercises.description(status)).isEqualTo(expected);
        }

        @ParameterizedTest(name = "{0} can be cancelled")
        @EnumSource(value = OrderStatus.class, names = {"PENDING", "PAID"})
        void cancellable(OrderStatus status) {
            assertThat(SwitchExercises.canBeCancelled(status)).isTrue();
        }

        @ParameterizedTest(name = "{0} cannot be cancelled")
        @EnumSource(value = OrderStatus.class, names = {"PENDING", "PAID"}, mode = EnumSource.Mode.EXCLUDE)
        void notCancellable(OrderStatus status) {
            assertThat(SwitchExercises.canBeCancelled(status)).isFalse();
        }
    }

    @Nested
    @DisplayName("pattern matching over a sealed interface")
    class PatternMatching {

        @ParameterizedTest(name = "card: {0} € -> {1} €")
        @CsvSource({
                "100.00, 1.50",
                "33.33,  0.50",
                "0,      0.00"
        })
        @DisplayName("card charges 1.5 % rounded to 2 decimals")
        void cardFee(String amount, String expected) {
            assertThat(SwitchExercises.fee(new Card("1234"), new BigDecimal(amount)))
                    .isEqualTo(new BigDecimal(expected));
        }

        @Test
        @DisplayName("bank transfer is free")
        void bankTransferFee() {
            assertThat(SwitchExercises.fee(new BankTransfer("ES12"), new BigDecimal("500.00")))
                    .isEqualTo(new BigDecimal("0.00"));
        }

        @Test
        @DisplayName("bizum charges a fixed 0.20 € whatever the amount")
        void bizumFee() {
            assertThat(SwitchExercises.fee(new Bizum("600111222"), new BigDecimal("10.00")))
                    .isEqualTo(new BigDecimal("0.20"));
            assertThat(SwitchExercises.fee(new Bizum("600111222"), new BigDecimal("999.99")))
                    .isEqualTo(new BigDecimal("0.20"));
        }

        @Test
        @DisplayName("describe uses the value each record carries")
        void describe() {
            assertThat(SwitchExercises.describe(new Card("1234")))
                    .isEqualTo("Card ending in 1234");
            assertThat(SwitchExercises.describe(new BankTransfer("ES9121000418450200051332")))
                    .isEqualTo("Bank transfer from ES9121000418450200051332");
            assertThat(SwitchExercises.describe(new Bizum("600111222")))
                    .isEqualTo("Bizum to 600111222");
        }
    }
}
