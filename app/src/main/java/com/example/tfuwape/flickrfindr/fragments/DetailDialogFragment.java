package com.example.tfuwape.flickrfindr.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.models.PhotoItem;
import com.example.tfuwape.flickrfindr.util.MyUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by
 * Oluwatoni Fuwape on 2/18/16.
 */
public class DetailDialogFragment extends DialogFragment {

    @BindView(R.id.detail_image_thumbnail)
    SimpleDraweeView draweeView;

    private Unbinder unbinder;
    private PhotoItem photoItem;

    public static final String PHOTO_ITEM_KEY = "photo_item_key";

    @OnClick(R.id.closeButton)
    public void close() {
        Activity activity = getActivity();
        if (activity != null) {
            FragmentManager manager = ((FragmentActivity) activity).getSupportFragmentManager();
            if (manager != null) {
                manager.popBackStack();
            }
        }
    }

    public static DetailDialogFragment newInstance() {
        return new DetailDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Bundle bundle = getArguments();
        if (bundle != null) {
            photoItem = (PhotoItem) bundle.getSerializable(PHOTO_ITEM_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dialog_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        if (photoItem != null) {
            draweeView.setImageURI("");
            final Uri mUri = MyUtil.getFrescoUri(photoItem.getLargeUrl());
            if (mUri != null) {
                draweeView.setImageURI(mUri);
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
