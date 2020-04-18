package com.loftblog.loftmoney.web;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebFactory {

    static WebFactory instance = null;
    private String baseUrl = "https://loftschool.com/android-api/basic/v1/";

    public static WebFactory getInstance() {
        if (instance == null) {
            instance = new WebFactory();
        }
        return instance;
    }

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private WebFactory() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public AuthRequest getAuthRequest() { return retrofit.create(AuthRequest.class); }
    public LoadItemsRequest loadItemsRequest() {
        return retrofit.create(LoadItemsRequest.class);
    }
    public PostItemRequest postItemRequest() { return retrofit.create(PostItemRequest.class); }
    public RemoveItemRequest removeItemRequest() { return retrofit.create(RemoveItemRequest.class); }
}
