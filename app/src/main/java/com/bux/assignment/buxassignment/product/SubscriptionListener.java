package com.bux.assignment.buxassignment.product;

import android.util.Log;

import com.bux.assignment.buxassignment.websocket.Event;
import com.bux.assignment.buxassignment.websocket.Subscribe;
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

    private final Gson gson = new Gson();
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private final ProductViewModel productViewModel;

    public SubscriptionListener(ProductViewModel productViewModel) {
        this.productViewModel = productViewModel;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d("WEBSOCKET","Receiving : " + text);
        WebSocketResponse response = gson.fromJson(text, WebSocketResponse.class);
        if(response.getEvent() == Event.CONNECTED){

            Gson gson = new Gson();
            String subscribeMessage = gson.toJson(new Subscribe("sb26513", "q"));
            webSocket.send(subscribeMessage);
        } else if(response.getEvent() == Event.TRADING_QUOTE){

            GsonBuilder gson = new GsonBuilder();
            Type collectionType = new TypeToken<WebSocketResponse<TradingQuote>>(){}.getType();

            WebSocketResponse<TradingQuote> tradingQuoteWebSocketResponse = gson.create().fromJson(text, collectionType);

            productViewModel.updateData(tradingQuoteWebSocketResponse.getBody());
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d("WEBSOCKET","Receiving bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        Log.d("WEBSOCKET","Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d("WEBSOCKET","Error : " + t.getMessage());
    }
}
