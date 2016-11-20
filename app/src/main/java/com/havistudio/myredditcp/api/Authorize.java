package com.havistudio.myredditcp.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kostas on 07/09/2016.
 */
public class Authorize implements Parcelable {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private String expiresIn;

    @SerializedName("scope")
    private String scope;

    @SerializedName("refresh_token")
    private String refreshToken;

    public Authorize() {

    }

    public Authorize(String accessToken, String tokenType, String expiresIn, String scope, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.refreshToken = refreshToken;
    }

    public Authorize(Parcel in) {
        this.accessToken = in.readString();
        this.tokenType = in.readString();
        this.expiresIn = in.readString();
        this.scope = in.readString();
        this.refreshToken = in.readString();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "Authorize{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", scope='" + scope + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getAccessToken());
        dest.writeString(getTokenType());
        dest.writeString(getExpiresIn());
        dest.writeString(getScope());
        dest.writeString(getRefreshToken());
    }

    public static final Parcelable.Creator<Authorize> CREATOR = new Parcelable.Creator<Authorize>() {
        public Authorize createFromParcel(Parcel in) {
            return new Authorize(in);
        }

        public Authorize[] newArray(int size) {
            return new Authorize[size];
        }
    };
}
