package com.example.tfuwape.flickrfindr.util;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import java.io.IOException;

import okhttp3.mockwebserver.MockWebServer;

public class BaseActivityTestRule extends ActivityTestRule {

    private MockWebServer mockWebServer;
    private NetworkTestUtil.MockWebServerType mockWebServerType = NetworkTestUtil.MockWebServerType.GOOD;


    protected BaseActivityTestRule(Class activityClass) {
        super(activityClass, true, true);
    }

    @Override
    protected Intent getActivityIntent() {
        return super.getActivityIntent();
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mockWebServer = NetworkTestUtil.startMockServer(targetContext, mockWebServerType);
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (mockWebServer != null) {
            try {
                mockWebServer.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}