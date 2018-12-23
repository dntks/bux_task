package com.bux.assignment.buxassignment.websocket;

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
}
