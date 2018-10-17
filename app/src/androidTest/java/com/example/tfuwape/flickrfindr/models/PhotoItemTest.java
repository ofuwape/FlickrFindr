package com.example.tfuwape.flickrfindr.models;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PhotoItemTest {

    private String id = "1298";
    private String title = "Funny Picture";
    private String smUrl = "https://small_url";
    private String lgUrl = "https://large_url";
    private PhotoItem photoItem = new PhotoItem();

    @Before
    public void setUp() {
        photoItem.setId(id);
        photoItem.setTitle(title);
        photoItem.setSquareUrl(smUrl);
        photoItem.setLargeUrl(lgUrl);
    }

    @Test
    public void testFields() {
        assert (photoItem.getTitle()).equals(title);
        assert (photoItem.getId()).equals(id);
        assert (photoItem.getSquareUrl()).equals(smUrl);
        assert (photoItem.getLargeUrl()).equals(lgUrl);
    }
}