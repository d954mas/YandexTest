package com.d954mas.android.yandextest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import com.d954mas.android.yandextest.fragments.ArtistsFragment;
import com.d954mas.android.yandextest.fragments.GenresFragment;
import com.d954mas.android.yandextest.utils.DataSingleton;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import javax.inject.Inject;

@AutoFactory
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final String firstTitle;
    private final String secondTitle;

    private final DataSingleton dataSingleton;

    @Inject
    public ViewPagerAdapter(@Provided FragmentManager fm,
                            @Provided DataSingleton dataSingleton,
                            String firstTitle, String secondTitle) {
        super(fm);
        this.firstTitle = firstTitle;
        this.secondTitle = secondTitle;
        this.dataSingleton = dataSingleton;
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
    public Object instantiateItem(ViewGroup container, int position) {
        Object o= super.instantiateItem(container, position);
        Log.i("ViewPagerAdapter","instantiateItemItem: "+position);
        //восстанавливаем данные
        switch (position){
            case 0:
                ArtistsFragment artistsFragment = (ArtistsFragment) o;
                artistsFragment.setData(dataSingleton.getArtists());
                break;
            case 1:
                GenresFragment genresFragment= (GenresFragment) o;
                genresFragment.setData(dataSingleton.getGenres());
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
