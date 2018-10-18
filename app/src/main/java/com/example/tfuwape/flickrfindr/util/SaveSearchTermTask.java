package com.example.tfuwape.flickrfindr.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.roomdb.SearchTerm;
import com.example.tfuwape.flickrfindr.roomdb.SearchTermDao;

public class SaveSearchTermTask extends AsyncTask {

    private String query;

    public SaveSearchTermTask(@NonNull String mQuery) {
        this.query = mQuery;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        SearchTermDao searchTermDao = MyApplication.getSearchTermDao();
        if (searchTermDao != null) {
            final SearchTerm searchTerm = new SearchTerm();
            searchTerm.setText(query);
            searchTermDao.insert(searchTerm);
        }
        return objects;
    }
}
