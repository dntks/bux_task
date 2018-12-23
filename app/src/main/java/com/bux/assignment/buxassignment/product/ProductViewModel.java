package com.bux.assignment.buxassignment.product;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.bux.assignment.buxassignment.websocket.Subscribe;
import com.bux.assignment.buxassignment.websocket.TradingQuote;
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
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ProductViewModel extends ViewModel {

    OkHttpClient okHttpClient = new OkHttpClient();
    private MutableLiveData<Product> productMutableLiveData;

    private String baseUrl = "http://10.0.2.2:8080/";
    private String baseUrlWebSocket = "http://10.0.2.2:8080/";

    public LiveData<Product> getProductMutableLiveData() {
        if (productMutableLiveData == null) {
            productMutableLiveData = new MutableLiveData<Product>();
            loadUsers();
        }
        return productMutableLiveData;
    }

    private void loadUsers() {
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
                public void accept(ServiceProduct serviceProduct) throws Exception {
                    Product product = new Product(serviceProduct.getSecurityId(), serviceProduct.getDisplayName(), serviceProduct.getCurrentPrice(),
                        serviceProduct.getClosingPrice());
                    productMutableLiveData.setValue(product);
                    callServerWebSocket();
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
    private void callServerWebSocket() throws IOException {
        Request request = new Request.Builder()
            .url(baseUrlWebSocket+"subscriptions/me")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWExMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInNjcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiIsImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg")
            .build();

        WebSocketListener listener = new SubscriptionListener(this);
        WebSocket ws = okHttpClient.newWebSocket(request, listener);

        Gson gson = new Gson();
        gson.toJson(new Subscribe("sb26513",""));
//        ws.send()
    }

    public void updateData(TradingQuote tradingQuote){
        Product value = productMutableLiveData.getValue();
        value.updateCurrentPrice(tradingQuote.getCurrentPrice());
        productMutableLiveData.postValue(value);
    }

}
