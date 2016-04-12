package com.d954mas.android.yandextest.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.activities.ArtistActivity;
import com.d954mas.android.yandextest.adapters.ArtistArrayAdapter;
import com.d954mas.android.yandextest.models.ArtistModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {

    private View root;
    private List<ArtistModel> artists;

    public ArtistsFragment() {
       // setRetainInstance(true);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_artists, container, false);
        if(savedInstanceState!=null){

        }else{
            if(artists!=null){
                GridView lvMain = (GridView) root.findViewById(R.id.artist_list);
                ArtistArrayAdapter adapter = new ArtistArrayAdapter(getContext(), artists);
                lvMain.setAdapter(adapter);
                lvMain.setOnItemClickListener((parent, view, position, id) -> {
                    ArtistModel artistModel = (ArtistModel) adapter.getItem(position);
                    Intent intent=new Intent(getContext(),ArtistActivity.class);
                    intent.putExtra("artist", artistModel.getJson().toString());
                    startActivity(intent);
                });
            }

        }
        return root;
    }

    public void setData(List<ArtistModel> artists){
        this.artists = artists;
    }
}
