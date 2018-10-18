package com.example.tfuwape.flickrfindr.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tfuwape.flickrfindr.core.Component;
import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.util.MyUtil;

import butterknife.ButterKnife;

/**
 * Base view holder with common injecting/asset code
 */
public abstract class InjectableBaseRecyclerViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final OnClickListener mListener;
    protected Context mContext;
    private View view;


    public InjectableBaseRecyclerViewHolder(Context context, View itemView) {
        this(context, itemView, null);
        view = itemView;
    }

    public InjectableBaseRecyclerViewHolder(Context context, View itemView, OnClickListener clickListener) {
        super(itemView);
        view = itemView;
        mContext = context;
        mListener = clickListener;

        itemView.setOnClickListener(this);

        ButterKnife.bind(this, itemView);
        Component graph = MyApplication.graph();
        if (graph != null) {
            graph.inject(this);
        }

    }

    /**
     * Click handler for when the view holder as a whole gets tapped
     *
     * @param v View that was clicked
     */
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public View getView() {
        return view;
    }

    /**
     * Click listener for handling clicks on the item
     */
    public interface OnClickListener {
        /**
         * Invoked when the user taps on the item
         *
         * @param position Position of view holder in recycler view
         */
        void onClick(int position);
    }
}
