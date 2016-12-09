package com.havistudio.myredditcp;

/**
 * Created by kostas on 03/12/2016.
 */

public class AdapterObject {

    private String id;
    private String content;
    private Long score;
    private Long ups;
    private Long downs;
    private Long padding;

    public AdapterObject(){

    }

    public AdapterObject(String id, String content, Long score, Long ups, Long downs, Long padding) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.ups = ups;
        this.downs = downs;
        this.padding = padding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getUps() {
        return ups;
    }

    public void setUps(Long ups) {
        this.ups = ups;
    }

    public Long getDowns() {
        return downs;
    }

    public void setDowns(Long downs) {
        this.downs = downs;
    }

    public Long getPadding() {
        return padding;
    }

    public void setPadding(Long padding) {
        this.padding = padding;
    }

    @Override
    public String toString() {
        return "AdapterObject{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", ups=" + ups +
                ", downs=" + downs +
                ", padding=" + padding +
                '}';
    }
}
