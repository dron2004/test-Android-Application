package ru.dron2004.translateapp.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.dron2004.translateapp.app.MainApplication;

public class NetworkUtils {
    public static boolean checkIsOnline() {
        Context app = MainApplication.getAppContext();
        ConnectivityManager cm =
                (ConnectivityManager) app.getSystemService(app.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
