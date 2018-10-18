package com.example.tfuwape.flickrfindr;


import com.example.tfuwape.flickrfindr.models.PhotoItem;

import java.util.ArrayList;

/**
 * A class with a collection of helper methods for generating mock content for tests
 */
public class MockFactory {

    public static ArrayList<PhotoItem> createListOfPhotoWithIdSet(int size) {
        ArrayList<PhotoItem> photoItems = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            photoItems.add(createMockPhotoItemWithId(index));
        }
        return photoItems;
    }

    public static ArrayList<String> createListOfTerms(int size) {
        ArrayList<String> items = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            items.add(createMockSearchItem(index));
        }
        return items;
    }

    private static String createMockSearchItem(int id) {
        return Integer.toBinaryString(id);
    }

    private static PhotoItem createMockPhotoItemWithId(int id) {
        PhotoItem photoItem = new PhotoItem();
        photoItem.setId(Integer.toBinaryString(id));
        return photoItem;
    }
}
