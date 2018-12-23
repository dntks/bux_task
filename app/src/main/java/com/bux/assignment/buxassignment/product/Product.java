package com.bux.assignment.buxassignment.product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {

    private static final String PERCENTAGE_FORMAT = "%s%%";

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private final String identifier;
    private final String productName;
    private final Price currentPrice;
    private final Price previousDayClosingPrice;
    private final BigDecimal priceDifferenceInPercentage;

    public Product(String identifier, String productName, Price currentPrice, Price previousDayClosingPrice) {
        this.identifier = identifier;
        this.productName = productName;
        this.currentPrice = currentPrice;
        this.previousDayClosingPrice = previousDayClosingPrice;
        this.priceDifferenceInPercentage = calculatePriceDifferencePercentage();
    }

    private BigDecimal calculatePriceDifferencePercentage() {
        BigDecimal priceChange = previousDayClosingPrice.getAmount().subtract(currentPrice.getAmount());
        BigDecimal divide = priceChange.multiply(HUNDRED).divide(previousDayClosingPrice.getAmount(), currentPrice.getDecimals(), RoundingMode.HALF_UP);
        return divide.abs();
    }

    public String getProductName() {
        return productName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public Price getPreviousDayClosingPrice() {
        return previousDayClosingPrice;
    }

    public String getPriceDifferenceInPercentage() {
        return String.format(PERCENTAGE_FORMAT, priceDifferenceInPercentage.toPlainString());
    }

    public void updateCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice.setValue(currentPrice);
    }
}
