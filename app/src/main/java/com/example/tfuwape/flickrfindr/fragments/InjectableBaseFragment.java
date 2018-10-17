package com.example.tfuwape.flickrfindr.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.tfuwape.flickrfindr.core.APIService;
import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.util.MyUtil;
import com.example.tfuwape.flickrfindr.util.TypefaceManager;

import javax.inject.Inject;

/**
 * Base fragment that contains injection logic
 */
public class InjectableBaseFragment extends Fragment {

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
        MyApplication application = MyUtil.getApplication(getActivity());
        if (application != null) {
            application.graph().inject(this);
        }
    }

    protected TypefaceManager getTypefaceManager() {
        return mTypefaceManager;
    }

    protected APIService getAPI() {
        return mAPI;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle oldFragInstanceState) {
        //No call for super(). Bug on API Level > 11.
    }
}
