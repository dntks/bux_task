/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bux.assignment.buxassignment.product;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bux.assignment.buxassignment.R;
import com.bux.assignment.buxassignment.error.BuxError;
import com.bux.assignment.buxassignment.product.model.Product;

public class ProductActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "productId";

    public static final String SHARED_PREFS = "SHARED_PREFS";

    public static final String LAST_PRODUCT_ID = "lastProductId";

    private TextView productNameTextView;
    private TextView productIdTextView;
    private TextView currentPriceTextView;
    private TextView closingPriceTextView;
    private TextView priceDifferenceTextView;
    private View productView;
    private View progressView;
    private TextView errorTextView;

    public static Intent getLaunchIntent(@NonNull Context context, @NonNull String productId) {
        final Intent entryActivityIntent = new Intent(context, ProductActivity.class);
        entryActivityIntent.putExtra(PRODUCT_ID, productId);
        return entryActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String productId = getIntent().getStringExtra("productId");

        initViews();

        initModel(productId);
    }

    private void initViews() {
        setContentView(R.layout.product_act);
        productNameTextView = findViewById(R.id.product_name_value);
        productIdTextView = findViewById(R.id.product_identifier_value);
        currentPriceTextView = findViewById(R.id.product_currentprice_value);
        closingPriceTextView = findViewById(R.id.product_closingprice_value);
        priceDifferenceTextView = findViewById(R.id.product_pricedifference_value);
        productView = findViewById(R.id.product_layout);
        progressView = findViewById(R.id.indeterminate_bar);
        progressView = findViewById(R.id.indeterminate_bar);
        errorTextView = findViewById(R.id.error_text);
    }

    private void initModel(String productId) {
        ProductViewModel model = ViewModelProviders.of(this).get(ProductViewModel.class);
        model.setProductErrorListener(createProductErrorListener());
        model.getProductMutableLiveData(productId, getPreviousId(productId)).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {
                updateViewContent(product);
            }
        });
    }

    private void updateViewContent(@Nullable Product product) {
        progressView.setVisibility(View.GONE);
        productView.setVisibility(View.VISIBLE);
        productNameTextView.setText(product.getProductName());
        productIdTextView.setText(product.getIdentifier());
        currentPriceTextView.setText(product.getCurrentPrice().toString());
        closingPriceTextView.setText(product.getPreviousDayClosingPrice().toString());
        priceDifferenceTextView.setText(product.getPriceDifferenceInPercentage());
    }

    @NonNull
    private ProductErrorListener createProductErrorListener() {
        return new ProductErrorListener(){
            @Override
            public void onProductError() {
                showErrorView();
            }

            @Override
            public void onProductError(String customString) {
                showErrorView();
                errorTextView.setText(customString);
            }

            @Override
            public void onProductError(BuxError buxError) {
                showErrorView();
                errorTextView.setText(buxError.getMessage());
            }
        };
    }

    private void showErrorView() {
        progressView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    private String getPreviousId(String productId) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String lastId = sharedPreferences.getString(LAST_PRODUCT_ID, "");
        Log.d("Got previous id:", lastId);
        if(productId.equals(lastId)){
            //We don't want to unsubscribe if the last is the same as the current
            return "";
        } else{
            saveId(productId, sharedPreferences);
            return lastId;
        }
    }

    private void saveId(String productId, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_PRODUCT_ID, productId);
        editor.commit();
    }
}
