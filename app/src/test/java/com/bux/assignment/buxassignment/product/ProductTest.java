package com.bux.assignment.buxassignment.product;

import com.bux.assignment.buxassignment.product.model.Price;
import com.bux.assignment.buxassignment.product.model.Product;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest {

    @Test
    public void createProduct_withCurrentPriceBiggerThanClosingPrice_productHasCorrectDifference() {
        // act
        Price currentPrice = new Price("USD", 2, new BigDecimal("200.0000"));
        Price previousDayClosingPrice = new Price("USD", 2, new BigDecimal("150.0000"));
        Product product = new Product("id", "Apple", currentPrice, previousDayClosingPrice);

        // assert
        Assert.assertEquals("33.33%", product.getPriceDifferenceInPercentage());
    }


    @Test
    public void createProduct_withCurrentPriceSmallerThanClosingPrice_productHasCorrectDifference() {
        // act
        Price currentPrice = new Price("USD", 2, new BigDecimal("100.0000"));
        Price previousDayClosingPrice = new Price("USD", 2, new BigDecimal("150.0000"));
        Product product = new Product("id", "Apple", currentPrice, previousDayClosingPrice);

        // assert
        Assert.assertEquals("-33.33%", product.getPriceDifferenceInPercentage());
    }

}