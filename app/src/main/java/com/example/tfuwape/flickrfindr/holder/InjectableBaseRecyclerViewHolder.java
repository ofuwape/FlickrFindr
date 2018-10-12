package com.example.tfuwape.flickrfindr.holder;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.util.DurationTimeFormatter;
import com.example.tfuwape.flickrfindr.util.MyUtil;
import com.example.tfuwape.flickrfindr.util.TypefaceManager;
import com.example.tfuwape.flickrfindr.util.TypefaceType;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Base view holder with common injecting/asset code
 */
public abstract class InjectableBaseRecyclerViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    protected DurationTimeFormatter mDurationTimeFormatter;
    protected TypefaceManager mTypefaceManager;

    private final AssetManager mAssetManager;
    private final OnClickListener mListener;
    protected Context mContext;
    private View view;

    @Inject
    public void setConstructorParams(DurationTimeFormatter mDurationTimeFormatter,
                                     TypefaceManager mTypefaceManager) {
        this.mDurationTimeFormatter = mDurationTimeFormatter;
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
        itemView.setOnLongClickListener(this);

        ButterKnife.bind(this, itemView);
        MyApplication app = MyUtil.getApplication(mContext);
        if (app != null) {
            app.graph().inject(this);
        }

        mAssetManager = context.getAssets();
        applyTypefaces();
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
     * Set the typeface for the given Button's title
     *
     * @param type       Typeface type
     * @param buttonView ButtonView object
     */
    protected void setTypefaceForButton(TypefaceType type, Button buttonView) {
        Typeface typeface;
        typeface = typefaceFromType(type);
        buttonView.setTypeface(typeface);
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
