package com.example.tfuwape.flickrfindr.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.util.MyUtil;

import butterknife.ButterKnife;

/**
 * Base view holder with common injecting/asset code
 */
public abstract class InjectableBaseRecyclerViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

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
        itemView.setOnLongClickListener(this);

        ButterKnife.bind(this, itemView);
        MyApplication app = MyUtil.getApplication(mContext);
        if (app != null) {
            app.graph().inject(this);
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

    @Override
    public boolean onLongClick(View v) {
        if (mListener != null) {
            mListener.onLongClick(getLayoutPosition());
        }

        return true;
    }

    public View getView() {
        return view;
    }

    /**
     * Click listener for handling clicks on the video card
     */
    public interface OnClickListener {
        /**
         * Invoked when the user taps on the video card
         *
         * @param position Position of view holder in recycler view
         */
        void onClick(int position);

        /**
         * Invoked on long clicks
         *
         * @param position Position of view holder in recycler view
         */
        void onLongClick(int position);
    }
}
