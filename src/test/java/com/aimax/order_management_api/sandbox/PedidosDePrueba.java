package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Lista de pedidos inventada que comparten todos los tests.
 *
 * <pre>
 * id  cliente  fecha       estado     líneas                                  total
 * 1   Ana      2026-09-01  PAGADO     2 Teclado x 25.00 + 1 Ratón x 15.50      65.50
 * 2   Luis     2026-09-03  ENVIADO    1 Monitor x 180.00                      180.00
 * 3   Ana      2026-08-20  ENTREGADO  3 Ratón x 15.50                          46.50
 * 4   Marta    2026-09-05  CANCELADO  10 Teclado x 25.00                      250.00
 * 5   Luis     2026-09-10  PENDIENTE  4 Cable HDMI x 7.25 + 1 Ratón x 15.50    44.50
 * 6   Ana      2026-09-15  PAGADO     (sin líneas)                              0.00
 * </pre>
 */
final class PedidosDePrueba {

    private PedidosDePrueba() {
    }

    static LineaPedido linea(String producto, int cantidad, String precio) {
        return new LineaPedido(producto, cantidad, new BigDecimal(precio));
    }

    static Pedido pedido1() {
        return new Pedido(1, "Ana", LocalDate.of(2026, 9, 1), EstadoPedido.PAGADO,
                List.of(linea("Teclado", 2, "25.00"), linea("Ratón", 1, "15.50")));
    }

    static Pedido pedido6() {
        return new Pedido(6, "Ana", LocalDate.of(2026, 9, 15), EstadoPedido.PAGADO, List.of());
    }

    static List<Pedido> todos() {
        return List.of(
                pedido1(),
                new Pedido(2, "Luis", LocalDate.of(2026, 9, 3), EstadoPedido.ENVIADO,
                        List.of(linea("Monitor", 1, "180.00"))),
                new Pedido(3, "Ana", LocalDate.of(2026, 8, 20), EstadoPedido.ENTREGADO,
                        List.of(linea("Ratón", 3, "15.50"))),
                new Pedido(4, "Marta", LocalDate.of(2026, 9, 5), EstadoPedido.CANCELADO,
                        List.of(linea("Teclado", 10, "25.00"))),
                new Pedido(5, "Luis", LocalDate.of(2026, 9, 10), EstadoPedido.PENDIENTE,
                        List.of(linea("Cable HDMI", 4, "7.25"), linea("Ratón", 1, "15.50"))),
                pedido6()
        );
    }
}
