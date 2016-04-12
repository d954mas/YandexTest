package com.d954mas.android.yandextest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by user on 09.04.2016.
 */
public class ArtistArrayAdapter extends BaseAdapter{
    protected List<ArtistModel> data;
    protected LayoutInflater lInflater;


    public ArtistArrayAdapter(Context context, List<ArtistModel> data){
        this.data = data;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
           view = lInflater.inflate(R.layout.artist_list_item, parent, false);
        }
        ImageView imageView= ((ImageView) view.findViewById(R.id.artist_element_image));
        imageView.setImageBitmap(null);
        ArtistModel artistModel = (ArtistModel) getItem(position);
        ImageLoader.getInstance().displayImage(artistModel.smallImageUrl,imageView);
                ((TextView) view.findViewById(R.id.artist_element_name)).setText(artistModel.name);
        return view;
    }
}
