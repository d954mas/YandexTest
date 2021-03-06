package com.d954mas.android.yandextest.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
    private static final String TAG_ERROR = "TAG_ERROR";

    private DataLoadingModel dataLoadingModel;
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
        final FragmentManager fm = getSupportFragmentManager();
        final DataLoadingFragment retainDataLoadingFragment =
                (DataLoadingFragment) fm.findFragmentByTag(TAG_DATA_LOADING);
        if (retainDataLoadingFragment != null) {
            dataLoadingModel = retainDataLoadingFragment.getDataLoadingModel();
        } else {
            final DataLoadingFragment dataLoadingFragment = new DataLoadingFragment();
            fm.beginTransaction()
                    .add(dataLoadingFragment, TAG_DATA_LOADING)
                    .commit();
            fm.executePendingTransactions(); //in order to invoke DataLoadingFragment.onCreate()
            dataLoadingModel = dataLoadingFragment.getDataLoadingModel();
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
    public void onLoadingStart(DataLoadingModel signInModel) {
        if(progressDialog==null){
            progressDialog = new ProgressDialog(MainActivity.this);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Log.i(TAG, "start getting data");
    }

    @Override
    public void onLoadingSucceeded(DataLoadingModel signInModel) {
        Log.i(TAG, "successfully get data");
        progressDialog.dismiss();

        tabFragment = (TabFragment) getSupportFragmentManager().findFragmentByTag(TAG_TAB);
        if(tabFragment==null){
            tabFragment =new TabFragment();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, tabFragment, TAG_TAB);
        fragmentTransaction.commit();
    }

    @Override
    public void onLoadingFailed(DataLoadingModel signInModel) {
        progressDialog.dismiss();

        internetErrorFragment=(InternetErrorFragment) getSupportFragmentManager().findFragmentByTag(TAG_ERROR);
        if(internetErrorFragment==null){
            internetErrorFragment=new InternetErrorFragment();
        }
        internetErrorFragment.setDataLoadingModel(dataLoadingModel);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, internetErrorFragment,TAG_ERROR);
        fragmentTransaction.commit();
        Log.i(TAG, "failed get data");
    }

}

