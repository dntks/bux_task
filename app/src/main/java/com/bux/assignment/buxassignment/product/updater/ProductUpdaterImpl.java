package com.bux.assignment.buxassignment.product.updater;

import android.arch.lifecycle.MutableLiveData;

import com.bux.assignment.buxassignment.product.model.Product;
import com.bux.assignment.buxassignment.websocket.TradingQuote;

public class ProductUpdaterImpl implements ProductUpdater {

    private MutableLiveData<Product> productMutableLiveData;

    public ProductUpdaterImpl(MutableLiveData<Product> productMutableLiveData) {
        this.productMutableLiveData = productMutableLiveData;
    }

    @Override
    public void updateProduct(TradingQuote tradingQuote){
        Product value = productMutableLiveData.getValue();
        productMutableLiveData.postValue(value.cloneWithNewCurrentPrice(tradingQuote));
    }
}
