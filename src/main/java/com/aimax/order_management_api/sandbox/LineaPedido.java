package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;

/**
 * Una línea de un pedido: qué producto, cuántas unidades y a qué precio.
 */
public record LineaPedido(String producto, int cantidad, BigDecimal precioUnitario) {

    /**
     * TODO (records): constructor compacto con validaciones.
     *  - producto no puede ser null ni estar en blanco  -> IllegalArgumentException
     *  - cantidad debe ser mayor que 0                  -> IllegalArgumentException
     *  - precioUnitario no puede ser null ni negativo   -> IllegalArgumentException
     */
    public LineaPedido {
    }

    /**
     * TODO: cantidad * precioUnitario.
     */
    public BigDecimal subtotal() {
        throw new UnsupportedOperationException("TODO");
    }
}
