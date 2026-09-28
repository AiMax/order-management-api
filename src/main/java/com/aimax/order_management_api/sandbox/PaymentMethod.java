package com.aimax.order_management_api.sandbox;

/**
 * Sealed interface: only these three records can implement it.
 * That way the compiler knows a switch over PaymentMethod is exhaustive without a "default".
 */
public sealed interface PaymentMethod permits PaymentMethod.Card, PaymentMethod.BankTransfer, PaymentMethod.Bizum {

    record Card(String last4Digits) implements PaymentMethod {
    }

    record BankTransfer(String iban) implements PaymentMethod {
    }

    record Bizum(String phone) implements PaymentMethod {
    }
}
