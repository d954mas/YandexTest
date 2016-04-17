package com.d954mas.android.yandextest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d954mas.android.yandextest.R;

import java.util.List;

/**
 * Created by user on 13.04.2016.
 */
public class GenreArrayAdapter extends ArrayAdapter<String,GenreArrayAdapter.GenreViewHolder> {
    public GenreArrayAdapter(List<String> data) {
        super(data);
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        super.onCreateViewHolder(parent,viewType);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list_item, parent, false);
        GenreViewHolder vh = new GenreViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        String genre = data.get(position);
        holder.textView.setText(genre);
    }

    protected static class GenreViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public GenreViewHolder(View itemView) {
            super(itemView);
            textView=((TextView) itemView.findViewById(R.id.genre_element_name));
        }
    }
}
