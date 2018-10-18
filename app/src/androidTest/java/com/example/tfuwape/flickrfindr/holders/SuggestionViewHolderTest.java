package com.example.tfuwape.flickrfindr.holders;


import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.holder.SuggestionItemViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class SuggestionViewHolderTest {

    private SuggestionItemViewHolder mViewHolder;
    private SuggestionItemViewHolder.SuggestionClickListener mMockListener =
            mock(SuggestionItemViewHolder.SuggestionClickListener.class);


    @Before
    public void setUp() {
        Context mContext = getInstrumentation().getTargetContext();

        LinearLayout parent = new LinearLayout(mContext);
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.suggestion_view, parent, false);
        mViewHolder = new SuggestionItemViewHolder(mContext, view, mMockListener);
    }

    @Test
    public void testUpdateWithItem() {
        final String term = "sample";
        mViewHolder.setText(term);
        assertThat(mViewHolder.getTextView().getText()).isEqualTo(term);
    }

    @Test
    public void testUpdateWithEmptyFields() {
        mViewHolder.setText("");
        assertThat(mViewHolder.getTextView().getText()).isEqualTo("");
    }
}