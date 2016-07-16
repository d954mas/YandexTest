package com.d954mas.android.yandextest.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.List;

//абстрактный адаптер для всех адаптеров со списком
public abstract class ArrayAdapter<DATA,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = "ArrayAdapter";
    protected List<DATA> data;
    private int lastPosition=-1;

    public ArrayAdapter(List<DATA> data){
        this.data=data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d(TAG, "onCreateViewHolder:");
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
     //   Log.d(TAG, "onBindViewHolder:" + position);
        holder.itemView.clearAnimation();
        int translationY;
        if (position>lastPosition){
            translationY=300;
        }else{
            translationY=-300;
        }

        AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator translationYAnimator=ObjectAnimator.ofFloat(holder.itemView,"translationY",translationY,0);
        ObjectAnimator translationXAnimator=ObjectAnimator.ofFloat(holder.itemView,"translationX",-25,25,-20,20,-15,15,-10,10,-5,5,0);
        animatorSet.playTogether(translationXAnimator,translationYAnimator);
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();

        lastPosition=position;
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
