package com.example.tfuwape.flickrfindr.models.containers;

import com.google.gson.annotations.SerializedName;

public class PhotoSearchContainer {

    @SerializedName("photos")
    private PhotoSearchData photos;

    public PhotoSearchData getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoSearchData photos) {
        this.photos = photos;
    }
}
