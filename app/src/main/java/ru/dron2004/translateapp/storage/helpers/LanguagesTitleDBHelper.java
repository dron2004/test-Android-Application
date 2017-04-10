package ru.dron2004.translateapp.storage.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * База данных для хранения названий языков
 * создается с учетом локали устройства
 * пополняется накопительно
 */
public class LanguagesTitleDBHelper extends SQLiteOpenHelper {
    private static final int version = 1;
    private static String dbFileName="languageTitles_%%%UI%%%.sql";

    private String userLanguage;

    public static final String LANG_TABLE_NAME="languages";
    public static final String _IDENT = "_ident";
    public static final String _TITLES = "_titles";


    public LanguagesTitleDBHelper(Context context, String uiLang) {
        //Создаем базу данных констант к конкретному UI
        super(context, dbFileName.replace("%%%UI%%%",uiLang), null, version);
        userLanguage = uiLang;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("happy", "--- onCreate database "+dbFileName+" ---");
        //Создаем таблицу хранящую наименование языков
        db.execSQL("create table "+LANG_TABLE_NAME+" ("
                + _IDENT  + " TEXT NOT NULL PRIMARY KEY,"
                + _TITLES + " TEXT,"
                + " UNIQUE ("+_IDENT+") ON CONFLICT REPLACE"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("happy", "--- onUpgrade database  "+dbFileName+" ---");
    }
}
