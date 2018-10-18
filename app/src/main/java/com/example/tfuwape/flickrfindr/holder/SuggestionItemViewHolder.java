package com.example.tfuwape.flickrfindr.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.tfuwape.flickrfindr.R;

import butterknife.BindView;

/**
 * Created by
 * Oluwatoni Fuwape on 8/23/16.
 */
public class SuggestionItemViewHolder extends InjectableBaseRecyclerViewHolder {

    @BindView(R.id.suggestion_text_view)
    TextView textView;

    public SuggestionItemViewHolder(Context context, View itemView, SuggestionClickListener clickListener) {
        super(context, itemView, clickListener);
    }

    public void setText(final String text) {
        textView.setText(text);
    }

    /**
     * Click listener for handling clicks on the item
     */
    public interface SuggestionClickListener extends OnClickListener {
        /**
         * Invoked when the user taps on the item
         *
         * @param position Position of view holder in recycler view
         */
        void onClick(int position);
    }

}
