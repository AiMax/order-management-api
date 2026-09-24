package com.aimax.order_management_api.sandbox;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EjerciciosTextBlocksTest {

    @Test
    @DisplayName("resumen genera el texto con el formato exacto")
    void resumen() {
        var esperado = """
                Pedido #1
                Cliente: Ana
                Estado: PAGADO
                Líneas: 2
                Total: 65.50 €
                """;

        assertThat(EjerciciosTextBlocks.resumen(PedidosDePrueba.pedido1())).isEqualTo(esperado);
    }

    @Test
    @DisplayName("resumen muestra siempre 2 decimales, también en un pedido vacío")
    void resumenPedidoVacio() {
        assertThat(EjerciciosTextBlocks.resumen(PedidosDePrueba.pedido6()))
                .contains("Líneas: 0")
                .contains("Total: 0.00 €");
    }

    @Test
    @DisplayName("aJson genera el JSON con sangría de 2 espacios")
    void aJson() {
        var esperado = """
                {
                  "id": 1,
                  "cliente": "Ana",
                  "estado": "PAGADO",
                  "total": 65.50
                }
                """;

        assertThat(EjerciciosTextBlocks.aJson(PedidosDePrueba.pedido1())).isEqualTo(esperado);
    }

    @Test
    @DisplayName("aJson usa los datos del pedido que recibe")
    void aJsonOtroPedido() {
        assertThat(EjerciciosTextBlocks.aJson(PedidosDePrueba.pedido6()))
                .contains("\"id\": 6,")
                .contains("\"total\": 0.00");
    }
}
