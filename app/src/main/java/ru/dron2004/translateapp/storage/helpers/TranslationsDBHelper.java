package ru.dron2004.translateapp.storage.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TranslationsDBHelper extends SQLiteOpenHelper {
    private static final int version = 1;
    private static String dbFileName="translationsHistory.sql";

    public static final String _TABLE_NAME ="translations";
    public static final String _ID = "_id";
    public static final String _FROM = "_from";
    public static final String _TO = "_to";
    public static final String _TEXT = "textToTranslate";
    public static final String _TRANSLATION = "translatedText";
    public static final String _DATETIME = "datetime";
    public static final String _FAVORITE = "favorite";


    public TranslationsDBHelper(Context context) {
        super(context, dbFileName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("happy", "--- onCreate database "+dbFileName+" ---");
        //Создаем таблицу хранящую наименование языков
        db.execSQL("create table "+ _TABLE_NAME +" ("
                + _ID  + " INTEGER primary key autoincrement,"
                + _FROM + " TEXT,"
                + _TO + " TEXT,"
                + _TEXT + " TEXT,"
                + _TRANSLATION + " TEXT,"
                + _DATETIME + " INTEGER,"
                + _FAVORITE + " INTEGER"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("happy", "--- onUpgrade database  "+dbFileName+" ---");
    }
}
