package com.example.tfuwape.flickrfindr.core;

import com.example.tfuwape.flickrfindr.activities.InjectableBaseActivity;
import com.example.tfuwape.flickrfindr.adapter.InjectableBaseAdapter;
import com.example.tfuwape.flickrfindr.fragment.InjectableBaseFragment;
import com.example.tfuwape.flickrfindr.holder.InjectableBaseRecyclerViewHolder;

/**
 * Common interface implemented by both the relase and debug flavored components
 */
public interface AppGraph {

    /*
    Common inject signatures for both release and debug variants
     */
    void inject(MyApplication app);

    void inject(InjectableBaseAdapter adapter);

    void inject(InjectableBaseFragment fragment);

    void inject(InjectableBaseActivity activity);

    void inject(InjectableBaseRecyclerViewHolder viewHolder);

}
