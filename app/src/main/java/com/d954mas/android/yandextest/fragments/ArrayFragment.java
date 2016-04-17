package com.d954mas.android.yandextest.fragments;

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
import com.d954mas.android.yandextest.adapters.ArrayAdapter;
import com.d954mas.android.yandextest.adapters.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


public abstract class ArrayFragment<DATA> extends Fragment {
    protected List<DATA>  dataList;
    protected List<DATA>  filteredDataList;
    protected RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayAdapter arrayAdapter;
    protected String TAG="ArrayFragment";

    public ArrayFragment(){
        dataList=new ArrayList<>();
        filteredDataList=new ArrayList<>();
        arrayAdapter=getArrayAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_artists, container, false);
        searchView = (SearchView) root.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
        recyclerView = (RecyclerView) root.findViewById(R.id.artist_list);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(arrayAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            Log.i(TAG, "Item clicked:" + position);
            itemClicked(position);
        }));
        return root;
    }

    protected void search(String newText){
        newText = newText.toLowerCase();
        filteredDataList.clear();
        for (int i = 0; i < dataList.size(); i++) {
            final String text =getSearchName(i).toLowerCase();
            if (text.contains(newText)) {
                filteredDataList.add(dataList.get(i));
            }
        }
        arrayAdapter.changeData(filteredDataList);
        arrayAdapter.notifyDataSetChanged();
    }

    protected void dataChanged(){
        arrayAdapter.changeData(filteredDataList);
        if(getView()!=null){
            Log.i(TAG, "data changed");
            if(searchView!=null && dataList.size()<6){
                searchView.setVisibility(View.GONE);
            }else{
                searchView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onPause() {
        super.onPause();
        //сбрасываю фокус с поиска при смене таба
        if(getView()!=null){
            SearchView search = (SearchView) getView().findViewById(R.id.search_view);
            search.setFocusable(false);
            Log.i(TAG, "fragment pause");
        }
    }

    public void setData(List<DATA> dataList){
        Log.i(TAG, "setData");
        if(dataList!=this.dataList){
            this.dataList=dataList;
            filteredDataList.clear();
            filteredDataList.addAll(dataList);
            if(searchView!=null ){
                //восстанавливаем поиск после смены ориентации
                search(searchView.getQuery().toString());
            }
            dataChanged();
        }
    }

    protected abstract String getSearchName(int position);

    protected abstract ArrayAdapter getArrayAdapter();

    protected abstract void itemClicked(int position);


}
