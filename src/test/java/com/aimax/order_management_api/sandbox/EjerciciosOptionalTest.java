package com.aimax.order_management_api.sandbox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.aimax.order_management_api.sandbox.EjerciciosOptional.PedidoNoEncontradoException;

class EjerciciosOptionalTest {

    private final List<Pedido> pedidos = PedidosDePrueba.todos();

    @Test
    @DisplayName("buscarPorId devuelve el pedido envuelto en un Optional")
    void buscarExistente() {
        var resultado = EjerciciosOptional.buscarPorId(pedidos, 2);

        assertThat(resultado).isPresent();
        assertThat(resultado).hasValueSatisfying(p -> assertThat(p.cliente()).isEqualTo("Luis"));
    }

    @Test
    @DisplayName("buscarPorId devuelve Optional vacío (nunca null) si no existe")
    void buscarInexistente() {
        var resultado = EjerciciosOptional.buscarPorId(pedidos, 99);

        assertThat(resultado).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("clienteDelPedido devuelve el nombre del cliente")
    void clienteExistente() {
        assertThat(EjerciciosOptional.clienteDelPedido(pedidos, 4)).isEqualTo("Marta");
    }

    @Test
    @DisplayName("clienteDelPedido devuelve 'Cliente desconocido' si el pedido no existe")
    void clienteInexistente() {
        assertThat(EjerciciosOptional.clienteDelPedido(pedidos, 99)).isEqualTo("Cliente desconocido");
    }

    @Test
    @DisplayName("obtenerPedido devuelve el pedido si existe")
    void obtenerExistente() {
        assertThat(EjerciciosOptional.obtenerPedido(pedidos, 1)).isEqualTo(PedidosDePrueba.pedido1());
    }

    @Test
    @DisplayName("obtenerPedido lanza PedidoNoEncontradoException si no existe")
    void obtenerInexistente() {
        assertThatThrownBy(() -> EjerciciosOptional.obtenerPedido(pedidos, 99))
                .isInstanceOf(PedidoNoEncontradoException.class)
                .hasMessage("Pedido 99 no encontrado");
    }
}
