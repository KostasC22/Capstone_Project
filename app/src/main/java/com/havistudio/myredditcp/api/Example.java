package com.havistudio.myredditcp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kostas on 09/09/2016.
 */
public class Example {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private GeneralData generalData;

    /**
     * @return The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * @param kind The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return The generalData
     */
    public GeneralData getGeneralData() {
        return generalData;
    }

    /**
     * @param generalData The generalData
     */
    public void setGeneralData(GeneralData generalData) {
        this.generalData = generalData;
    }

}