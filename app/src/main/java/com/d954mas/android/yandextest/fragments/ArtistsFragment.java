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
import android.widget.SearchView;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.activities.ArtistActivity;
import com.d954mas.android.yandextest.adapters.ArtistArrayAdapter;
import com.d954mas.android.yandextest.adapters.RecyclerItemClickListener;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.d954mas.android.yandextest.utils.DataSingleton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {
    private static final String TAG="ArtistsFragment";

    private View root;
    private List<ArtistModel> artists;
    private List<ArtistModel> filteredArtists;
    private RecyclerView lvMain;
    private ArtistArrayAdapter adapter;

    public ArtistsFragment() {
        artists=new ArrayList<>();
        filteredArtists=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root= inflater.inflate(R.layout.fragment_artists, container, false);
        Log.i(TAG, "on create view");
        SearchView search = (SearchView) root.findViewById(R.id.search_view);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                filteredArtists.clear();
                for (int i = 0; i < artists.size(); i++) {
                    final String text = artists.get(i).name.toLowerCase();
                    if (text.contains(newText)) {
                        filteredArtists.add(artists.get(i));
                    }
                }
                lvMain = (RecyclerView) root.findViewById(R.id.artist_list);
                adapter.setData(filteredArtists);
                adapter.notifyDataSetChanged();
                return true;
            }
        }); // call the QuerytextListner.

        lvMain = (RecyclerView) root.findViewById(R.id.artist_list);
        lvMain.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            Log.i(TAG, "Item cliced:" + position);
            ArtistModel artistModel = filteredArtists.get(position);
            Intent intent=new Intent(getContext(),ArtistActivity.class);
            intent.putExtra("artist", artistModel.getJson().toString());
            startActivity(intent);
        }));
        artists= DataSingleton.get().getArtists();
        filteredArtists=new ArrayList<>(artists);
        dataChanged(filteredArtists);
        return root;
    }

    protected void dataChanged(List<ArtistModel> artists){
        if(root!=null){
            Log.i(TAG,"data changed");
            adapter = new ArtistArrayAdapter(artists);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
            lvMain.setLayoutManager(gridLayoutManager);
            lvMain.setAdapter(adapter);
            SearchView search = (SearchView) root.findViewById(R.id.search_view);
            if(filteredArtists.size()<6){
                search.setVisibility(View.GONE);
            }else{
                search.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onPause() {  //not sure if you should use onDestroyView() instead
        super.onPause();
        if(getView()!=null){
            SearchView search = (SearchView) root.findViewById(R.id.search_view);
            search.setFocusable(false);
            Log.i(TAG,"fragment pause");
        }
    }

    public void setData(List<ArtistModel> artists){
        Log.i(TAG, "setData");
        if(artists!=this.artists){
            this.artists = artists;
            filteredArtists.clear();
            filteredArtists.addAll(artists);
            dataChanged(artists);

        }

    }
}
