package com.example.tfuwape.flickrfindr.holder;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tfuwape.flickrfindr.core.Component;
import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.util.TypefaceManager;
import com.example.tfuwape.flickrfindr.util.TypefaceType;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Base view holder with common injecting/asset code
 */
public abstract class InjectableBaseRecyclerViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final OnClickListener mListener;
    protected Context mContext;
    private View view;
    protected TypefaceManager mTypefaceManager;
    private AssetManager mAssetManager;


    @Inject
    public void setConstructorParams(TypefaceManager mTypefaceManager) {
        this.mTypefaceManager = mTypefaceManager;
    }

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
        mAssetManager = context.getAssets();

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

    /**
     * Override this method with view holder specific font bindings
     */
    protected abstract void applyTypefaces();

    /**
     * Set the typeface for the given TextView
     *
     * @param type Typeface type
     * @param tv   TextView object
     */
    protected void setTypefaceForTextView(TypefaceType type, TextView tv) {
        Typeface typeface;
        typeface = typefaceFromType(type);
        tv.setTypeface(typeface);
    }

    /**
     * Helper method to get typeface object from given type enum
     *
     * @param type TypfaceType enum
     * @return Android Typeface object
     */
    private Typeface typefaceFromType(TypefaceType type) {
        return mTypefaceManager.compiledTypefaceWithType(mAssetManager, type);
    }
}
