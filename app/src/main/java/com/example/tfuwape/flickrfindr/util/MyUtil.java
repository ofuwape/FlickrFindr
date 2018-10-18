package com.example.tfuwape.flickrfindr.util;

import android.content.Context;
import android.net.Uri;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.listeners.FetchSearchTermsListener;
import com.example.tfuwape.flickrfindr.listeners.FoundSearchTermListener;

public abstract class MyUtil {

    public static String getAPIPath(Context mContext) {
        return mContext.getString(R.string.api_path);
    }

    public static MyApplication getApplication(Context context) {
        if (context != null && context.getApplicationContext() instanceof MyApplication) {
            return (MyApplication) context.getApplicationContext();
        } else {
            return null;
        }
    }

    public static Uri getFrescoUri(final String url) {
        if (url != null && !url.isEmpty()) {
            return Uri.parse(url);
        } else {
            return null;
        }
    }

}
