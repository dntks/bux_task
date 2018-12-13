package com.bux.assignment.buxassignment.product;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest {

    @Test
    public void createProduct_withCurrentPriceBiggerThanClosingPrice_productHasCorrectDifference() {
        // act
        Product product = new Product("id", new BigDecimal("200.0000"), new BigDecimal("150.0000"));

        // assert
        Assert.assertEquals(new BigDecimal("33.3333"), product.getPriceDifferenceInPercentage());
    }

}