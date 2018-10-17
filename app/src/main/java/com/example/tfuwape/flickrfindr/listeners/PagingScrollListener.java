package com.example.tfuwape.flickrfindr.listeners;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.tfuwape.flickrfindr.models.Paginator;

/**
 * A Scroll Listener that's used with a RecyclerView for paging/infinite lists. Requires
 * the connected RecyclerView to be using the LinearLayoutManager.
 */
public class PagingScrollListener extends RecyclerView.OnScrollListener {

    private OnPageStateListener mListener;

    /**
     * Enum that describes the 3 different states the scroll listener can be in:
     * <p>
     * LOADING - Currently waiting on the next page of data to come from the server
     * NO_PAGES - No more pages to retrieve from the server or none to begin with.
     * WAITING_FOR_THRESHOLD - Page is loaded and is waiting to get to the bottom to load next page.
     */
    public enum PagingState {
        LOADING,
        NO_PAGES,
        WAITING_FOR_THRESHOLD
    }

    private Paginator mPaginator;

    private int mThreshold;

    private PagingState mPagingState;

    protected PagingState getPagingState() {
        return mPagingState;
    }

    public PagingScrollListener() {
        mPagingState = PagingState.NO_PAGES;
    }

    /**
     * Assigns the paginator to the scroll listener. Will update the internal paging state to
     * reflect the information provided by the paginator object.
     *
     * @param paginator Paginator object from API response
     */
    public void setPaginator(Paginator paginator) {
        mPaginator = paginator;

        if (paginator == null) {
            mPagingState = PagingState.NO_PAGES;
            return;
        }

        // ASSUMPTION: Limit is the same for all pages
        mThreshold = (int) Math.round(paginator.getLimit() * paginator.getCurrentPage() * 0.8);
        //load next page once you view 80% of current items

        // If we're not on the last page and there are pages to load,
        // set state to waiting for threshold
        if (mPaginator.getTotalPages() >= 1 &&
                mPaginator.getCurrentPage() < mPaginator.getTotalPages()) {
            mPagingState = PagingState.WAITING_FOR_THRESHOLD;
        } else {
            mPagingState = PagingState.NO_PAGES;
        }
    }

    public void setOnChangeStateListener(OnPageStateListener listener) {
        mListener = listener;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        try {
            super.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = recyclerView.getChildCount();

            int firstVisibleItemIndex = 0;
            if (layoutManager != null) {
                firstVisibleItemIndex = layoutManager.findFirstVisibleItemPosition();
            }

            if (firstVisibleItemIndex + visibleItemCount >= mThreshold &&
                    mPagingState == PagingState.WAITING_FOR_THRESHOLD) {
                // Threshold reached
                if (mListener != null) {
                    mListener.onLoadNextPage(mPaginator.getCurrentPage() + 1);
                    mPagingState = PagingState.LOADING;
                }
            }
        } catch (Exception e) {
            Log.e("onScrolled", e.getMessage());
        }
    }

    /**
     * An interface to handle when the paging scroll changes/triggers
     * different states.
     */
    public interface OnPageStateListener {

        /**
         * Called when the set threshold has been reached
         */
        void onLoadNextPage(int nextPageNumber);
    }
}
