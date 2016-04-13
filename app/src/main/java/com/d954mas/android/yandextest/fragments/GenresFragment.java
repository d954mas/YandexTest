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
import com.d954mas.android.yandextest.activities.ArtistGenreListActivity;
import com.d954mas.android.yandextest.adapters.GenreArrayAdapter;
import com.d954mas.android.yandextest.adapters.RecyclerItemClickListener;
import com.d954mas.android.yandextest.utils.DataSingleton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends Fragment {


    private static final String TAG = "GenresFragment";
    private List<String> genres;

    public GenresFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_genres, container, false);
        Log.i(TAG, "on create view");
        genres = DataSingleton.get().getGenres();
        if (genres != null) {
            RecyclerView lvMain = (RecyclerView) root.findViewById(R.id.genres_list);
            GenreArrayAdapter adapter = new GenreArrayAdapter(genres);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            lvMain.setLayoutManager(gridLayoutManager);
            lvMain.setAdapter(adapter);
            lvMain.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
                Log.i(TAG, "Item cliced:" + position);
                Intent intent=new Intent(getContext(),ArtistGenreListActivity.class);
                intent.putExtra("genre", genres.get(position));
                startActivity(intent);
            }));
        }
        return root;

    }

}
