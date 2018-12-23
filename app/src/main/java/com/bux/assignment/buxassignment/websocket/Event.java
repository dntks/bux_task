package com.bux.assignment.buxassignment.websocket;

import com.google.gson.annotations.SerializedName;

public enum Event {

    @SerializedName("connect.connected")
    CONNECTED,

    @SerializedName("connect.failed")
    CONNECT_FAILED,

    @SerializedName("trading.quote")
    TRADING_QUOTE;
}
