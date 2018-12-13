package com.bux.assignment.buxassignment.product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {

    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private final String identifier;
    private final BigDecimal currentPrice;
    private final BigDecimal previousDayClosingPrice;
    private final BigDecimal priceDifferenceInPercentage;

    public Product(String identifier, BigDecimal currentPrice, BigDecimal previousDayClosingPrice) {
        this.identifier = identifier;
        this.currentPrice = currentPrice;
        this.previousDayClosingPrice = previousDayClosingPrice;
        this.priceDifferenceInPercentage = calculatePriceDifferencePercentage();
    }

    private BigDecimal calculatePriceDifferencePercentage() {
        BigDecimal priceChange = previousDayClosingPrice.subtract(currentPrice);
        BigDecimal divide = priceChange.multiply(HUNDRED).divide(previousDayClosingPrice, RoundingMode.HALF_UP);
        return divide.abs();
    }

    public String getIdentifier() {
        return identifier;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public BigDecimal getPreviousDayClosingPrice() {
        return previousDayClosingPrice;
    }

    public BigDecimal getPriceDifferenceInPercentage() {
        return priceDifferenceInPercentage;
    }
}
