package com.example.tfuwape.flickrfindr.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tfuwape.flickrfindr.models.Paginator;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PagingScrollListenerTest {
    private PagingScrollListener mListener;

    private PagingScrollListener.OnPageStateListener mMockPageListener;

    @Before
    public void setUp() {
        mListener = new PagingScrollListener();
        mMockPageListener = mock(PagingScrollListener.OnPageStateListener.class);
        mListener.setOnChangeStateListener(mMockPageListener);
    }

    @Test
    public void testDefaultStates() {
        assert (mListener.getPagingState()).equals(PagingScrollListener.PagingState.NO_PAGES);
    }

    @Test
    public void testSetNullPaginator() {
        mListener.setPaginator(null);
        assert (mListener.getPagingState()).equals(PagingScrollListener.PagingState.NO_PAGES);
    }

    @Test
    public void testAvailablePagePaginator() {
        Paginator paginator = new Paginator(2, 1, 10);
        mListener.setPaginator(paginator);
        assert (mListener.getPagingState())
                .equals(PagingScrollListener.PagingState.WAITING_FOR_THRESHOLD);
    }

    @Test
    public void testLastPagePaginator() {
        Paginator paginator = new Paginator(2, 2, 10);
        mListener.setPaginator(paginator);
        assert (mListener.getPagingState())
                .equals(PagingScrollListener.PagingState.NO_PAGES);
    }

    @Test
    public void testOnScrolledFirstItem() {
        Paginator paginator = new Paginator(2, 1, 10);
        mListener.setPaginator(paginator);
        RecyclerView mockView = createMockRecyclerViewWithCount(0);
        mListener.onScrolled(mockView, 0, 0);
        assert (mListener.getPagingState())
                .equals(PagingScrollListener.PagingState.WAITING_FOR_THRESHOLD);
        verifyZeroInteractions(mMockPageListener);
    }

    @Test
    public void testOnScrolledJustBeforeThreshold() {
        Paginator paginator = new Paginator(2, 1, 10);
        mListener.setPaginator(paginator);
        RecyclerView mockView = createMockRecyclerViewWithCount(2);
        mListener.onScrolled(mockView, 0, 0);
        assert (mListener.getPagingState()).equals(PagingScrollListener.PagingState.WAITING_FOR_THRESHOLD);
        verifyZeroInteractions(mMockPageListener);
    }

    @Test
    public void testOnScrolledOnThreshold() {
        Paginator paginator = new Paginator(2, 1, 10);
        mListener.setPaginator(paginator);
        RecyclerView mockView = createMockRecyclerViewWithCount(5);
        mListener.onScrolled(mockView, 0, 0);
        assert (mListener.getPagingState())
                .equals(PagingScrollListener.PagingState.LOADING);
        verify(mMockPageListener).onLoadNextPage(2);
    }

    @Test
    public void testOnScrolledJustAfterThreshold() {
        Paginator paginator = new Paginator(2, 1, 10);
        mListener.setPaginator(paginator);
        RecyclerView mockView = createMockRecyclerViewWithCount(6);
        mListener.onScrolled(mockView, 0, 0);
        assert (mListener.getPagingState())
                .equals(PagingScrollListener.PagingState.LOADING);
        verify(mMockPageListener).onLoadNextPage(2);
    }

    private RecyclerView createMockRecyclerViewWithCount(int visiblePosition) {
        LinearLayoutManager mockManager = mock(LinearLayoutManager.class);
        when(mockManager.findFirstVisibleItemPosition()).thenReturn(visiblePosition);

        RecyclerView mockView = mock(RecyclerView.class);
        when(mockView.getChildCount()).thenReturn(5);

        when(mockView.getLayoutManager()).thenReturn(mockManager);
        return mockView;
    }
}