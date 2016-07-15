package com.d954mas.android.yandextest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 7/15/16.
 */

public class ArtistFragment extends Fragment{
    private static final String DATA="DATA";
    private ArtistModel artistModel;


    public void setData(ArtistModel artistModel){
        this.artistModel = artistModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_artist,container,false);
        ImageView imageView= ((ImageView) view.findViewById(R.id.artist_big_image));
        imageView.setImageBitmap(null);
        ImageLoader.getInstance().displayImage(artistModel.bigImageUrl, imageView);
        ((TextView) view.findViewById(R.id.artist_biography)).setText(artistModel.description);
        StringBuilder builder=new StringBuilder();
        for(String genre: artistModel.genres){
            builder.append(genre);
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        ((TextView) view.findViewById(R.id.artist_genres)).setText(builder.toString());
        ((TextView) view.findViewById(R.id.artist_songs)).setText(getResources().getQuantityString(R.plurals.numberOfSongs,artistModel.tracks,artistModel.tracks));
        ((TextView) view.findViewById(R.id.artist_albums)).setText(getResources().getQuantityString(R.plurals.numberOfAlbums,artistModel.albums,artistModel.albums));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATA,artistModel.getJson().toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            try {
                artistModel=new ArtistModel(new JSONObject(savedInstanceState.getString(DATA)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
