package com.example.tfuwape.flickrfindr.models;

import com.google.gson.annotations.SerializedName;

public class PhotoItem {

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private String id;

    @SerializedName("url_sq")
    private String squareUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSquareUrl() {
        return squareUrl;
    }

    public void setSquareUrl(String squareUrl) {
        this.squareUrl = squareUrl;
    }
}
