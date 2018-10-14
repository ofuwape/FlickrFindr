package com.example.tfuwape.flickrfindr.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tfuwape.flickrfindr.core.APIService;
import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.util.MyUtil;
import com.example.tfuwape.flickrfindr.util.TypefaceManager;

import javax.inject.Inject;


/**
 * Base activity that contains common components and injection logic
 */
public abstract class InjectableBaseActivity extends AppCompatActivity {

    protected TypefaceManager mTypefaceManager;
    protected APIService mAPI;

    @Inject
    public void setConstructorParams(TypefaceManager mTypefaceManager, APIService mAPI) {
        this.mTypefaceManager = mTypefaceManager;
        this.mAPI = mAPI;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication application = MyUtil.getApplication(this);
        if (application != null) {
            application.graph().inject(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public APIService getAPI() {
        return mAPI;
    }


    public TypefaceManager getTypefaceManager() {
        return mTypefaceManager;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle oldActInstanceState) {
        try {
            super.onSaveInstanceState(oldActInstanceState);
            oldActInstanceState.clear();
        } catch (Exception e) {
            final String tag = "oldActInstanceState";
            Log.e(tag, e.getMessage());
        }
    }
}
