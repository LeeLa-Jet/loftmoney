package com.loftblog.loftmoney.web;

import com.loftblog.loftmoney.web.models.GetItemsResponseModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoadItemsRequest {
    @GET("./items")
    Single<GetItemsResponseModel> request(@Query("type") String string);
}
