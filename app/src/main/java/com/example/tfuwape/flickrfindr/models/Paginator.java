package com.example.tfuwape.flickrfindr.models;

public class Paginator {

    //current page
    private int page;

    //total number of pages
    private int pages;

    private int perPage;

    public Paginator() {
    }

    public Paginator(int mPage, int mPages, int mPerPage) {
        this.page = mPage;
        this.pages = mPages;
        this.perPage = mPerPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}
