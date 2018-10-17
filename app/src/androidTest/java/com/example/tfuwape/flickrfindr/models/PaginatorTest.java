package com.example.tfuwape.flickrfindr.models;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PaginatorTest {

    @Test
    public void testConstructor() {
        int totalPages = 20;
        int currentPage = 5;
        int limit = 25;
        Paginator paginator = new Paginator(totalPages, currentPage, limit);
        assertEquals(paginator.getTotalPages(), totalPages);
        assertEquals(paginator.getCurrentPage(), currentPage);
        assertEquals(paginator.getLimit(), limit);
    }

    @Test
    public void testEmptyConstructor() {
        Paginator paginator = new Paginator();
        assertEquals(paginator.getTotalPages(), 0);
        assertEquals(paginator.getCurrentPage(), 0);
        assertEquals(paginator.getLimit(), 0);
    }

}