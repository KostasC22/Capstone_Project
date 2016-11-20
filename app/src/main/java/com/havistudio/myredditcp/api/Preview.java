
package com.havistudio.myredditcp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Preview {

    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();

    /**
     * 
     * @return
     *     The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Preview{" +
                "images=" + images +
                '}';
    }
}
