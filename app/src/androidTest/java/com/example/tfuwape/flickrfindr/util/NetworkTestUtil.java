package com.example.tfuwape.flickrfindr.util;

import android.content.Context;
import android.util.Log;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.core.MyApplication;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Created by
 * Oluwatoni Fuwape on 11/21/16.
 */

public class NetworkTestUtil {

    public enum MockWebServerType {
        GOOD(200),
        NOT_FOUND(404),
        UNAUTHORIZED(401);

        MockWebServerType(int i) {
        }
    }

    static MockWebServer startMockServer(final Context mContext,
                                         final MockWebServerType mockWebServerType) {
        MockWebServer server = new MockWebServer();
        server.url(mContext.getResources().getString(R.string.api_path));
        MyApplication app = MyUtil.getApplication(mContext);
        if (app != null) {
            app.setMockBaseUrl(server.url("/").toString());
            app.setAPIMockMode(true);
        }
        switch (mockWebServerType) {
            case UNAUTHORIZED:
            case NOT_FOUND:
                NetworkTestUtil.setBadServerDispatch(server, mockWebServerType);
                break;
            case GOOD:
            default:
                NetworkTestUtil.setSuccessServerDispatch(server, mContext);
                break;
        }
        try {
            server.start();
        } catch (Exception e) {
            Log.e("Server Error", "Message: " + e);
        }
        return server;
    }

    private static void setSuccessServerDispatch(MockWebServer server, final Context mContext) {
        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if (request.getPath().contains("flickr.photos.search")) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(AssetJSONFile.readByFilename("json/search_results.json", mContext));

                }
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher(dispatcher);
    }

    private static void setBadServerDispatch(MockWebServer server, final MockWebServerType mockWebServerType) {
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                return new MockResponse().setResponseCode(mockWebServerType == MockWebServerType.UNAUTHORIZED
                        ? 404 : 401);
            }
        };
        server.setDispatcher(dispatcher);
    }


}
