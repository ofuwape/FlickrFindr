package com.example.tfuwape.flickrfindr.models.containers;

import com.example.tfuwape.flickrfindr.models.Paginator;
import com.example.tfuwape.flickrfindr.models.PhotoItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PhotoSearchData {

    //current page
    @SerializedName("page")
    private int page;

    //total number of pages
    @SerializedName("pages")
    private int pages;

    @SerializedName("perpage")
    private int perPage;

    @SerializedName("photo")
    private ArrayList<PhotoItem> photoItems;

    private Paginator paginator;


    PhotoSearchData() {
    }

    PhotoSearchData(ArrayList<PhotoItem> mPhotoItems) {
        this.photoItems = mPhotoItems;
    }

    public Paginator getPaginator() {
        if (paginator == null) {
            paginator = new Paginator(page, pages, perPage);
        }
        return paginator;
    }


    public ArrayList<PhotoItem> getPhotoItems() {
        return photoItems;
    }

    public void setPhotoItems(ArrayList<PhotoItem> photoItems) {
        this.photoItems = photoItems;
    }
}
