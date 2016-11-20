package com.havistudio.myredditcp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kostas on 09/09/2016.
 */
public class GeneralData {

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("children")
    @Expose
    private List<Child> children = new ArrayList<Child>();
    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("before")
    @Expose
    private Object before;

    /**
     * @return The modhash
     */
    public String getModhash() {
        return modhash;
    }

    /**
     * @param modhash The modhash
     */
    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    /**
     * @return The children
     */
    public List<Child> getChildren() {
        return children;
    }

    /**
     * @param children The children
     */
    public void setChildren(List<Child> children) {
        this.children = children;
    }

    /**
     * @return The after
     */
    public String getAfter() {
        return after;
    }

    /**
     * @param after The after
     */
    public void setAfter(String after) {
        this.after = after;
    }

    /**
     * @return The before
     */
    public Object getBefore() {
        return before;
    }

    /**
     * @param before The before
     */
    public void setBefore(Object before) {
        this.before = before;
    }

    @Override
    public String toString() {
        return "GeneralData{" +
                "modhash='" + modhash + '\'' +
                ", children=" + children +
                ", after='" + after + '\'' +
                ", before=" + before +
                '}';
    }
}
