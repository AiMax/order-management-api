package com.aimax.order_management_api.sandbox;

/**
 * Text blocks ("""...""") y String.formatted().
 */
public final class EjerciciosTextBlocks {

    private EjerciciosTextBlocks() {
    }

    /**
     * Resumen del pedido con este formato exacto (total con 2 decimales, termina en salto de línea):
     *
     *   Pedido #1
     *   Cliente: Ana
     *   Estado: PAGADO
     *   Líneas: 2
     *   Total: 45.50 €
     */
    public static String resumen(Pedido pedido) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * El pedido como JSON (sin las líneas), con este formato exacto
     * (sangría de 2 espacios, termina en salto de línea después de la llave):
     *
     *   {
     *     "id": 1,
     *     "cliente": "Ana",
     *     "estado": "PAGADO",
     *     "total": 45.50
     *   }
     */
    public static String aJson(Pedido pedido) {
        throw new UnsupportedOperationException("TODO");
    }
}
