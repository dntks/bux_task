package com.bux.assignment.buxassignment.product.updater;

import android.arch.lifecycle.MutableLiveData;

import com.bux.assignment.buxassignment.product.ProductErrorListener;
import com.bux.assignment.buxassignment.product.model.Product;
import com.bux.assignment.buxassignment.websocket.TradingQuote;

public class ProductUpdaterImpl implements ProductUpdater {

    private MutableLiveData<Product> productMutableLiveData;

    private ProductErrorListener productErrorListener;

    public ProductUpdaterImpl(MutableLiveData<Product> productMutableLiveData, ProductErrorListener productErrorListener) {
        this.productMutableLiveData = productMutableLiveData;
        this.productErrorListener = productErrorListener;
    }

    @Override
    public void updateProduct(TradingQuote tradingQuote){
        Product value = productMutableLiveData.getValue();
        productMutableLiveData.postValue(value.cloneWithNewCurrentPrice(tradingQuote));
    }

    @Override
    public void errorOnUpdate(String error) {
        if(productErrorListener != null) {
            productErrorListener.onProductError(error);
        }
    }
}
