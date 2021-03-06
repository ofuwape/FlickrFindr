package com.example.tfuwape.flickrfindr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.tfuwape.flickrfindr.core.Component;
import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.holder.InjectableBaseRecyclerViewHolder;
import com.example.tfuwape.flickrfindr.util.MyUtil;

/**
 * Abstract base for injecting common members for BaseAdapter subclasses
 */
public abstract class InjectableBaseAdapter extends RecyclerView.Adapter<InjectableBaseRecyclerViewHolder> {


    public InjectableBaseAdapter() {
        Component graph = MyApplication.graph();
        if (graph != null) {
            graph.inject(this);
        }
    }
}