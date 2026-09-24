package com.aimax.order_management_api.sandbox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EjerciciosStreamsTest {

    private final List<Pedido> pedidos = PedidosDePrueba.todos();

    @Nested
    @DisplayName("1. pedidosDeCliente")
    class PedidosDeCliente {

        @Test
        @DisplayName("devuelve solo los pedidos del cliente, ordenados por fecha")
        void filtraYOrdena() {
            var resultado = EjerciciosStreams.pedidosDeCliente(pedidos, "Ana");

            assertThat(resultado).extracting(Pedido::id).containsExactly(3L, 1L, 6L);
        }

        @Test
        @DisplayName("devuelve una lista vacía si el cliente no tiene pedidos")
        void clienteSinPedidos() {
            assertThat(EjerciciosStreams.pedidosDeCliente(pedidos, "Pepe")).isEmpty();
        }

        @Test
        @DisplayName("no modifica el orden de la lista original")
        void noModificaOriginal() {
            EjerciciosStreams.pedidosDeCliente(pedidos, "Ana");

            assertThat(pedidos).extracting(Pedido::id).containsExactly(1L, 2L, 3L, 4L, 5L, 6L);
        }
    }

    @Nested
    @DisplayName("2. totalFacturado")
    class TotalFacturado {

        @Test
        @DisplayName("suma los totales sin contar los pedidos cancelados")
        void sinCancelados() {
            // 65.50 + 180.00 + 46.50 + 44.50 + 0 (el pedido 4, de 250.00, está cancelado)
            assertThat(EjerciciosStreams.totalFacturado(pedidos)).isEqualByComparingTo("336.50");
        }

        @Test
        @DisplayName("una lista vacía factura 0")
        void listaVacia() {
            assertThat(EjerciciosStreams.totalFacturado(List.of())).isEqualByComparingTo(BigDecimal.ZERO);
        }
    }

    @Nested
    @DisplayName("3. totalPorCliente")
    class TotalPorCliente {

        @Test
        @DisplayName("agrupa por cliente y suma sus pedidos no cancelados")
        void agrupa() {
            var resultado = EjerciciosStreams.totalPorCliente(pedidos);

            assertThat(resultado).hasSize(2);
            assertThat(resultado.get("Ana")).isEqualByComparingTo("112.00");
            assertThat(resultado.get("Luis")).isEqualByComparingTo("224.50");
        }

        @Test
        @DisplayName("un cliente que solo tiene pedidos cancelados no aparece")
        void soloCancelados() {
            assertThat(EjerciciosStreams.totalPorCliente(pedidos)).doesNotContainKey("Marta");
        }
    }

    @Nested
    @DisplayName("4. productoMasVendido")
    class ProductoMasVendido {

        @Test
        @DisplayName("es el que suma más unidades en pedidos no cancelados")
        void masUnidades() {
            // Ratón: 1 + 3 + 1 = 5. Teclado tendría 12 si contaras el pedido cancelado.
            assertThat(EjerciciosStreams.productoMasVendido(pedidos)).contains("Ratón");
        }

        @Test
        @DisplayName("devuelve Optional vacío si no hay líneas")
        void sinLineas() {
            assertThat(EjerciciosStreams.productoMasVendido(List.of())).isEmpty();
            assertThat(EjerciciosStreams.productoMasVendido(List.of(PedidosDePrueba.pedido6()))).isEmpty();
        }
    }

    @Nested
    @DisplayName("5. contarPorEstado")
    class ContarPorEstado {

        @Test
        @DisplayName("cuenta los pedidos de cada estado")
        void cuenta() {
            assertThat(EjerciciosStreams.contarPorEstado(pedidos)).containsOnly(
                    entry(EstadoPedido.PAGADO, 2L),
                    entry(EstadoPedido.ENVIADO, 1L),
                    entry(EstadoPedido.ENTREGADO, 1L),
                    entry(EstadoPedido.CANCELADO, 1L),
                    entry(EstadoPedido.PENDIENTE, 1L));
        }

        @Test
        @DisplayName("no incluye estados sin pedidos")
        void soloEstadosPresentes() {
            var soloAna = EjerciciosStreams.pedidosDeCliente(pedidos, "Ana");

            assertThat(EjerciciosStreams.contarPorEstado(soloAna)).containsOnly(
                    entry(EstadoPedido.PAGADO, 2L),
                    entry(EstadoPedido.ENTREGADO, 1L));
        }
    }
}
