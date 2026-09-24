package com.aimax.order_management_api.sandbox;

import static com.aimax.order_management_api.sandbox.PedidosDePrueba.linea;
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

// Tests unitarios puros: sin @SpringBootTest, no hace falta arrancar Spring ni la base de datos.
class RecordTests {

    @Nested
    @DisplayName("LineaPedido")
    class LineaPedidoTests {

        @Test
        @DisplayName("un record genera equals, hashCode y toString a partir de sus componentes")
        void equalsHashCodeToString() {
            var a = linea("Teclado", 2, "25.00");
            var b = linea("Teclado", 2, "25.00");

            assertThat(a).isEqualTo(b);
            assertThat(a.hashCode()).isEqualTo(b.hashCode());
            assertThat(a.toString()).contains("producto=Teclado", "cantidad=2");
        }

        @Test
        @DisplayName("los accesores se llaman como el componente, sin 'get'")
        void accesores() {
            var l = linea("Ratón", 3, "15.50");

            assertThat(l.producto()).isEqualTo("Ratón");
            assertThat(l.cantidad()).isEqualTo(3);
            assertThat(l.precioUnitario()).isEqualByComparingTo("15.50");
        }

        @Test
        @DisplayName("subtotal = cantidad x precio unitario")
        void subtotal() {
            assertThat(linea("Teclado", 2, "25.00").subtotal()).isEqualByComparingTo("50.00");
            assertThat(linea("Cable HDMI", 4, "7.25").subtotal()).isEqualByComparingTo("29.00");
        }

        @ParameterizedTest(name = "cantidad {0} no es válida")
        @ValueSource(ints = {0, -1, -100})
        @DisplayName("rechaza cantidades menores o iguales a 0")
        void cantidadInvalida(int cantidad) {
            assertThatThrownBy(() -> linea("Teclado", cantidad, "25.00"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest(name = "producto \"{0}\" no es válido")
        @ValueSource(strings = {"", "   "})
        @DisplayName("rechaza un producto en blanco")
        void productoEnBlanco(String producto) {
            assertThatThrownBy(() -> linea(producto, 1, "25.00"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("rechaza un producto null")
        void productoNull() {
            assertThatThrownBy(() -> new LineaPedido(null, 1, BigDecimal.TEN))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("rechaza un precio negativo o null")
        void precioInvalido() {
            assertThatThrownBy(() -> linea("Teclado", 1, "-0.01"))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> new LineaPedido("Teclado", 1, null))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("acepta un precio 0 (producto de regalo)")
        void precioCero() {
            assertThat(linea("Pegatina", 1, "0").subtotal()).isEqualByComparingTo("0");
        }
    }

    @Nested
    @DisplayName("Pedido")
    class PedidoTests {

        @Test
        @DisplayName("total = suma de los subtotales de las líneas")
        void total() {
            assertThat(PedidosDePrueba.pedido1().total()).isEqualByComparingTo("65.50");
        }

        @Test
        @DisplayName("un pedido sin líneas vale 0")
        void totalSinLineas() {
            assertThat(PedidosDePrueba.pedido6().total()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("cliente, fecha y estado son obligatorios")
        void camposObligatorios() {
            var hoy = LocalDate.now();
            List<LineaPedido> lineas = List.of();

            assertThatThrownBy(() -> new Pedido(1, null, hoy, EstadoPedido.PAGADO, lineas))
                    .isInstanceOf(NullPointerException.class);
            assertThatThrownBy(() -> new Pedido(1, "Ana", null, EstadoPedido.PAGADO, lineas))
                    .isInstanceOf(NullPointerException.class);
            assertThatThrownBy(() -> new Pedido(1, "Ana", hoy, null, lineas))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("modificar la lista original no cambia el pedido (copia defensiva)")
        void copiaDefensiva() {
            var lineas = new ArrayList<LineaPedido>();
            lineas.add(linea("Teclado", 1, "25.00"));
            var pedido = new Pedido(1, "Ana", LocalDate.now(), EstadoPedido.PENDIENTE, lineas);

            lineas.add(linea("Monitor", 1, "180.00"));

            assertThat(pedido.lineas()).hasSize(1);
            assertThat(pedido.total()).isEqualByComparingTo("25.00");
        }

        @Test
        @DisplayName("las líneas del pedido no se pueden modificar desde fuera")
        void lineasInmodificables() {
            var pedido = PedidosDePrueba.pedido1();

            assertThatThrownBy(() -> pedido.lineas().add(linea("Monitor", 1, "180.00")))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
