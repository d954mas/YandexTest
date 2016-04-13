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
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        GenreViewHolder vh = new GenreViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String genre = data.get(position);
      //  ImageLoader.getInstance().displayImage(artistModel.smallImageUrl, holder.imageView);
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
