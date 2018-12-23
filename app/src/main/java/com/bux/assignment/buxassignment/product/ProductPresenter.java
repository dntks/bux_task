/*
 * Copyright 2016, The Android Open Source Project
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

import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

final class ProductPresenter implements ProductContract.Presenter {


    OkHttpClient okHttpClient = new OkHttpClient();

    @Nullable
    private ProductContract.View productView;

    private String baseUrl = "http://10.0.2.2:8080/";

    public ProductPresenter(ProductContract.View productView) {
        this.productView = productView;
        productView.setPresenter(this);
        Observable.fromCallable(new Callable<ServiceProduct>() {
            @Override
            public ServiceProduct call() throws Exception {
                return callServer();
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<ServiceProduct>() {
                @Override
                public void accept(ServiceProduct aBoolean) throws Exception {

                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {

                }
            });
    }

    private ServiceProduct callServer() throws IOException {
        Request request = new Request.Builder()
            .url(baseUrl+"core/21/products/sb26513")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWExMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInNjcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiIsImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg")
            .build();

        Response response = okHttpClient.newCall(request).execute();
        String string = response.body().string();
        Gson gson = new Gson();
        return gson.fromJson(string, ServiceProduct.class);
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
