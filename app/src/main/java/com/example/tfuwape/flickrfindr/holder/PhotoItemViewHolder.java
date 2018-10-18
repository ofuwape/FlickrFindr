package com.example.tfuwape.flickrfindr.holder;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.models.PhotoItem;
import com.example.tfuwape.flickrfindr.util.MyUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

public class PhotoItemViewHolder extends InjectableBaseRecyclerViewHolder {

    @BindView(R.id.image_thumbnail)
    SimpleDraweeView imageDraweeView;

    @BindView(R.id.titleTextView)
    TextView textView;


    public PhotoItemViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    public PhotoItemViewHolder(Context context, View itemView, PhotoItemClickListener clickListener) {
        super(context, itemView, clickListener);
    }

    public void updateWithItem(@NonNull PhotoItem photoItem) {
        //clear views
        imageDraweeView.setImageURI("");
        textView.setText("");
        //set views
        final Uri mUri = MyUtil.getFrescoUri(photoItem.getSquareUrl());
        if (mUri != null) {
            imageDraweeView.setImageURI(mUri);
        }
        textView.setText(photoItem.getTitle());
    }

    /**
     * Click listener for handling clicks on the item
     */
    public interface PhotoItemClickListener extends OnClickListener {
        /**
         * Invoked when the user taps on the item
         *
         * @param position Position of view holder in recycler view
         */
        void onClick(int position);
    }

    public TextView getTextView() {
        return textView;
    }
}
