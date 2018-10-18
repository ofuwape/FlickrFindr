package com.example.tfuwape.flickrfindr.adapters;

/*
  Created by
  Oluwatoni Fuwape
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.holder.InjectableBaseRecyclerViewHolder;
import com.example.tfuwape.flickrfindr.holder.PhotoItemViewHolder;
import com.example.tfuwape.flickrfindr.models.PhotoItem;

import java.util.ArrayList;

public class PhotoSearchAdapter extends InjectableBaseAdapter {

    private ArrayList<PhotoItem> mPhotoItems = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private PhotoItemViewHolder.PhotoItemClickListener mClickListener;

    public PhotoSearchAdapter(Context context, PhotoItemViewHolder.PhotoItemClickListener clickListener) {
        super();
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mClickListener = clickListener;
    }

    public PhotoSearchAdapter(Context context, PhotoItemViewHolder.PhotoItemClickListener clickListener, ArrayList<PhotoItem> photoItems) {
        this(context, clickListener);
        this.mPhotoItems = photoItems;
        notifyDataSetChanged();
    }

    public void resetPhotoItems() {
        this.mPhotoItems.clear();
        notifyDataSetChanged();
    }

    public void addPhotoItems(ArrayList<PhotoItem> photoItems) {
        this.mPhotoItems.addAll(photoItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InjectableBaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = mLayoutInflater.inflate(R.layout.photo_list_item, viewGroup, false);
        return new PhotoItemViewHolder(mContext, view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InjectableBaseRecyclerViewHolder holder, int position) {
        if (holder instanceof PhotoItemViewHolder) {
            ((PhotoItemViewHolder) holder).updateWithItem(mPhotoItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoItems.size();
    }


    public PhotoItem getPhotoItem(int position) {
        if (mPhotoItems.size() > position) {
            return mPhotoItems.get(position);
        } else {
            return null;
        }
    }


}
