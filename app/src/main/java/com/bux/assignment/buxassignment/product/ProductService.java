package com.bux.assignment.buxassignment.product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {

    @GET("core/21/products/{productId}")
    Call<String> getProduct(@Path("productId") String productId);

}
