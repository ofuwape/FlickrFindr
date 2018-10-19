package com.example.tfuwape.flickrfindr.builders;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class SearchParamsBuilderTest {

    private SearchParamsBuilder builder;

    @Before
    public void setUp() {
        builder = new SearchParamsBuilder();
    }

    @Test
    public void testDefaultConstructor() {
        final Map<String, String> params = builder.toParams();
        Assert.assertThat(params.get("per_page"), equalTo(SearchParamsBuilder.PAGE_LIMIT));
        Assert.assertThat(params.get("format"), equalTo(SearchParamsBuilder.FORMAT));
        Assert.assertThat(params.get("nojsoncallback"), equalTo(SearchParamsBuilder.JSON_CALLBACK));
        Assert.assertThat(params.get("extras"), equalTo(SearchParamsBuilder.IMAGE_TYPES));
    }

    @Test
    public void testAddSearchTerm() {
        final String searchTerm = "joy";
        builder.addSearchTerm(searchTerm);
        final Map<String, String> params = builder.toParams();
        Assert.assertThat(params.get("text"), equalTo(searchTerm));
    }

    @Test
    public void testAddPageNumber() {
        final int pageNumber = 3;
        builder.addPageNumber(pageNumber);
        final Map<String, String> params = builder.toParams();
        Assert.assertThat(params.get("page"), equalTo(Integer.toString(pageNumber)));
    }

    @Test
    public void testAddAPIKey() {
        final String apiKey = "$k#e^y@";
        builder.addAPIKey(apiKey);
        final Map<String, String> params = builder.toParams();
        Assert.assertThat(params.get("api_key"), equalTo(apiKey));
    }

}
