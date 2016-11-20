package com.havistudio.myredditcp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comments {

	@SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private GeneralData data;

    /**
     * 
     * @return
     *     The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * 
     * @param kind
     *     The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * 
     * @return
     *     The data
     */
    public GeneralData getGeneralData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setGeneralData(GeneralData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }
}
