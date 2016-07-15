package com.d954mas.android.yandextest.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.d954mas.android.yandextest.FragmentInjectors;
import com.d954mas.android.yandextest.FragmentModule;
import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.adapters.ViewPagerAdapterFactory;
import dagger.Subcomponent;

import javax.inject.Inject;

//фрагмент с табами(все артисты и жанры)
public class TabFragment extends Fragment {
    private static final String TAG="TabFragment";
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Inject
    ViewPagerAdapterFactory viewPagerAdapterFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentInjectors.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");
        View root= inflater.inflate(R.layout.tab_fragment,container,false);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapterFactory.create(getString(R.string.all), getString(R.string.byGenre)));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                //убираем фокус со строки поиска(скрываем клаву)
                final InputMethodManager imm = (InputMethodManager)getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
            }
        });
        return root;
    }

    @Subcomponent(modules = FragmentModule.class)
    public interface Component {
        void inject(TabFragment f);
    }
}
