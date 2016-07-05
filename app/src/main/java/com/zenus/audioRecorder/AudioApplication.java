package com.zenus.audioRecorder;

import android.app.Application;
import android.content.Context;

public class AudioApplication extends Application{
    public static AudioApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized AudioApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return AudioApplication.getInstance().getApplicationContext();
    }
}
