package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Un pedido de un cliente con sus líneas.
 */
public record Pedido(long id, String cliente, LocalDate fecha, EstadoPedido estado, List<LineaPedido> lineas) {

    /**
     * TODO (records): constructor compacto.
     *  - cliente, fecha y estado no pueden ser null  -> NullPointerException (pista: Objects.requireNonNull)
     *  - lineas: guarda una copia inmodificable (pista: List.copyOf)
     *    para que el record sea inmutable de verdad.
     */
    public Pedido {
    }

    /**
     * TODO (streams): suma de los subtotales de todas las líneas.
     * Un pedido sin líneas vale BigDecimal.ZERO.
     */
    public BigDecimal total() {
        throw new UnsupportedOperationException("TODO");
    }
}
