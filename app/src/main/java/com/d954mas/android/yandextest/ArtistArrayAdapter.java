package com.d954mas.android.yandextest;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 09.04.2016.
 */
public class ArtistArrayAdapter extends BaseAdapter{
    protected List<ArtistBean> data;
    protected LayoutInflater lInflater;


    public ArtistArrayAdapter(Context context, List<ArtistBean> data){
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
        ArtistBean artistBean= (ArtistBean) getItem(position);
        ImageLoader.getInstance().displayImage(artistBean.smallImageUrl,imageView);
                ((TextView) view.findViewById(R.id.artist_element_name)).setText(artistBean.name);
        return view;
    }
}
