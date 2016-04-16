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
import com.d954mas.android.yandextest.activities.ArtistGenreListActivity;
import com.d954mas.android.yandextest.adapters.GenreArrayAdapter;
import com.d954mas.android.yandextest.adapters.RecyclerItemClickListener;
import com.d954mas.android.yandextest.utils.DataSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends Fragment {
    private static final String TAG = "GenresFragment";
    private List<String> genres;
    private List<String> filteredGenres;
    private GenreArrayAdapter adapter;

    public GenresFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_genres, container, false);
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
                filteredGenres.clear();
                for (int i = 0; i < genres.size(); i++) {
                    final String text = genres.get(i).toLowerCase();
                    if (text.contains(newText)) {
                        filteredGenres.add(genres.get(i));
                    }
                }
                adapter.setData(filteredGenres);
                adapter.notifyDataSetChanged();
                return true;
            }
        }); // call the QuerytextListner.
        genres = DataSingleton.get().getGenres();
        filteredGenres=new ArrayList<>();
        filteredGenres.addAll(genres);
        if (genres != null) {
            RecyclerView lvMain = (RecyclerView) root.findViewById(R.id.genres_list);
            adapter = new GenreArrayAdapter(genres);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            lvMain.setLayoutManager(gridLayoutManager);
            lvMain.setAdapter(adapter);
            lvMain.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
                Log.i(TAG, "Item cliced:" + position);
                Intent intent=new Intent(getContext(),ArtistGenreListActivity.class);
                intent.putExtra("genre", filteredGenres.get(position));
                startActivity(intent);
            }));
        }
        return root;

    }

}
