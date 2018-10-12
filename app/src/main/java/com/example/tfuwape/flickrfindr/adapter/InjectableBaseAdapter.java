package com.example.tfuwape.flickrfindr.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.util.MyUtil;
import com.example.tfuwape.flickrfindr.util.TypefaceManager;

import javax.inject.Inject;

/**
 * Abstract base for injecting common members for BaseAdapter subclasses
 */
public abstract class InjectableBaseAdapter extends BaseAdapter {

    protected TypefaceManager mTypefaceManager;

    @Inject
    public void setConstructorParams(TypefaceManager mTypefaceManager) {
        this.mTypefaceManager = mTypefaceManager;
    }

    public InjectableBaseAdapter(Context context) {
        MyApplication app = MyUtil.getApplication(context);
        if (app != null) {
            app.graph().inject(this);
        }
    }

    protected TypefaceManager getTypefaceManager() {
        return mTypefaceManager;
    }
}