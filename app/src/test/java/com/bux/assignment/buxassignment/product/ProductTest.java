package com.bux.assignment.buxassignment.product;

import com.bux.assignment.buxassignment.product.model.Price;
import com.bux.assignment.buxassignment.product.model.Product;
import com.bux.assignment.buxassignment.websocket.TradingQuote;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest {

    @Test
    public void createProduct_withCurrentPriceBiggerThanClosingPrice_productHasCorrectDifference() {
        //arrange
        Price currentPrice = new Price("USD", 2, new BigDecimal("200.0000"));
        Price previousDayClosingPrice = new Price("USD", 2, new BigDecimal("150.0000"));

        // act
        Product product = new Product("id", "Apple", currentPrice, previousDayClosingPrice);

        // assert
        Assert.assertEquals("33.33%", product.getPriceDifferenceInPercentage());
    }

    @Test
    public void createProduct_withCurrentPriceSmallerThanClosingPrice_productHasCorrectDifference() {
        //arrange
        Price currentPrice = new Price("USD", 2, new BigDecimal("100.0000"));
        Price previousDayClosingPrice = new Price("USD", 2, new BigDecimal("150.0000"));

        // act
        Product product = new Product("id", "Apple", currentPrice, previousDayClosingPrice);

        // assert
        Assert.assertEquals("-33.33%", product.getPriceDifferenceInPercentage());
    }

    @Test
    public void cloneWith() {
        //arrange
        TradingQuote tradingQuote = new TradingQuote("id", new BigDecimal("222.99"));
        Price currentPrice = new Price("USD", 2, new BigDecimal("100.0000"));
        Price previousDayClosingPrice = new Price("USD", 2, new BigDecimal("150.0000"));
        Product product = new Product("id", "Apple", currentPrice, previousDayClosingPrice);

        // act
        Product actual = product.cloneWithNewCurrentPrice(tradingQuote);

        // assert
        Assert.assertEquals("Apple", actual.getProductName());
        Assert.assertEquals("222.99 USD", actual.getCurrentPrice().toString());
    }

}