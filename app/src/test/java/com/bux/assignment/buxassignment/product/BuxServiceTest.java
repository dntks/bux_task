package com.bux.assignment.buxassignment.product;

import com.bux.assignment.buxassignment.error.BuxErrorException;
import com.bux.assignment.buxassignment.error.ProductNotFoundException;
import com.bux.assignment.buxassignment.product.model.ServiceProduct;
import com.bux.assignment.buxassignment.product.updater.ProductUpdater;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuxServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private OkHttpClient okHttpClient;

    @Mock
    private Call call;

    @Mock
    private WebSocket webSocket;

    @Mock
    private ProductUpdater productUpdater;

    private BuxService buxService;

    private ResponseBody responseBody;

    private ResponseBody errorBody;

    private Request request;

    @Before
    public void setup() {
        buxService = new BuxService(okHttpClient);
        request = new Request.Builder().url("http://localhost").build();
        responseBody = ResponseBody.create(null, "{   \"symbol\": \"FRANCE40\",   \"securityId\": \"26608\",   \"displayName\": \"French Exchange\",   \"currentPrice\": {      \"currency\": \"EUR\",      \"decimals\": 1,      \"amount\": \"4371.8\"   },   \"closingPrice\": { \"currency\": \"EUR\", \"decimals\": 1, \"amount\": \"4216.4\"} }");
        errorBody = ResponseBody.create(null, "{\n" + "    \"message\": \"may be null\",\n" + "    \"developerMessage\": \"technical description of the error\",\n" + "    \"errorCode\": \"AUTH_001\"\n" + "}");
    }

    @Test
    public void callServer_whenResponseSuccessful_returnsServiceProduct() throws IOException {
        //arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        Response response = new Response.Builder().code(200).request(request).protocol(Protocol.HTTP_2).message("message").body(responseBody).build();
        when(call.execute()).thenReturn(response);

        //act
        ServiceProduct serviceProduct = buxService.callServer("sb2837");

        //assert
        Assert.assertEquals("French Exchange", serviceProduct.getDisplayName());
        Assert.assertEquals("26608", serviceProduct.getSecurityId());
        Assert.assertEquals("FRANCE40", serviceProduct.getSymbol());
        Assert.assertEquals("4371.8", serviceProduct.getCurrentPrice().getAmount().toPlainString());
        Assert.assertEquals("4216.4", serviceProduct.getClosingPrice().getAmount().toPlainString());
    }

    @Test
    public void callServer_whenResponseNotFound_throwsException() throws IOException {
        //arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        Response response = new Response.Builder().code(404).request(request).protocol(Protocol.HTTP_2).message("message").build();
        when(call.execute()).thenReturn(response);

        expectedException.expect(ProductNotFoundException.class);

        //act
        buxService.callServer("sb2837");
    }

    @Test
    public void callServer_whenOtherError_throwsException() throws IOException {
        //arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        Response response = new Response.Builder().code(500).request(request).protocol(Protocol.HTTP_2).message("message").body(errorBody).build();
        when(call.execute()).thenReturn(response);

        expectedException.expect(BuxErrorException.class);

        //act
        buxService.callServer("sb2837");
    }

    @Test
    public void callServerWebSocket_callsOkHttpClientNewWebSocket() {
        //arrange
        when(okHttpClient.newWebSocket(any(Request.class), any(WebSocketListener.class))).thenReturn(webSocket);

        //act
        buxService.callServerWebSocket(productUpdater, "newId", "oldId");

        //assert
        verify(okHttpClient).newWebSocket(any(Request.class), any(WebSocketListener.class));
    }

    @Test
    public void stopWebSocket_whenWebsocketAlreadyCreated_callsCancel() {
        //arrange
        when(okHttpClient.newWebSocket(any(Request.class), any(WebSocketListener.class))).thenReturn(webSocket);
        buxService.callServerWebSocket(productUpdater, "newId", "oldId");

        //act
        buxService.stopWebSocket();

        //assert
        verify(webSocket).cancel();
    }

    @Test
    public void callServerWebSocket_whenWebsocketAlreadyCreated_callsCancel() {
        //arrange
        when(okHttpClient.newWebSocket(any(Request.class), any(WebSocketListener.class))).thenReturn(webSocket);
        buxService.callServerWebSocket(productUpdater, "newId", "oldId");

        //act
        buxService.callServerWebSocket(productUpdater, "newId", "oldId");

        //assert
        verify(webSocket).cancel();
    }

    @Test
    public void stopWebSocket_whenWebsocketNotCreated_doesNotCallCancel() {
        //arrange
        when(okHttpClient.newWebSocket(any(Request.class), any(WebSocketListener.class))).thenReturn(webSocket);

        //act
        buxService.stopWebSocket();

        //assert
        verify(webSocket, never()).cancel();
    }

}