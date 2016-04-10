package com.d954mas.android.yandextest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {

    private View root;

    public ArtistsFragment() {
        setRetainInstance(true);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_artists, container, false);
        if(savedInstanceState!=null){
                ;
        }
        return root;
    }

    public void setData(List<ArtistBean> artists){
        if(getView()!=null){
            ListView lvMain = (ListView) getView().findViewById(R.id.artist_list);
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
}
