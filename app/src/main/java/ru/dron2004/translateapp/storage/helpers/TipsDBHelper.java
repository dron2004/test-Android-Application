package ru.dron2004.translateapp.storage.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TipsDBHelper extends SQLiteOpenHelper{
    private static final int version = 1;
    private static String dbFileName="tipsHistory.sql";

    public static final String _TABLE_NAME ="tips";
    public static final String _LANG = "language";
    public static final String _TIP = "tip";


    public TipsDBHelper(Context context) {
        super(context, dbFileName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("happy", "--- onCreate database "+dbFileName+" ---");
        //Создаем таблицу хранящую наименование языков
        db.execSQL("create table "+ _TABLE_NAME +" ("
                + _LANG + " TEXT,"
                + _TIP + " TEXT NOT NULL PRIMARY KEY"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
