package com.aimax.order_management_api.sandbox;

import java.util.List;
import java.util.Optional;

/**
 * Ejercicios de Optional. Prohibido usar optional.get() y comprobar null a mano.
 */
public final class EjerciciosOptional {

    private EjerciciosOptional() {
    }

    /**
     * Busca un pedido por id.
     */
    public static Optional<Pedido> buscarPorId(List<Pedido> pedidos, long id) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Devuelve el nombre del cliente del pedido con ese id,
     * o "Cliente desconocido" si no existe.
     * Pista: map + orElse.
     */
    public static String clienteDelPedido(List<Pedido> pedidos, long id) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Devuelve el pedido con ese id o lanza PedidoNoEncontradoException
     * con el mensaje "Pedido 99 no encontrado" (con el id que corresponda).
     * Pista: orElseThrow.
     */
    public static Pedido obtenerPedido(List<Pedido> pedidos, long id) {
        throw new UnsupportedOperationException("TODO");
    }

    public static class PedidoNoEncontradoException extends RuntimeException {
        public PedidoNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }
}
