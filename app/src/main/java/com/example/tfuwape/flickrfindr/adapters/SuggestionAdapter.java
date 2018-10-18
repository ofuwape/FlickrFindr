package com.example.tfuwape.flickrfindr.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfuwape.flickrfindr.R;
import com.example.tfuwape.flickrfindr.holder.InjectableBaseRecyclerViewHolder;
import com.example.tfuwape.flickrfindr.holder.SuggestionItemViewHolder;
import com.example.tfuwape.flickrfindr.models.PhotoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created
 * by Oluwatoni Fuwape on 8/23/16.
 */
public class SuggestionAdapter extends InjectableBaseAdapter {

    private List<String> terms = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private SuggestionItemViewHolder.SuggestionClickListener mClickListener;


    public SuggestionAdapter(Context context, SuggestionItemViewHolder.SuggestionClickListener clickListener) {
        super();
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mClickListener = clickListener;
    }


    public SuggestionAdapter(final List<String> terms, Context context, SuggestionItemViewHolder.SuggestionClickListener clickListener) {
        this(context, clickListener);
        setTerms(terms);
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    public void resetTerms() {
        this.terms.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InjectableBaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.suggestion_view, parent, false);
        return new SuggestionItemViewHolder(mContext, view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InjectableBaseRecyclerViewHolder holder, int position) {
        if (holder instanceof SuggestionItemViewHolder) {
            ((SuggestionItemViewHolder) holder).setText(terms.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public String getTerm(int position) {
        if (terms.size() > position) {
            return terms.get(position);
        } else {
            return null;
        }
    }

}
