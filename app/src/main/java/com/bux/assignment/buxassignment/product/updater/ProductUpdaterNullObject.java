package com.bux.assignment.buxassignment.product.updater;

import com.bux.assignment.buxassignment.websocket.TradingQuote;

public class ProductUpdaterNullObject implements ProductUpdater {

    @Override
    public void updateProduct(TradingQuote tradingQuote) {
        //null object not doing anything
    }

    @Override
    public void errorOnUpdate(String buxError) {
        //null object not doing anything
    }
}
