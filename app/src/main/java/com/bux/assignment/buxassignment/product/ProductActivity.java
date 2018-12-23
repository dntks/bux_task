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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bux.assignment.buxassignment.R;

public class ProductActivity extends AppCompatActivity {

    TextView productName;
    TextView productId;
    TextView currentPrice;
    TextView closingPrice;
    TextView priceDifference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_act);
        productName = findViewById(R.id.product_name_value);
        productId = findViewById(R.id.product_identifier_value);
        currentPrice = findViewById(R.id.product_currentprice_value);
        closingPrice = findViewById(R.id.product_closingprice_value);
        priceDifference = findViewById(R.id.product_pricedifference_value);


        ProductViewModel model = ViewModelProviders.of(this).get(ProductViewModel.class);
        model.getProductMutableLiveData().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {
                productName.setText(product.getProductName());
                productId .setText(product.getIdentifier());
                currentPrice .setText(product.getCurrentPrice().toString());
                closingPrice .setText(product.getPreviousDayClosingPrice().toString());
                priceDifference .setText(product.getPriceDifferenceInPercentage());
            }
        });
    }
}
