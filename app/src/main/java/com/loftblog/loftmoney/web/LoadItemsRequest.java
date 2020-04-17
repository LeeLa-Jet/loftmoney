package com.loftblog.loftmoney.web;

import com.loftblog.loftmoney.web.models.ItemRemote;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoadItemsRequest {
    @GET("./items")
    Single<List<ItemRemote>> request(@Query("type") String string, @Query("auth-token") String authToken);
}
