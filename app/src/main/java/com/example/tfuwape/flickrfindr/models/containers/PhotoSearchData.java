package com.example.tfuwape.flickrfindr.models.containers;

import com.example.tfuwape.flickrfindr.models.Paginator;
import com.example.tfuwape.flickrfindr.models.PhotoItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PhotoSearchData {

    //current page
    @SerializedName("page")
    int page;

    //total number of pages
    @SerializedName("pages")
    int pages;

    @SerializedName("perpage")
    int perPage;

    @SerializedName("photo")
    ArrayList<PhotoItem> photoItems;

    private Paginator paginator;


    PhotoSearchData() {
    }

    public Paginator getPaginator() {
        if (paginator == null) {
            paginator = new Paginator(pages, page, perPage);
        }
        return paginator;
    }


    public ArrayList<PhotoItem> getPhotoItems() {
        return photoItems;
    }
}
