package com.example.tfuwape.flickrfindr.util;

import android.os.AsyncTask;

import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.listeners.FetchSearchTermsListener;
import com.example.tfuwape.flickrfindr.roomdb.SearchTerm;
import com.example.tfuwape.flickrfindr.roomdb.SearchTermDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Helps to fetch previous search terms
 */
public class FetchSearchTermsTask extends AsyncTask<Void, Integer, List<SearchTerm>> {

    private FetchSearchTermsListener listener;

    public FetchSearchTermsTask(FetchSearchTermsListener mListener) {
        this.listener = mListener;
    }

    @Override
    protected List<SearchTerm> doInBackground(Void... arg0) {
        SearchTermDao searchTermDao = MyApplication.getSearchTermDao();
        List<SearchTerm> searchTerms = new ArrayList<>();
        if (searchTermDao != null) {
            searchTerms = searchTermDao.getAll();
        }
        return searchTerms;
    }

    @Override
    protected void onPostExecute(List<SearchTerm> result) {
        final ArrayList<String> terms = new ArrayList<>();
        for (SearchTerm searchTerm : result) {
            terms.add(searchTerm.getText());
        }
        listener.foundTerms(terms);
    }
}