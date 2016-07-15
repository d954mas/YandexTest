package com.d954mas.android.yandextest.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.fragments.InternetErrorFragment;
import com.d954mas.android.yandextest.fragments.TabFragment;

/**
 * Created by user on 7/15/16.
 */

public class DefaultLoadingObserver implements DataLoadingModel.Observer {

    private ProgressDialog progressDialog;
    private Context context;
    private FragmentManager manager;
    private static final String TAG = "DefaultLoadingObserver";
    private boolean loadindDone;

    public DefaultLoadingObserver(Context context, FragmentManager manager){
        this.context=context;
        this.manager = manager;
        loadindDone=false;
    }

    @Override
    public void onLoadingStart(DataLoadingModel loadingModel) {
        if(progressDialog==null){
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Log.i(TAG, "start getting data");
    }

    @Override
    public void onLoadingSucceeded(DataLoadingModel loadingModel) {
        Log.i(TAG, "successfully get data");
        progressDialog.dismiss();
        TabFragment tabFragment =new TabFragment();
        manager.beginTransaction().replace(R.id.container, tabFragment).commit();
        loadindDone=true;
        dispose();

    }

    @Override
    public void onLoadingFailed(DataLoadingModel loadingModel) {
        progressDialog.dismiss();
        InternetErrorFragment internetErrorFragment=new InternetErrorFragment();
        internetErrorFragment.setDataLoadingModel(loadingModel);
        manager.beginTransaction().replace(R.id.container, internetErrorFragment).commit();
        Log.i(TAG, "failed get data");
        loadindDone=true;
        dispose();
    }

    public void dispose(){
        if(progressDialog!=null)progressDialog.dismiss();
        progressDialog=null;
        context=null;
    }

    public boolean isLoadingDone(){
        return loadindDone;
    }
}
