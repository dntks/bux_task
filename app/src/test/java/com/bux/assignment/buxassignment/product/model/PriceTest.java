package com.bux.assignment.buxassignment.product.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PriceTest {

    @Test
    public void toString_withCurrencyAndValue_returnsCorrectValue() {
        Price price = new Price("USD", 2, new BigDecimal("555.65"));

        Assert.assertEquals("555.65 USD", price.toString());
    }
}