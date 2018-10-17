package com.example.tfuwape.flickrfindr.models;

public class Paginator {

    //current currentPage
    private int currentPage;

    //total number of totalPages
    private int totalPages;

    private int limit;

    public Paginator() {
    }

    public Paginator(int mTotalPages, int mCurrentPage, int mLimit) {
        this.currentPage = mCurrentPage;
        this.totalPages = mTotalPages;
        this.limit = mLimit;
    }

    public int getLimit() {
        return limit;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
