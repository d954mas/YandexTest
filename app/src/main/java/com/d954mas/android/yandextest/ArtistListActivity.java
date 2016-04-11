package com.d954mas.android.yandextest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.d954mas.android.yandextest.fragments.DataLoadingFragment;
import com.d954mas.android.yandextest.models.DataLoadingModel;

public class ArtistListActivity extends AppCompatActivity implements DataLoadingModel.Observer {
    private static final String TAG = "ArtistListActivity";
    private static final String TAG_WORKER = "TAG_WORKER";
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    DataLoadingModel dataLoadingModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        final DataLoadingFragment retainedWorkerFragment =
                (DataLoadingFragment) getSupportFragmentManager().findFragmentByTag(TAG_WORKER);

        if (retainedWorkerFragment != null) {
            dataLoadingModel = retainedWorkerFragment.getDataLoadingModel();
        } else {
            final DataLoadingFragment workerFragment = new DataLoadingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(workerFragment, TAG_WORKER)
                    .commit();
            dataLoadingModel = workerFragment.getDataLoadingModel();
        }
        dataLoadingModel.registerObserver(this);
        dataLoadingModel.loadData(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
               dataLoadingModel.loadData(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }

    @Override
    public void onSignInStarted(DataLoadingModel signInModel) {
        progressDialog = new ProgressDialog(ArtistListActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    public void onSignInSucceeded(DataLoadingModel signInModel) {
        progressDialog.dismiss();
        viewPagerAdapter.getArtistsFragment().setData(signInModel.getArtists());
    }

    @Override
    public void onSignInFailed(DataLoadingModel signInModel) {
        progressDialog.dismiss();
    }
}

