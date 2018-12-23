package com.bux.assignment.buxassignment.product;

import com.bux.assignment.buxassignment.error.BuxError;

public interface ProductErrorListener {
    void onProductError();
    void onProductError(String customString);
    void onProductError(BuxError buxError);
}
