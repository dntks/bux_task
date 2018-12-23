package com.bux.assignment.buxassignment.websocket;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Subscribe {
    private static final String SUBSCRIBE_FORMAT = "trading.product.%s";
    private final List<String> subscribeTo;
    private final List<String> unsubscribeFrom;

    public Subscribe(List<String> subscribeTo, List<String> unsubscribeFrom) {
        this.subscribeTo = subscribeTo;
        this.unsubscribeFrom = unsubscribeFrom;
    }

    public Subscribe(String subscribeTo, String unsubscribeFrom) {
        this.subscribeTo = newArrayList(String.format(SUBSCRIBE_FORMAT, subscribeTo));
        this.unsubscribeFrom = newArrayList(String.format(SUBSCRIBE_FORMAT, unsubscribeFrom));
    }

    public List<String> getSubscribeTo() {
        return subscribeTo;
    }

    public List<String> getUnsubscribeFrom() {
        return unsubscribeFrom;
    }
}
