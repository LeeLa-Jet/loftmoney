package com.loftblog.loftmoney.web;

import io.reactivex.Completable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RemoveItemRequest {
    @POST("./items/remove")
    @FormUrlEncoded
    Completable request(@Field("id") String id, @Field("auth-token") String authToken);
}
