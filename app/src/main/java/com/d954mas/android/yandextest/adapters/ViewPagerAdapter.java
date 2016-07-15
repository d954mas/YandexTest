package com.d954mas.android.yandextest.adapters;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.d954mas.android.yandextest.fragments.ArtistsFragment;
import com.d954mas.android.yandextest.fragments.GenresFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final String firstTitle;
    private final String secondTitle;

    public ViewPagerAdapter(FragmentManager fm,String firstTitle,String secondTitle) {
        super(fm);
        this.firstTitle = firstTitle;
        this.secondTitle = secondTitle;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("ViewPagerAdapter","getItem: "+position);
        switch (position){
            case 0:{
                return new ArtistsFragment();
            }
            case 1:{
                return new GenresFragment();
            }
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return firstTitle;
            case 1:return secondTitle;
            default:return "";
        }
    }
}
