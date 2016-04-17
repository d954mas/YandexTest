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


public class ArtistArrayAdapter extends ArrayAdapter<ArtistModel,ArtistArrayAdapter.ArtistViewHolder> {
    public ArtistArrayAdapter(List<ArtistModel> data) {
        super(data);
    }


    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        super.onCreateViewHolder(parent, viewType);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_item, parent, false);
        return new ArtistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);

        holder.imageView.setImageBitmap(null);
        ArtistModel artistModel = data.get(position);
        ImageLoader.getInstance().displayImage(artistModel.smallImageUrl, holder.imageView);
        holder.textView.setText(artistModel.name);
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
