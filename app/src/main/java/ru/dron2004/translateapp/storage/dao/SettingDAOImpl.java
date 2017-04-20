package ru.dron2004.translateapp.storage.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import ru.dron2004.translateapp.model.Language;


public class SettingDAOImpl implements SettingDAO {
    private static final String API_CHECK_PREF = "apiCheckDate";
    private static final String DB_CHECK_PREF = "LangSyncDate_";
    private Context app;
    private SharedPreferences sharedPref;

    public SettingDAOImpl(Context context){
        app = context;
        sharedPref =  app.getSharedPreferences(app.getPackageName(),Context.MODE_PRIVATE);
    }

    @Override
    public String getFromLanguageIdent() {
        return sharedPref.getString("LastLanguageFROM","ru");
    }

    @Override
    public void setFromLanguage(Language language) {
        sharedPref.edit()
                .putString("LastLanguageFROM",language.ident)
                .commit();
    }

    @Override
    public String getToLanguageIdent() {
        return sharedPref.getString("LastLanguageTO","en");
    }

    @Override
    public void setToLanguage(Language language) {
        sharedPref.edit()
                .putString("LastLanguageTO",language.ident)
                .commit();
    }

    @Override
    public long getLastDateSyncLanguages(String locale) {
        Log.d("happy",DB_CHECK_PREF+locale+": "+sharedPref.getLong(DB_CHECK_PREF+locale,0));
        return sharedPref.getLong(DB_CHECK_PREF+locale,0);
    }

    @Override
    public void setLastDateSyncLanguagesNow(String locale) {
        sharedPref.edit()
                .putLong(DB_CHECK_PREF+locale,(long) System.currentTimeMillis()/1000L)
                .commit();
    }

    @Override
    public void setLastDateCheckAPI() {
        sharedPref.edit()
                .putLong(API_CHECK_PREF,(long) System.currentTimeMillis()/1000L)
                .commit();
    }

    @Override
    public long getLastDateCheckAPI() {
        return sharedPref.getLong(API_CHECK_PREF,0);
    }
}
