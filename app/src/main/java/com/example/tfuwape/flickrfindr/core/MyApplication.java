package com.example.tfuwape.flickrfindr.core;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.example.tfuwape.flickrfindr.roomdb.AppDatabase;
import com.example.tfuwape.flickrfindr.roomdb.SearchTermDao;
import com.facebook.drawee.backends.pipeline.Fresco;

import javax.inject.Singleton;

import dagger.Provides;

public class MyApplication extends Application {

    @VisibleForTesting
    private static final String DATABASE_NAME = "flickr-findr-db";

    private static Component mComponent;
    private static String mockBaseUrl = "";
    private static SearchTermDao searchTermDao;
    private static AppDatabase appDatabase;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    @Override
    public void onCreate() {
        super.onCreate();
        initializeLibs();
    }

    public static SearchTermDao getSearchTermDao() {
        return searchTermDao;
    }

    private void initializeLibs() {
        mComponent = Component.Initializer.init(false, this);
        mComponent.inject(this);
        Fresco.initialize(this);
        seUpDB();
        searchTermDao = appDatabase.searchTermDao();
    }

    @Singleton
    void seUpDB() {
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        if (getDatabasePath(DATABASE_NAME).exists()) {
            mIsDatabaseCreated.postValue(true);
        }
    }

    public static Component graph() {
        return mComponent;
    }

    public void setMockMode(boolean useMock) {
        mComponent = Component.Initializer.init(useMock, this);
    }

    //Testing Helpers
    public static String getMockBaseUrl() {
        return mockBaseUrl;
    }

    public void setMockBaseUrl(String mockBaseUrl) {
        MyApplication.mockBaseUrl = mockBaseUrl;
    }
}
