package com.d954mas.android.yandextest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by user on 09.04.2016.
 */
public class ArtistArrayAdapter extends RecyclerView.Adapter<ArtistArrayAdapter.ArtistViewHolder>{
    protected List<ArtistModel> data;

    public ArtistArrayAdapter(List<ArtistModel> data){
        this.data = data;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ArtistViewHolder vh = new ArtistViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imageView.setImageBitmap(null);
        ArtistModel artistModel = data.get(position);
        ImageLoader.getInstance().displayImage(artistModel.smallImageUrl, holder.imageView);
        holder.textView.setText(artistModel.name);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    protected static class ArtistViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView textView;
        public ArtistViewHolder(View itemView) {
            super(itemView);
            imageView= ((ImageView) itemView.findViewById(R.id.artist_element_image));
            textView=((TextView) itemView.findViewById(R.id.artist_element_name));
        }
    }
}
