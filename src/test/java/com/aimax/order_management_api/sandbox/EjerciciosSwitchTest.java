package com.aimax.order_management_api.sandbox;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import com.aimax.order_management_api.sandbox.MetodoPago.Bizum;
import com.aimax.order_management_api.sandbox.MetodoPago.Tarjeta;
import com.aimax.order_management_api.sandbox.MetodoPago.Transferencia;

class EjerciciosSwitchTest {

    @Nested
    @DisplayName("switch sobre un enum")
    class SwitchEnum {

        @ParameterizedTest(name = "{0} -> \"{1}\"")
        @CsvSource({
                "PENDIENTE, Pendiente de pago",
                "PAGADO,    Preparando tu pedido",
                "ENVIADO,   En camino",
                "ENTREGADO, Entregado",
                "CANCELADO, Cancelado"
        })
        @DisplayName("descripcion devuelve el texto de cada estado")
        void descripcion(EstadoPedido estado, String esperado) {
            assertThat(EjerciciosSwitch.descripcion(estado)).isEqualTo(esperado);
        }

        @ParameterizedTest(name = "{0} se puede cancelar")
        @EnumSource(value = EstadoPedido.class, names = {"PENDIENTE", "PAGADO"})
        void cancelables(EstadoPedido estado) {
            assertThat(EjerciciosSwitch.puedeCancelarse(estado)).isTrue();
        }

        @ParameterizedTest(name = "{0} no se puede cancelar")
        @EnumSource(value = EstadoPedido.class, names = {"PENDIENTE", "PAGADO"}, mode = EnumSource.Mode.EXCLUDE)
        void noCancelables(EstadoPedido estado) {
            assertThat(EjerciciosSwitch.puedeCancelarse(estado)).isFalse();
        }
    }

    @Nested
    @DisplayName("pattern matching sobre una interfaz sellada")
    class PatternMatching {

        @ParameterizedTest(name = "tarjeta: {0} € -> {1} €")
        @CsvSource({
                "100.00, 1.50",
                "33.33,  0.50",
                "0,      0.00"
        })
        @DisplayName("tarjeta cobra el 1,5 % redondeado a 2 decimales")
        void comisionTarjeta(String importe, String esperada) {
            assertThat(EjerciciosSwitch.comision(new Tarjeta("1234"), new BigDecimal(importe)))
                    .isEqualTo(new BigDecimal(esperada));
        }

        @Test
        @DisplayName("transferencia es gratis")
        void comisionTransferencia() {
            assertThat(EjerciciosSwitch.comision(new Transferencia("ES12"), new BigDecimal("500.00")))
                    .isEqualTo(new BigDecimal("0.00"));
        }

        @Test
        @DisplayName("bizum cobra 0,20 € fijos sea cual sea el importe")
        void comisionBizum() {
            assertThat(EjerciciosSwitch.comision(new Bizum("600111222"), new BigDecimal("10.00")))
                    .isEqualTo(new BigDecimal("0.20"));
            assertThat(EjerciciosSwitch.comision(new Bizum("600111222"), new BigDecimal("999.99")))
                    .isEqualTo(new BigDecimal("0.20"));
        }

        @Test
        @DisplayName("describir usa el dato que lleva dentro cada record")
        void describir() {
            assertThat(EjerciciosSwitch.describir(new Tarjeta("1234")))
                    .isEqualTo("Tarjeta acabada en 1234");
            assertThat(EjerciciosSwitch.describir(new Transferencia("ES9121000418450200051332")))
                    .isEqualTo("Transferencia desde ES9121000418450200051332");
            assertThat(EjerciciosSwitch.describir(new Bizum("600111222")))
                    .isEqualTo("Bizum al 600111222");
        }
    }
}
