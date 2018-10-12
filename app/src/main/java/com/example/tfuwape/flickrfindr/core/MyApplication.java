package com.example.tfuwape.flickrfindr.core;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {

    private Component mComponent;

    private String mockBaseUrl = "";

    @Override
    public void onCreate() {
        super.onCreate();
        initializeLibs();
    }

    private void initializeLibs() {
        mComponent = Component.Initializer.init(false, this);
        mComponent.inject(this);
        Fresco.initialize(this);
    }


    public Component graph() {
        return mComponent;
    }

    public void setMockMode(boolean useMock) {
        mComponent = Component.Initializer.init(useMock, this);
    }


    //Testing Helpers
    public String getMockBaseUrl() {
        return mockBaseUrl;
    }

    public void setMockBaseUrl(String mockBaseUrl) {
        this.mockBaseUrl = mockBaseUrl;
    }

}
