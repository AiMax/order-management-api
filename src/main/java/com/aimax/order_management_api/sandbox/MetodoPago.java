package com.aimax.order_management_api.sandbox;

/**
 * Interfaz sellada: solo estos tres records pueden implementarla.
 * Así el compilador sabe que un switch sobre MetodoPago está completo sin "default".
 */
public sealed interface MetodoPago permits MetodoPago.Tarjeta, MetodoPago.Transferencia, MetodoPago.Bizum {

    record Tarjeta(String ultimos4Digitos) implements MetodoPago {
    }

    record Transferencia(String iban) implements MetodoPago {
    }

    record Bizum(String telefono) implements MetodoPago {
    }
}
