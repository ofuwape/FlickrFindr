package com.example.tfuwape.flickrfindr.models.containers;

import com.google.gson.annotations.SerializedName;

public class PhotoSearchContainer {

    @SerializedName("photos")
    PhotoSearchData photos;

    public PhotoSearchData getPhotos() {
        return photos;
    }

}
