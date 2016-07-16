package com.d954mas.android.yandextest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.fragments.AboutFragment;
import com.d954mas.android.yandextest.fragments.DataLoadingFragment;
import com.d954mas.android.yandextest.models.DataLoadingModel;
import com.d954mas.android.yandextest.models.DefaultLoadingObserver;

/**
 * Created by user on 7/14/16.
 */
public class MainActivity extends AppCompatActivity {
    private static final String LOADING_DONE="LOADING_DONE";
    private static final String TAG="MainActivity";
    FragmentManager manager;
    DataLoadingModel dataLoadingModel;
    private DefaultLoadingObserver defaultLoadingObserver;
    private ActionBar actionBar;

    //главная активити приложени,получает данные с сервера,и отображает, либо фрагмент м данными,либо фрагмент с ошибкой полученя данных
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=getSupportFragmentManager();
        if(savedInstanceState==null || !savedInstanceState.getBoolean(LOADING_DONE,true)) {
            loading();
        }
        actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                if(getSupportFragmentManager().getBackStackEntryCount()==1){
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    actionBar.setTitle(R.string.app_name);
                }
                return true;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new AboutFragment())
                        .addToBackStack(null).commit();
                return true;
            case R.id.feedback:
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "someemail@gmail.com" });
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                Email.putExtra(Intent.EXTRA_TEXT, "Dear d954mas, your app is awesome" + "");
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loading() {
        Log.d(TAG,"loading");
        String TAG_DATA_LOADING = "TAG_DATA_LOADING";
        //получаем фрагмент для загрузки данных(если он уже был создан)
        final DataLoadingFragment retainDataLoadingFragment =
                (DataLoadingFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATA_LOADING);
        if (retainDataLoadingFragment != null) {
            Log.d(TAG,"has data model");
            dataLoadingModel = retainDataLoadingFragment.getDataLoadingModel();
        } else {
            Log.d(TAG,"no data model");
            final DataLoadingFragment dataLoadingFragment = new DataLoadingFragment();
            manager.beginTransaction()
                    .add(dataLoadingFragment, TAG_DATA_LOADING)
                    .commit();
            dataLoadingModel = dataLoadingFragment.getDataLoadingModel();
        }
        defaultLoadingObserver=new DefaultLoadingObserver(this,manager);
        dataLoadingModel.registerObserver(defaultLoadingObserver);
        dataLoadingModel.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dataLoadingModel!=null && defaultLoadingObserver!=null){
            dataLoadingModel.unregisterObserver(defaultLoadingObserver);
            defaultLoadingObserver.dispose();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(defaultLoadingObserver!=null){
            outState.putBoolean(LOADING_DONE,defaultLoadingObserver.isLoadingDone());
        }
    }

}
