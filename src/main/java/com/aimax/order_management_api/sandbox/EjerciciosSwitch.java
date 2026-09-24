package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;

/**
 * Switch moderno: switch como expresión, flechas "->" y pattern matching.
 */
public final class EjerciciosSwitch {

    private EjerciciosSwitch() {
    }

    /**
     * Texto para el cliente según el estado (switch expression, sin break):
     *  PENDIENTE -> "Pendiente de pago"
     *  PAGADO    -> "Preparando tu pedido"
     *  ENVIADO   -> "En camino"
     *  ENTREGADO -> "Entregado"
     *  CANCELADO -> "Cancelado"
     */
    public static String descripcion(EstadoPedido estado) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Un pedido solo se puede cancelar si está PENDIENTE o PAGADO.
     * Pista: varias constantes en un mismo case (case A, B -> ...).
     */
    public static boolean puedeCancelarse(EstadoPedido estado) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Comisión que cobra cada método de pago sobre el importe (pattern matching en switch):
     *  Tarjeta       -> 1,5 % del importe
     *  Transferencia -> 0 (gratis)
     *  Bizum         -> 0,20 € fijos
     * Devuelve el resultado con 2 decimales (redondeo HALF_UP).
     */
    public static BigDecimal comision(MetodoPago metodo, BigDecimal importe) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Texto del método de pago usando el valor que lleva dentro cada record
     * (pista: record patterns, case Tarjeta(var digitos) -> ...):
     *  Tarjeta("1234")          -> "Tarjeta acabada en 1234"
     *  Transferencia("ES12...") -> "Transferencia desde ES12..."
     *  Bizum("600111222")       -> "Bizum al 600111222"
     */
    public static String describir(MetodoPago metodo) {
        throw new UnsupportedOperationException("TODO");
    }
}
