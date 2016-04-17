package com.d954mas.android.yandextest.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.fragments.DataLoadingFragment;
import com.d954mas.android.yandextest.fragments.InternetErrorFragment;
import com.d954mas.android.yandextest.fragments.TabFragment;
import com.d954mas.android.yandextest.models.DataLoadingModel;

//главная активити приложени,получает данные с сервера,и отображает, либо фрагмент м данными,либо фрагмент с ошибкой полученя данных
public class MainActivity extends AppCompatActivity implements DataLoadingModel.Observer {
    private static final String TAG = "MainActivity";
    private static final String TAG_DATA_LOADING = "TAG_DATA_LOADING";
    private static final String TAG_TAB = "TAG_TAB";
    DataLoadingModel dataLoadingModel;
    TabFragment tabFragment;
    InternetErrorFragment internetErrorFragment;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }

        //получаем фрагмент для загрузки данных(если он уже был создан)
        final DataLoadingFragment retainDataLoadingFragment =
                (DataLoadingFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATA_LOADING);
        if (retainDataLoadingFragment != null) {
            dataLoadingModel = retainDataLoadingFragment.getDataLoadingModel();
        } else {
            final DataLoadingFragment dataLoadingFragment = new DataLoadingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(dataLoadingFragment, TAG_DATA_LOADING)
                    .commit();
            dataLoadingModel = dataLoadingFragment.getDataLoadingModel();
        }

        if(savedInstanceState==null){
            tabFragment =new TabFragment();
            internetErrorFragment=new InternetErrorFragment();
            internetErrorFragment.setDataLoadingModel(dataLoadingModel);
        }else{
            tabFragment = (TabFragment) getSupportFragmentManager().findFragmentByTag(TAG_TAB);
        }
        dataLoadingModel.registerObserver(this);
        //грузим данные,если они уже загруженны,то массив вернется сразу
        dataLoadingModel.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataLoadingModel.unregisterObserver(this);
    }


    //DATA LOADING LISTENERS
    @Override
    public void onSignInStarted(DataLoadingModel signInModel) {
        if(progressDialog==null){
            progressDialog = new ProgressDialog(MainActivity.this);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Log.i(TAG, "start getting data");
    }

    @Override
    public void onSignInSucceeded(DataLoadingModel signInModel) {
        Log.i(TAG, "successfully get data");
        progressDialog.dismiss();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, tabFragment, TAG_TAB);
        fragmentTransaction.commit();
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

