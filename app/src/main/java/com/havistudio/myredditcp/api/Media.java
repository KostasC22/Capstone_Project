
package com.havistudio.myredditcp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media {

    @SerializedName("oembed")
    @Expose
    private MediaOembed oembed;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The oembed
     */
    public MediaOembed getOembed() {
        return oembed;
    }

    /**
     * 
     * @param oembed
     *     The oembed
     */
    public void setOembed(MediaOembed oembed) {
        this.oembed = oembed;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
