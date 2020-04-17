package com.loftblog.loftmoney.web;

import io.reactivex.Completable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostItemRequest {
    @POST("./items/add")
    @FormUrlEncoded
    Completable request(@Field("price") Integer price,
                        @Field("name") String name, @Field("type") String type, @Field("auth-token") String authToken);
}
