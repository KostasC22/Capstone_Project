package com.havistudio.myredditcp.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kostas on 14/10/2016.
 */

public class UserAgentInterceptor implements Interceptor {

    private final String userAgent;

    public UserAgentInterceptor(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        Request requestWithUserAgent = originRequest.newBuilder()
                .header("User-Agent", "MyRedditCleint/0.1 by "+userAgent)
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}