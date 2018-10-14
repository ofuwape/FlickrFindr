package com.example.tfuwape.flickrfindr.adapter;

/**
 * Created by
 * Oluwatoni Fuwape on 11/12/15.
 */

import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.activities.MainActivity;
import com.example.tfuwape.flickrfindr.core.APIService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionSimpleCursorAdapter extends SimpleCursorAdapter {
    private static final String[] mFields = {"_id", "result"};
    private static final String[] mVisible = {"result"};
    private static final int[] mViewIds = {android.R.id.text1};
    private ArrayList<String> mResults=new ArrayList<>();
    private static final String UN_IMPLEMENTED_TEXT = "unimplemented";

    private Context activityContext;

    public SuggestionSimpleCursorAdapter(Context context) {
        super(context, R.layout.sugestions_list, null, mVisible, mViewIds, 0);
        activityContext = context;
    }

    private void retrieveSuggestions(final String text) {
        if (activityContext instanceof MainActivity) {
//            APIService mAPI = ((MainActivity) activityContext).getAPIForSuggest();
//            mAPI.getSuggestions(suggestParam).enqueue(new Callback<Object>() {
//                @Override
//                public void onResponse(@NonNull final Call<Object> call,
//                                       @NonNull final Response<Object> response) {
//                    handleSuggestionResponse(response, text);
//                }
//
//                @Override
//                public void onFailure(@NonNull final Call<Object> call,
//                                      @NonNull final Throwable t) {
//                    clearResult();
//                }
//            });
        }
    }

    private void handleSuggestionResponse(final Response<Object> response, final String text) {
        final Object suggestionContainer = response.body();
        if (response.isSuccessful() && suggestionContainer != null) {
//            mResults = suggestionContainer.getMediaTitles();
            if (mResults.isEmpty()) {
                clearResult();
            } else {
                notifyDataSetChanged();
            }
        } else {
            clearResult();
        }
    }

    public String getTextAtPosition(final int position) {
        return mResults.get(position);
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        return new SuggestionsCursor(constraint);
    }

    private void clearResult() {
        mResults = new ArrayList<>();
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

            if (mResults == null) {
                clearResult();
            }
        }

        @Override
        public int getCount() {
            return mResults.size();
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
                return mResults.get(getPosition());
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
