package com.havistudio.myredditcp.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by kostas on 24/12/2015.
 */
public interface MyApiRetrofit {

    // NO authorization
    @POST("/api/v1/authorize")
    Call<Authorize> authorize(@QueryMap Map<String, String> options);

    @POST("/api/v1/access_token")
    @FormUrlEncoded
    Call<Authorize> accessToken(@Field("grant_type") String grantType, @Field("username") String username, @Field("password") String password);

    @POST("/api/v1/access_token")
    @FormUrlEncoded
    Call<Authorize> accessToken2(@Field(("client_id")) String clientid, @Field(("response_type")) String username, @Field(("state")) String password, @Field(("redirect_uri")) String redirect_uri, @Field(("scope")) String scope);

    @POST("/api/v1/access_token")
    @FormUrlEncoded
    Call<Authorize> accessToken3(@Field("grant_type") String grantType, @Field("device_id") String device_id);

    @GET("api/v1/me/")
    Call<Authorize> retrieveMyInfo();

    @GET("/subreddits/mine/subscriber")
    Call<Authorize> getMySubcriptions();

    // NO authorization
    @POST("/api/v1/authorize.compact")
    Call<Authorize> authorize2(@QueryMap Map<String, String> options);

    // NO authorization
    @GET("r/subreddits/new.json")
    Call<Example> getNewSubreddits(@QueryMap Map<String, String> options);

    @GET("r/pics/new.json")
    Call<Example> getNewPhotoSubreddits(@QueryMap Map<String, String> options);

    // NO authorization
    @GET("r/subreddit/new.json")
    Call<Example> getNewSubredditsA(@QueryMap Map<String, String> options);

    // NO authorization
    // https://www.reddit.com/r/pics/comments/5bm0ty.json?
    @GET("r/pics/comments/{subredditid}.json")
    Call<Comments[]> getCommentsBySubredditId(@Path("subredditid") String subredditid);

    // login
    //https://ssl.reddit.com/api/login  <-- this url is for the login

    // Authorization need
    //@GET("3/movie/{id}/reviews")
    //Call<ReviewJson> getReviews(@Path("id") long id, @QueryMap Map<String, String> options);
}
