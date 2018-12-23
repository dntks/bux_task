package com.bux.assignment.buxassignment.product;

import com.bux.assignment.buxassignment.product.updater.ProductUpdater;
import com.bux.assignment.buxassignment.websocket.SubscribeMessage;
import com.bux.assignment.buxassignment.websocket.TradingQuote;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import okhttp3.WebSocket;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionListenerTest {

    @Mock
    private ProductUpdater productUpdater;

    @Mock
    private WebSocket webSocket;

    private SubscribeMessage subscribeMessage;

    private SubscriptionListener subscriptionListener;

    @Before
    public void setup(){
        subscribeMessage = new SubscribeMessage("abc", "def");
        subscriptionListener = new SubscriptionListener(productUpdater, subscribeMessage);
    }

    @Test
    public void onMessage_whenMessageIsConnected_subscribeMessageIsSent() {

        //act
        subscriptionListener.onMessage(webSocket, "{  \"t\": \"connect.connected\" }");

        //assert
        verify(webSocket).send("{\"subscribeTo\":[\"trading.product.abc\"],\"unsubscribeFrom\":[\"trading.product.def\"]}");
    }

    @Test
    public void onMessage_whenMessageIsTradingQuote_updateProductIsCalled() {

        //act
        subscriptionListener.onMessage(webSocket, "{\n" + "   \"t\": \"trading.quote\",\n" + "   \"body\": {\n" + "      \"securityId\": \"abcd\",\n" + "      \"currentPrice\": \"10692.3\"\n" + "   }\n" + "}");

        //assert
        verify(productUpdater).updateProduct(new TradingQuote("abcd", new BigDecimal("10692.3")));
    }

    @Test
    public void onMessage_whenMessageIsError_errorOnUpdateIsCalled() {

        //act
        subscriptionListener.onMessage(webSocket, "{\n" + "    \"t\": \"connect.failed\",\n" + "    \"body\": {\n" + "        \"developerMessage\": \"Missing JWT Access Token in request\",\n" + "        \"errorCode\": \"RTF_002\"\n" + "    }\n" + "}");

        //assert
        verify(productUpdater).errorOnUpdate("RTF_002: Missing JWT Access Token in request");
    }
}