package com.d954mas.android.yandextest.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.fragments.ArtistListFragment;
import com.d954mas.android.yandextest.fragments.DataLoadingFragment;
import com.d954mas.android.yandextest.fragments.InternerErrorFragment;
import com.d954mas.android.yandextest.models.DataLoadingModel;

public class ArtistListActivity extends AppCompatActivity implements DataLoadingModel.Observer {
    private static final String TAG = "ArtistListActivity";
    private static final String TAG_WORKER = "TAG_WORKER";
    DataLoadingModel dataLoadingModel;
    ArtistListFragment artistListFragment;
    InternerErrorFragment internetErrorFragment;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)actionBar.setTitle("Исполнители");
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

        artistListFragment=new ArtistListFragment();
        internetErrorFragment=new InternerErrorFragment();
        internetErrorFragment.setDataLoadingModel(dataLoadingModel);

        dataLoadingModel.registerObserver(this);
        dataLoadingModel.loadData(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataLoadingModel.unregisterObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
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
        Log.i(TAG, "start getting data");
    }

    @Override
    public void onSignInSucceeded(DataLoadingModel signInModel) {
        Log.i(TAG,"successfully get data");
        progressDialog.dismiss();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, artistListFragment);
        fragmentTransaction.commit();
        artistListFragment.setData(signInModel.getArtists());
    }

    @Override
    public void onSignInFailed(DataLoadingModel signInModel) {
        progressDialog.dismiss();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, internetErrorFragment);
        fragmentTransaction.commit();
        Log.i(TAG, "failed get data");

    }
}

