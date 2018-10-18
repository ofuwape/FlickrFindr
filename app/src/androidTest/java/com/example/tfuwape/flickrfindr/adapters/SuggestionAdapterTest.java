package com.example.tfuwape.flickrfindr.adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.widget.FrameLayout;

import com.example.tfuwape.flickrfindr.MockFactory;
import com.example.tfuwape.flickrfindr.holder.SuggestionItemViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class SuggestionAdapterTest {

    private SuggestionAdapter mAdapter;

    private SuggestionItemViewHolder.SuggestionClickListener mMockListener =
            mock(SuggestionItemViewHolder.SuggestionClickListener.class);

    private ArrayList<String> searchTerms;
    private Context mContext;

    @Before
    public void setUp() {
        searchTerms = MockFactory.createListOfTerms(5);
        mContext = getInstrumentation().getTargetContext();
        mAdapter = new SuggestionAdapter(searchTerms, mContext, mMockListener);
    }

    @Test
    public void testSetItemsChangesState() {
        ArrayList<String> items = MockFactory.createListOfTerms(3);
        mAdapter.setTerms(items);
        assertThat(mAdapter.getItemCount()).isEqualTo(items.size());
    }

    @Test
    public void testEmptyState() {
        mAdapter.resetTerms();
        assertThat(mAdapter.getItemCount()).isEqualTo(0);
    }

    @Test
    public void testOnCreateViewHolderReturnsItem() {
        SuggestionItemViewHolder holder =
                (SuggestionItemViewHolder) mAdapter.onCreateViewHolder(new FrameLayout(mContext), 0);
        assertThat(holder).isNotNull();
        assertThat(holder).isInstanceOf(SuggestionItemViewHolder.class);
    }

    @Test
    public void testOnBindViewHolderUpdatesHolder() {
        SuggestionItemViewHolder holder = mock(SuggestionItemViewHolder.class);
        mAdapter.onBindViewHolder(holder, 0);
        verify(holder).setText(searchTerms.get(0));
    }

    @Test
    public void testOnGetItem() {
        assertThat(mAdapter.getTerm(0)).isEqualTo(searchTerms.get(0));
    }


}
