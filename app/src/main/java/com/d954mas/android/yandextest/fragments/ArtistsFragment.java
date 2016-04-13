package com.d954mas.android.yandextest.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.activities.ArtistActivity;
import com.d954mas.android.yandextest.adapters.ArtistArrayAdapter;
import com.d954mas.android.yandextest.adapters.RecyclerItemClickListener;
import com.d954mas.android.yandextest.models.ArtistModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {
    private static final String TAG="ArtistsFragment";

    private View root;
    private List<ArtistModel> artists;
    private RecyclerView lvMain;
    private ArtistArrayAdapter adapter;

    public ArtistsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_artists, container, false);
        Log.i(TAG,"on create view");
        if(artists!=null){
            lvMain = (RecyclerView) root.findViewById(R.id.artist_list);
            adapter = new ArtistArrayAdapter(artists);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
            lvMain.setLayoutManager(gridLayoutManager);
            lvMain.setAdapter(adapter);
            lvMain.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
                Log.i(TAG, "Item cliced:" + position);
                ArtistModel artistModel = artists.get(position);
                Intent intent=new Intent(getContext(),ArtistActivity.class);
                intent.putExtra("artist", artistModel.getJson().toString());
                startActivity(intent);

            }));
        }
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"save instance state");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored");
    }

    public void setData(List<ArtistModel> artists){
        Log.i(TAG,"setData");
        this.artists = artists;
        if(root!=null){
            lvMain = (RecyclerView) root.findViewById(R.id.artist_list);
            adapter = new ArtistArrayAdapter(artists);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
            lvMain.setLayoutManager(gridLayoutManager);
            lvMain.setAdapter(adapter);
            lvMain.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
                Log.i(TAG, "Item cliced:" + position);
                ArtistModel artistModel = artists.get(position);
                Intent intent=new Intent(getContext(),ArtistActivity.class);
                intent.putExtra("artist", artistModel.getJson().toString());
                startActivity(intent);

            }));
        }
    }
}
