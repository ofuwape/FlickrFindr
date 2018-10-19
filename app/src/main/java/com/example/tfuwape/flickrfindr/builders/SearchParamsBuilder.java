package com.example.tfuwape.flickrfindr.builders;

import java.util.HashMap;
import java.util.Map;

/**
 * A convenience builder class for creating a params map for searching the Flickr API.
 */
public class SearchParamsBuilder {

    private Map<String, String> mParams = new HashMap<>();
    final static String PAGE_LIMIT = "25";
    final static String FORMAT = "json";
    final static String JSON_CALLBACK = "1";
    final static String IMAGE_TYPES = "url_sq,url_l";

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
        mParams.put("per_page", SearchParamsBuilder.PAGE_LIMIT);
    }

    private void enableImageTypes() {
        mParams.put("extras", IMAGE_TYPES);
    }

    private void enableJSONFormat() {
        mParams.put("format", FORMAT);
        mParams.put("nojsoncallback", JSON_CALLBACK);
    }

    public Map<String, String> toParams() {
        addPageLimit();
        enableImageTypes();
        enableJSONFormat();
        return mParams;
    }

}
