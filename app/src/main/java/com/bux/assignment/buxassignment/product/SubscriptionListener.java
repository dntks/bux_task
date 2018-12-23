package com.bux.assignment.buxassignment.product;

import android.util.Log;

import com.bux.assignment.buxassignment.product.updater.ProductUpdater;
import com.bux.assignment.buxassignment.websocket.Event;
import com.bux.assignment.buxassignment.websocket.SubscribeMessage;
import com.bux.assignment.buxassignment.websocket.TradingQuote;
import com.bux.assignment.buxassignment.websocket.WebSocketResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

class SubscriptionListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;

    public static final String WEBSOCKET = "WEBSOCKET";

    private final Gson gson;
    private final GsonBuilder gsonBuilder;

    private final ProductUpdater productUpdater;
    private final SubscribeMessage subscribeMessage;

    public SubscriptionListener(ProductUpdater productUpdater, SubscribeMessage subscribeMessage) {
        this.productUpdater = productUpdater;
        this.subscribeMessage = subscribeMessage;
        gson = new Gson();
        gsonBuilder = new GsonBuilder();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d(WEBSOCKET,"Opened.");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        WebSocketResponse response = gson.fromJson(text, WebSocketResponse.class);
        if(response.getEvent() == Event.CONNECTED){
            String subscribeMessageJson = gson.toJson(subscribeMessage);
            webSocket.send(subscribeMessageJson);
        } else if(response.getEvent() == Event.TRADING_QUOTE){
            Type collectionType = new TypeToken<WebSocketResponse<TradingQuote>>(){}.getType();
            WebSocketResponse<TradingQuote> tradingQuoteWebSocketResponse = gsonBuilder.create().fromJson(text, collectionType);
            productUpdater.updateProduct(tradingQuoteWebSocketResponse.getBody());
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d(WEBSOCKET,"Receiving bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, reason);
        Log.d(WEBSOCKET,"Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d(WEBSOCKET,"Error : " + t.getMessage());
    }
}
