package com.example.tfuwape.flickrfindr.holders;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.holder.PhotoItemViewHolder;
import com.example.tfuwape.flickrfindr.models.PhotoItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


@RunWith(AndroidJUnit4.class)
public class PhotoItemViewHolderTest {

    private PhotoItemViewHolder mViewHolder;
    private PhotoItemViewHolder.PhotoItemClickListener mMockListener =
            mock(PhotoItemViewHolder.PhotoItemClickListener.class);


    @Before
    public void setUp() {
        Context mContext = getInstrumentation().getTargetContext();

        LinearLayout parent = new LinearLayout(mContext);
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.photo_list_item, parent, false);
        mViewHolder = new PhotoItemViewHolder(mContext, view, mMockListener);
    }

    @Test
    public void testUpdateWithPhotoItem() {
        PhotoItem photoItem = new PhotoItem();

        String title = "This is a sample title";
        String id = "SAMPLE_ID";
        String url = "";

        photoItem.setTitle(title);
        photoItem.setId(id);
        photoItem.setSquareUrl(url);
        photoItem.setLargeUrl(url);

        mViewHolder.updateWithItem(photoItem);
        assertThat(mViewHolder.getTextView().getText()).isEqualTo(title);
    }

    @Test
    public void testUpdateWithEmptyFields() {
        mViewHolder.updateWithItem(new PhotoItem());
        assertThat(mViewHolder.getTextView().getText()).isEqualTo("");
    }
}