package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Los 5 ejercicios de streams del lunes. Resuélvelos sin bucles for.
 */
public final class EjerciciosStreams {

    private EjerciciosStreams() {
    }

    /**
     * 1. Pedidos de un cliente, ordenados por fecha (el más antiguo primero).
     */
    public static List<Pedido> pedidosDeCliente(List<Pedido> pedidos, String cliente) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 2. Total facturado: suma de los totales de todos los pedidos que NO están cancelados.
     */
    public static BigDecimal totalFacturado(List<Pedido> pedidos) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 3. Total gastado por cada cliente (sin contar pedidos cancelados).
     * Un cliente que solo tiene pedidos cancelados no aparece en el mapa.
     * Pista: Collectors.groupingBy + Collectors.reducing.
     */
    public static Map<String, BigDecimal> totalPorCliente(List<Pedido> pedidos) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 4. Nombre del producto con más unidades vendidas en pedidos no cancelados.
     * Si no hay ninguna línea, Optional.empty().
     * Pista: flatMap sobre las líneas.
     */
    public static Optional<String> productoMasVendido(List<Pedido> pedidos) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 5. Número de pedidos en cada estado.
     * Solo aparecen como clave los estados que tienen al menos un pedido.
     */
    public static Map<EstadoPedido, Long> contarPorEstado(List<Pedido> pedidos) {
        throw new UnsupportedOperationException("TODO");
    }
}
