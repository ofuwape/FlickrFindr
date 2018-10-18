package com.example.tfuwape.flickrfindr.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.listeners.FoundSearchTermListener;
import com.example.tfuwape.flickrfindr.roomdb.SearchTerm;
import com.example.tfuwape.flickrfindr.roomdb.SearchTermDao;

public class CheckSearchTermTask extends AsyncTask<Void, Integer, Boolean> {

    private String query;
    private FoundSearchTermListener listener;

    public CheckSearchTermTask(@NonNull String mQuery, FoundSearchTermListener mListener) {
        this.query = mQuery;
        this.listener = mListener;
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
        SearchTermDao searchTermDao = MyApplication.getSearchTermDao();
        SearchTerm foundTerm = null;
        if (searchTermDao != null) {
            foundTerm = MyApplication.getSearchTermDao().findText(query);
        }
        return foundTerm != null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        listener.didFindTerm(result, query);
    }

}