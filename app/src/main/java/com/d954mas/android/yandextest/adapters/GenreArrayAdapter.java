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
public class GenreArrayAdapter extends RecyclerView.Adapter<GenreArrayAdapter.GenreViewHolder>{
    protected List<String> data;
    public GenreArrayAdapter(List<String> data){
        this.data = data;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list_item, parent, false);
        GenreViewHolder vh = new GenreViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        String genre = data.get(position);
        holder.textView.setText(genre);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class GenreViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public GenreViewHolder(View itemView) {
            super(itemView);
            textView=((TextView) itemView.findViewById(R.id.genre_element_name));
        }
    }
}
