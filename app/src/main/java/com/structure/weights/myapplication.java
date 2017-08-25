package com.structure.weights;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.structure.weights.R;

/**
 * Created by sohampandya on 01/06/16.
 */
public class myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));
    }
}
