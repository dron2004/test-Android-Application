package ru.dron2004.translateapp.utility;

import java.util.Locale;

import ru.dron2004.translateapp.app.MainApplication;

public class LocaleUtils {
    public static String getLocale(){
        return Locale.getDefault().getLanguage();
    }

    public static String getLocaleStringResource(int resourceID){
        return MainApplication.getAppContext().getString(resourceID);
    }
}
