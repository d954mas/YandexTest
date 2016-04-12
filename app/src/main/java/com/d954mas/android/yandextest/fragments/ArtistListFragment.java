package com.d954mas.android.yandextest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.adapters.ViewPagerAdapter;
import com.d954mas.android.yandextest.models.ArtistModel;

import java.util.List;

/**
 * Created by user on 11.04.2016.
 */
public class ArtistListFragment extends Fragment {
    private static final String TAG="ArtistListFragment";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<ArtistModel> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");
        View root= inflater.inflate(R.layout.artist_list_fragment,container,false);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if(data!=null){
            Log.i(TAG,"set data in on create view");
            viewPagerAdapter.setData(data);
        }
        return root;
    }

    public void setData(List<ArtistModel> artistModels){
        if(viewPagerAdapter!=null){
            Log.i(TAG,"set data");
            viewPagerAdapter.setData(artistModels);
        }
       else{
            data= artistModels;
        }

    }

}
