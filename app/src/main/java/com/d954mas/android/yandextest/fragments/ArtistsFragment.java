package com.d954mas.android.yandextest.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.activities.ArtistActivity;
import com.d954mas.android.yandextest.adapters.ArtistArrayAdapter;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.d954mas.android.yandextest.utils.DataSingleton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {
    private static final String GRID_VIEW_KEY="gridViewKey";
    private static final String TAG="ArtistsFragment";

    private View root;
    private List<ArtistModel> artists;
    private GridView lvMain;
    private ArtistArrayAdapter adapter;

    public ArtistsFragment() {
       // setRetainInstance(true);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_artists, container, false);
        Log.i(TAG,"on create view");
        artists= DataSingleton.get().getArtists();
        if(artists!=null){
            lvMain = (GridView) root.findViewById(R.id.artist_list);
            adapter = new ArtistArrayAdapter(getContext(), artists);
            lvMain.setAdapter(adapter);
            lvMain.setOnItemClickListener((parent, view, position, id) -> {
                ArtistModel artistModel = (ArtistModel) adapter.getItem(position);
                Intent intent=new Intent(getContext(),ArtistActivity.class);
                intent.putExtra("artist", artistModel.getJson().toString());
                startActivity(intent);
                });
        }
        if(savedInstanceState!=null){
            lvMain.onRestoreInstanceState(savedInstanceState.getParcelable(GRID_VIEW_KEY));
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
        outState.putParcelable(GRID_VIEW_KEY, lvMain.onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored");
    }

    public void setData(List<ArtistModel> artists){
        Log.i(TAG,"setData");
        this.artists = artists;
    }
}
