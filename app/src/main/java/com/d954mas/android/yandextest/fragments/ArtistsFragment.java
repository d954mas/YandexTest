package com.d954mas.android.yandextest.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.d954mas.android.yandextest.ArtistArrayAdapter;
import com.d954mas.android.yandextest.ArtistBean;
import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.activity.ArtistActivity;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {

    private View root;
    private List<ArtistBean> artists;

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
                ListView lvMain = (ListView) root.findViewById(R.id.artist_list);
                ArtistArrayAdapter adapter = new ArtistArrayAdapter(getContext(), artists);
                lvMain.setAdapter(adapter);
                lvMain.setOnItemClickListener((parent, view, position, id) -> {
                    ArtistBean artistBean= (ArtistBean) adapter.getItem(position);
                    Intent intent=new Intent(getContext(),ArtistActivity.class);
                    intent.putExtra("artist",artistBean.getJson().toString());
                    startActivity(intent);
                });
            }

        }
        return root;
    }

    public void setData(List<ArtistBean> artists){
        this.artists = artists;
    }
}
