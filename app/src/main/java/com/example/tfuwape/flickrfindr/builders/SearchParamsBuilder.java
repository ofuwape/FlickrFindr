package com.example.tfuwape.flickrfindr.builders;

import java.util.HashMap;
import java.util.Map;

/**
 * A convenience builder class for creating a params map for searching the Flickr API.
 */
public class SearchParamsBuilder {

    private Map<String, String> mParams = new HashMap<>();
    private final static int PAGE_LIMIT = 25;

    public SearchParamsBuilder addSearchTerm(String term) {
        if (term != null) {
            mParams.put("text", term);
        }
        return this;
    }

    public SearchParamsBuilder addPageNumber(int pageNumber) {
        if (pageNumber > 0) {
            mParams.put("page", String.valueOf(pageNumber));
        }
        return this;
    }

    public SearchParamsBuilder addAPIKey(final String apiKey) {
        mParams.put("api_key", apiKey);
        return this;
    }

    private void addPageLimit() {
        if (SearchParamsBuilder.PAGE_LIMIT > 0) {
            mParams.put("per_page", String.valueOf(SearchParamsBuilder.PAGE_LIMIT));
        }
    }

    private void enableImageTypes() {
        mParams.put("extras", "url_sq,url_l");
    }

    private void enableJSONFormat() {
        mParams.put("format", "json");
        mParams.put("nojsoncallback", "1");
    }

    public Map<String, String> toParams() {
        addPageLimit();
        enableImageTypes();
        enableJSONFormat();
        return mParams;
    }

}
