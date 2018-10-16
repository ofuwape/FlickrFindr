package com.example.tfuwape.flickrfindr.core;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class APICallback<T> implements Callback<T> {
    private APICallback.OnUnauthorizedErrorListener mListener;

    public APICallback(OnUnauthorizedErrorListener mListener) {
        this.mListener = mListener;
    }

    public OnUnauthorizedErrorListener getListener() {
        return mListener;
    }

    public void setListener(OnUnauthorizedErrorListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onResponse(final Call<T> call, final Response<T> response) {
        if (!response.isSuccessful() && response.code() == 401) {
            if (mListener != null) {
                mListener.onThrowUnauthorizedEvent();
            }
        }
    }

    @Override
    public void onFailure(final Call<T> call, final Throwable t) {
    }

    /**
     * Click listener for handling the HTTP Error 401 Unauthorized. It should  display the login
     * activity and force the user to re-login before using the app
     */
    public interface OnUnauthorizedErrorListener {
        void onThrowUnauthorizedEvent();
    }
}