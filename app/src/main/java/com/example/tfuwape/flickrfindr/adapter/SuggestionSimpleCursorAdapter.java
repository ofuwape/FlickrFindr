package com.curiosity.adapters;

/**
 * Created by
 * Oluwatoni Fuwape on 11/12/15.
 */

import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;

import com.curiosity.R;
import com.curiosity.activities.MainActivity;
import com.curiosity.core.APIService;
import com.curiosity.models.containers.suggestion_models.SuggestionContainer;
import com.curiosity.util.CuriosityUtil;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionSimpleCursorAdapter extends SimpleCursorAdapter {
    private static final String[] mFields = {"_id", "result"};
    private static final String[] mVisible = {"result"};
    private static final int[] mViewIds = {android.R.id.text1};
    private ArrayList<String> mResults;
    private static final String UN_IMPLEMENTED_TEXT = "unimplemented";

    private Context activityContext;

    public SuggestionSimpleCursorAdapter(Context context) {
        super(context, R.layout.sugestions_list, null, mVisible, mViewIds, 0);
        activityContext = context;

    }

    private void retrieveSuggestions(final String text, final int fuzzinessNum) {
        if (activityContext instanceof MainActivity) {
            APIService mAPI = ((MainActivity) activityContext).getAPIForSuggest();

            HashMap<String, Integer> fuzziness = new HashMap<>();
            fuzziness.put("fuzziness", fuzzinessNum);

            HashMap<String, Object> completion = new HashMap<>();
            completion.put("field", "suggest");
            completion.put("fuzzy", fuzziness);

            HashMap<String, Object> media = new HashMap<>();
            media.put("text", CuriosityUtil.formatSearchText(text));
            media.put("completion", completion);

            HashMap<String, Object> suggestParam = new HashMap<>();
            suggestParam.put("media", media);

            mAPI.getSuggestions(suggestParam).enqueue(new Callback<SuggestionContainer>() {
                @Override
                public void onResponse(final Call<SuggestionContainer> call, final Response<SuggestionContainer> response) {
                    handleSuggestionResponse(response, text, fuzzinessNum);
                }

                @Override
                public void onFailure(final Call<SuggestionContainer> call, final Throwable t) {
                    clearResult();
                }
            });
        }
    }

    private void handleSuggestionResponse(final Response<SuggestionContainer> response,
                                          final String text, final int fuzzinessNum) {
        final SuggestionContainer suggestionContainer = response.body();
        if (response.isSuccessful() && suggestionContainer != null) {
            suggestionContainer.setContext(activityContext);
            mResults = suggestionContainer.getMediaTitles();
            if (mResults.isEmpty()) {
                clearResult();
                if (fuzzinessNum == 0) {
                    retrieveSuggestions(text, fuzzinessNum + 2);
                }
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
            if (!input.isEmpty() && input.length() >= 1) {
                retrieveSuggestions(input, 0);
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
