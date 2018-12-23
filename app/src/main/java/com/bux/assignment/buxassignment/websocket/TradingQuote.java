package com.bux.assignment.buxassignment.websocket;

import com.google.common.base.Objects;

import java.math.BigDecimal;

public class TradingQuote {
    private final String securityId;
    private final BigDecimal currentPrice;

    public TradingQuote(String securityId, BigDecimal currentPrice) {
        this.securityId = securityId;
        this.currentPrice = currentPrice;
    }

    public String getSecurityId() {
        return securityId;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradingQuote that = (TradingQuote) o;
        return Objects.equal(securityId, that.securityId) && Objects.equal(currentPrice, that.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(securityId, currentPrice);
    }
}
