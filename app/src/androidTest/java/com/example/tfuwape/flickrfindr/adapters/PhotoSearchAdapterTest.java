package com.example.tfuwape.flickrfindr.adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.widget.FrameLayout;

import com.example.tfuwape.flickrfindr.MockFactory;
import com.example.tfuwape.flickrfindr.holder.PhotoItemViewHolder;
import com.example.tfuwape.flickrfindr.models.PhotoItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class PhotoSearchAdapterTest {

    private PhotoSearchAdapter mAdapter;

    private PhotoItemViewHolder.PhotoItemClickListener mMockListener =
            mock(PhotoItemViewHolder.PhotoItemClickListener.class);

    private ArrayList<PhotoItem> photoItems;
    private Context mContext;

    @Before
    public void setUp() {
        photoItems = MockFactory.createListOfPhotoWithIdSet(5);
        mContext = getInstrumentation().getTargetContext();
        mAdapter = new PhotoSearchAdapter(mContext, mMockListener, photoItems);
    }

    @Test
    public void testSetItemsChangesState() {
        ArrayList<PhotoItem> mPhotoItems = MockFactory.createListOfPhotoWithIdSet(3);
        mAdapter.resetPhotoItems();
        mAdapter.addPhotoItems(mPhotoItems);
        assertThat(mAdapter.getItemCount()).isEqualTo(mPhotoItems.size());
    }

    @Test
    public void testEmptyState() {
        mAdapter.resetPhotoItems();
        assertThat(mAdapter.getItemCount()).isEqualTo(0);
    }

    @Test
    public void testOnCreateViewHolderReturnsItem() {
        PhotoItemViewHolder holder =
                (PhotoItemViewHolder) mAdapter.onCreateViewHolder(new FrameLayout(mContext), 0);
        assertThat(holder).isNotNull();
        assertThat(holder).isInstanceOf(PhotoItemViewHolder.class);
    }

    @Test
    public void testOnBindViewHolderUpdatesHolder() {
        PhotoItemViewHolder holder = mock(PhotoItemViewHolder.class);
        mAdapter.onBindViewHolder(holder, 0);
        verify(holder).updateWithItem(photoItems.get(0));
    }

    @Test
    public void testOnGetItem() {
        assertThat(mAdapter.getPhotoItem(0)).isEqualTo(photoItems.get(0));
    }


}