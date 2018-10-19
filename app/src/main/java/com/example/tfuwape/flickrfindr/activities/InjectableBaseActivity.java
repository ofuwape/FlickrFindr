package com.example.tfuwape.flickrfindr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tfuwape.flickrfindr.core.APIService;
import com.example.tfuwape.flickrfindr.core.Component;
import com.example.tfuwape.flickrfindr.core.MyApplication;
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
        Component graph = MyApplication.graph();
        if (graph != null) {
            graph.inject(this);
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

}
