package com.example.tfuwape.flickrfindr.adapter;

/*
  Created by
  Oluwatoni Fuwape
 */

import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.activities.MainActivity;
import com.example.tfuwape.flickrfindr.builders.SearchParamsBuilder;
import com.example.tfuwape.flickrfindr.core.APIService;
import com.example.tfuwape.flickrfindr.models.PhotoItem;
import com.example.tfuwape.flickrfindr.models.containers.PhotoSearchContainer;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoSearchAdapter extends SimpleCursorAdapter {
    private static final String[] mFields = {"_id", "result"};
    private static final String[] mVisible = {"result"};
    private static final int[] mViewIds = {android.R.id.text1};
    private ArrayList<PhotoItem> mPhotoItems = new ArrayList<>();
    private static final String UN_IMPLEMENTED_TEXT = "unimplemented";

    private Context activityContext;
    private APIService apiService;

    public PhotoSearchAdapter(Context context, APIService mAPIService) {
        super(context, R.layout.sugestions_list, null, mVisible, mViewIds, 0);
        activityContext = context;
        this.apiService = mAPIService;
    }

    private void retrieveSuggestions(final String text) {
        if (activityContext instanceof MainActivity && apiService != null) {

            final String apiKey = activityContext.getResources().getString(R.string.api_key);
            final Map<String, String> searchParams = new SearchParamsBuilder().
                    addSearchTerm(text).addAPIKey(apiKey).toParams();

            apiService.searchTerm(searchParams).enqueue(new Callback<PhotoSearchContainer>() {
                @Override
                public void onResponse(@NonNull final Call<PhotoSearchContainer> call,
                                       @NonNull final Response<PhotoSearchContainer> response) {
                    handleSuggestionResponse(response, text);
                }

                @Override
                public void onFailure(@NonNull final Call<PhotoSearchContainer> call,
                                      @NonNull final Throwable t) {
                    clearResult();
                }
            });
        }
    }

    private void handleSuggestionResponse(final Response<PhotoSearchContainer> response, final String text) {
        final PhotoSearchContainer container = response.body();
        if (response.isSuccessful() && container != null && container.getPhotos() != null) {
            mPhotoItems = container.getPhotos().getPhotoItems();
            if (mPhotoItems == null || mPhotoItems.isEmpty()) {
                clearResult();
            } else {
                notifyDataSetChanged();
            }
        } else {
            clearResult();
        }
    }

    public String getTextAtPosition(final int position) {
        return mPhotoItems.get(position).getTitle();
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        return new SuggestionsCursor(constraint);
    }

    private void clearResult() {
        mPhotoItems = new ArrayList<>();
        notifyDataSetChanged();
    }


    private class SuggestionsCursor extends AbstractCursor {

        SuggestionsCursor(CharSequence constraint) {
            final String input = constraint == null ? "" : constraint.toString();
            if (!input.isEmpty()) {
                retrieveSuggestions(input);
            } else {
                clearResult();
            }

            if (mPhotoItems == null) {
                clearResult();
            }
        }

        @Override
        public int getCount() {
            return mPhotoItems.size();
        }

        @Override
        public String[] getColumnNames() {
            return mFields;
        }

        @Override
        public long getLong(int column) {
            if (column == 0) {
                return getPosition();
            }
            throw new UnsupportedOperationException(UN_IMPLEMENTED_TEXT);
        }

        @Override
        public String getString(int column) {
            if (column == 1) {
                return mPhotoItems.get(getPosition()).getTitle();
            }
            throw new UnsupportedOperationException(UN_IMPLEMENTED_TEXT);
        }

        @Override
        public short getShort(int column) {
            throw new UnsupportedOperationException(UN_IMPLEMENTED_TEXT);
        }

        @Override
        public int getInt(int column) {
            throw new UnsupportedOperationException(UN_IMPLEMENTED_TEXT);
        }

        @Override
        public float getFloat(int column) {
            throw new UnsupportedOperationException(UN_IMPLEMENTED_TEXT);
        }

        @Override
        public double getDouble(int column) {
            throw new UnsupportedOperationException(UN_IMPLEMENTED_TEXT);
        }

        @Override
        public boolean isNull(int column) {
            return false;
        }
    }
}
