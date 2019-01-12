package com.example.bw;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        MobSDK.init(this);
        mContext=getApplicationContext();

        LeakCanary.install(this);
    }
    public  static Context getApplication (){
        return mContext;
    }
}
