package com.bux.assignment.buxassignment.product;

public class ServiceProduct {

    private String securityId;
    private String displayName;
    private String symbol;
    private Price currentPrice;
    private Price closingPrice;

    public ServiceProduct(String securityId, String displayName, String symbol, Price currentPrice, Price closingPrice) {
        this.securityId = securityId;
        this.displayName = displayName;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.closingPrice = closingPrice;
    }

    public String getSecurityId() {
        return securityId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public Price getClosingPrice() {
        return closingPrice;
    }
}
