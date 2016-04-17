package com.d954mas.android.yandextest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by user on 17.04.2016.
 */
public abstract class ArrayAdapter<DATA,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<DATA> data;

    public ArrayAdapter(List<DATA> data){
        this.data=data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void changeData(List<DATA> data){
        this.data=data;
        notifyDataSetChanged();
    }
}
