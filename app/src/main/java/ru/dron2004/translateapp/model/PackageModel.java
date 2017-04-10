package ru.dron2004.translateapp.model;

import android.content.Context;
import android.content.res.Resources;

public class PackageModel {
    private Context context;

    public PackageModel(Context context) {
        this.context = context;
    }

    public Context getAppContext(){
        return this.context;
    }

    public Resources getResources() {
        return this.context.getResources();
    }

    public String getString(int resourceId) {
        return this.context.getResources().getText(resourceId).toString();
    }

}
