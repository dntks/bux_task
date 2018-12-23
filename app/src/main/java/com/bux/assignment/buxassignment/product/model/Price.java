package com.bux.assignment.buxassignment.product.model;

import java.math.BigDecimal;

public class Price {

    private final String currency;

    private final int decimals;

    private BigDecimal amount;

    public Price(String currency, int decimals, BigDecimal amount) {
        this.currency = currency;
        this.decimals = decimals;
        this.amount = amount;
    }

    public Price cloneWithNewAmount(BigDecimal newAmount){
        return new Price(currency, decimals, newAmount);
    }

    public String getCurrency() {
        return currency;
    }

    public int getDecimals() {
        return decimals;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return amount.toPlainString() + " " + currency;
    }

    public void setValue(BigDecimal currentPrice) {
        this.amount = currentPrice;
    }
}
