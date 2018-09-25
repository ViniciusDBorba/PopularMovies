package com.udacity.nanodegree.popularmovies.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerDTO {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("iso_639_1")
    private String iso_639_1;

    @Expose
    @SerializedName("iso_3166_1")
    private String iso_3166_1;

    @Expose
    @SerializedName("key")
    private String key;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("site")
    private String site;

    @Expose
    @SerializedName("size")
    private String size;

    @Expose
    @SerializedName("type")
    private String type;

    public TrailerDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
