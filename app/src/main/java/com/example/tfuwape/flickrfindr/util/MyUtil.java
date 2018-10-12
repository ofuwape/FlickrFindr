package com.example.tfuwape.flickrfindr.util;

import android.content.Context;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.core.MyApplication;

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

}
