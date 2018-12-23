package com.bux.assignment.buxassignment.product;

import com.bux.assignment.buxassignment.BuildConfig;
import com.bux.assignment.buxassignment.error.BuxError;
import com.bux.assignment.buxassignment.error.BuxErrorException;
import com.bux.assignment.buxassignment.error.ProductNotFoundException;
import com.bux.assignment.buxassignment.product.model.ServiceProduct;
import com.bux.assignment.buxassignment.product.updater.ProductUpdater;
import com.bux.assignment.buxassignment.websocket.SubscribeMessage;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class BuxService {

    private static final String PRODUCT_ENDPOINT = BuildConfig.API_END_POINT +"core/21/products/%s";
    private static final String RTF_ENDPOINT = BuildConfig.RTF_END_POINT + "subscriptions/me";
    private static final String AUTHORIZATION = "Authorization";

    private final OkHttpClient okHttpClient;

    private WebSocket webSocket;

    public BuxService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public ServiceProduct callServer(String productId) throws IOException {
        //OkHttp retries the request by default
        Request request = new Request.Builder()
            .url(String.format(PRODUCT_ENDPOINT, productId))
            .addHeader(AUTHORIZATION, BuildConfig.AUTHORIZATION)
            .build();

        Response response = okHttpClient.newCall(request).execute();
        Gson gson = new Gson();
        if(response.isSuccessful()){
            return gson.fromJson(response.body().string(), ServiceProduct.class);
        } else if(response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new ProductNotFoundException();
        } else{
            BuxError buxError = gson.fromJson(response.body().string(), BuxError.class);
            throw new BuxErrorException(buxError);
        }
    }

    public void callServerWebSocket(ProductUpdater productUpdater, String productId, String formerId) {
        Request request = new Request.Builder()
            .url(RTF_ENDPOINT)
            .addHeader("Authorization", BuildConfig.AUTHORIZATION)
            .build();

        WebSocketListener listener = new SubscriptionListener(productUpdater, new SubscribeMessage(productId, formerId));
        if(webSocket != null){
            webSocket.cancel();
        }
        webSocket = okHttpClient.newWebSocket(request, listener);
    }

    public void stopWebSocket() {
        if(webSocket != null){
            webSocket.cancel();
        }
    }
}
