package com.bux.assignment.buxassignment.product.updater;

import com.bux.assignment.buxassignment.websocket.TradingQuote;

public interface ProductUpdater {

    void updateProduct(TradingQuote tradingQuote);
}
