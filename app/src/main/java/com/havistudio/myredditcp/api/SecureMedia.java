
package com.havistudio.myredditcp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecureMedia {

    @SerializedName("secureMediaOembed")
    @Expose
    private SecureMediaOembed secureMediaOembed;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The secureMediaOembed
     */
    public SecureMediaOembed getSecureMediaOembed() {
        return secureMediaOembed;
    }

    /**
     * 
     * @param secureMediaOembed
     *     The secureMediaOembed
     */
    public void setSecureMediaOembed(SecureMediaOembed secureMediaOembed) {
        this.secureMediaOembed = secureMediaOembed;
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
