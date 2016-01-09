package com.marvik.apps.coreutils.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marvik.apps.coreutils.utils.Utils;

/**
 * Created by victor on 8/24/2015.
 */
public abstract class ActivityWrapper extends AppCompatActivity implements View.OnClickListener,
        com.marvik.apps.coreutils.fragments.FragmentWrapper.OnCreateFragment {

    private Utils utils;

    public Utils getUtils() {
        return utils;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAll();
        onCreateActivity(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyActivity();
    }

    @Override
    public void setActivityTitle(String activityTitle) {
        mActivityTitle.setText(activityTitle);
    }

    protected abstract void onCreateActivity(Bundle savedInstanceState);

    protected abstract void onResumeActivity();

    protected abstract void onPauseActivity();

    protected abstract void onDestroyActivity();

    private void initAll() {
        utils = new Utils(ActivityWrapper.this);
        attachActionBar();
    }

    private View customActionBar;
    private ImageView mDrawerHandle;
    private TextView mActivityTitle;

    private void attachActionBar() {


    }


    public View getDrawerHandle() {
        return mDrawerHandle;
    }

    @Override
    public void onClick(View v) {

    }


}
