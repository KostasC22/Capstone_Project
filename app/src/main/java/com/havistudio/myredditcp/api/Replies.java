
package com.havistudio.myredditcp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Replies {

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
    public GeneralData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(GeneralData data) {
        this.data = data;
    }

	@Override
	public String toString() {
		return "Replies [kind=" + kind + ", data=" + data + "]";
	}
    
    
}
