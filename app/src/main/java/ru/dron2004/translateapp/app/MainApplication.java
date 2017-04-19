package ru.dron2004.translateapp.app;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }

    public static Context getAppContext(){
        return context;
    }
}
