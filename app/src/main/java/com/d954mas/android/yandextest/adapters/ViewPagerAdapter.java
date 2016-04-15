package com.d954mas.android.yandextest.adapters;

/**
 * Created by user on 10.04.2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.d954mas.android.yandextest.fragments.ArtistsFragment;
import com.d954mas.android.yandextest.fragments.GenresFragment;
import com.d954mas.android.yandextest.utils.DataSingleton;

/**
 * Created by Admin on 11-12-2015.
 */
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
                ArtistsFragment artistsFragment = new ArtistsFragment();
                return artistsFragment;
            }
            case 1:{
                GenresFragment genresFragment = new GenresFragment();
                return genresFragment;
            }
            default:return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object o= super.instantiateItem(container, position);
        Log.i("ViewPagerAdapter","instantiateItemItem: "+position);
        switch (position){
            case 0:
                ArtistsFragment artistsFragment = (ArtistsFragment) o;
                artistsFragment.setData(DataSingleton.get().getArtists());
                break;
        }
        return o;
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
