package com.cedrotech.mytravel;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by isilva on 16/09/16.
 */
public class MyTravel extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
