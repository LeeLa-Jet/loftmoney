package com.loftblog.loftmoney.web.models;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    public static String AUTH_TOKEN_KEY = "auth-token-key";

    private String status;
    private @SerializedName("id") String userId;
    private @SerializedName("auth_token") String authToken;

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuthToken() {
        return authToken;
    }
}
