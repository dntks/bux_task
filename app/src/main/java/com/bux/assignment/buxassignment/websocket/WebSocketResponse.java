package com.bux.assignment.buxassignment.websocket;

public class WebSocketResponse<T> {
    private final Event t;
    private final T body;

    public WebSocketResponse(Event t, T body) {
        this.t = t;
        this.body = body;
    }

    public Event getEvent() {
        return t;
    }

    public T getBody() {
        return body;
    }
}
