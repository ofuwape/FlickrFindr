package com.example.tfuwape.flickrfindr.core;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.VisibleForTesting;

import com.example.tfuwape.flickrfindr.roomdb.AppDatabase;
import com.example.tfuwape.flickrfindr.roomdb.SearchTermDao;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {

    private static final String DATABASE_NAME = "flickr-findr-db";

    @VisibleForTesting
    private static final String TEST_DATABASE_NAME = "test-flickr-findr-db";

    private static Component mComponent;
    private static String mockBaseUrl = "";
    private static SearchTermDao searchTermDao;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    private boolean mockMode;


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
        configureDB();
    }

    private void configureDB() {
        AppDatabase appDatabase;
        if (mockMode) {
            appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, TEST_DATABASE_NAME).build();
        } else {
            appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        }
        if (getDatabasePath(DATABASE_NAME).exists()) {
            mIsDatabaseCreated.postValue(true);
        }
        searchTermDao = appDatabase.searchTermDao();
    }

    public static Component graph() {
        return mComponent;
    }

    public void setAPIMockMode(boolean useMock) {
        mComponent = Component.Initializer.init(useMock, this);
        mockMode = useMock;
        configureDB();
    }

    //Testing Helpers
    public static String getMockBaseUrl() {
        return mockBaseUrl;
    }

    public void setMockBaseUrl(String mockBaseUrl) {
        MyApplication.mockBaseUrl = mockBaseUrl;
    }
}
