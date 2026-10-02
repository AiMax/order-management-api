package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A line of an order: which product, how many units and at what price.
 */
public record OrderLine(String product, int quantity, BigDecimal unitPrice) {

    /**
     * TODO (records): compact constructor with validations.
     *  - product must not be null or blank        -> IllegalArgumentException
     *  - quantity must be greater than 0          -> IllegalArgumentException
     *  - unitPrice must not be null or negative   -> IllegalArgumentException
     */
    public OrderLine {

        if (product == null || product.isEmpty()) {throw new IllegalArgumentException();}
        if (quantity <= 0) {throw new IllegalArgumentException();}
        if (unitPrice == null || unitPrice.compareTo(new BigDecimal(0)) < 0) {throw new IllegalArgumentException();}

    }

    /**
     * TODO: quantity * unitPrice.
     */
    public BigDecimal subtotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}
