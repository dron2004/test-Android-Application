package ru.dron2004.translateapp.storage.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.app.MainApplication;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.storage.helpers.LanguagesDBHelper;
import ru.dron2004.translateapp.utility.LocaleUtils;

public class LanguageDAOImpl implements LanguageDAO {

    private UpdateLanguagesCallback updateLanguagesCallback;
    private LanguageListCallback languageListCallback;
    private LanguageCallback languageCallback;

    private LanguagesDBHelper dbHelper;

    public LanguageDAOImpl(){
        dbHelper = new LanguagesDBHelper(MainApplication.getAppContext(), LocaleUtils.getLocale());
    }

    //Вариант в возвратом обратного вызова после обновления таблицы языков
    public LanguageDAOImpl(UpdateLanguagesCallback callback){
        this();
        updateLanguagesCallback = callback;
    }

    //Вариант в возвратом обратного вызова после получения таблицы языков
    public LanguageDAOImpl(LanguageListCallback callback){
        this();
        languageListCallback = callback;
    }

    //Вариант в возвратом обратного вызова после получения одного языка
    public LanguageDAOImpl(LanguageCallback callback){
        this();
        languageCallback = callback;
    }

    @Override
    public void updateLanguages(List<Language> languageList) {
//        INSERT OR REPLACE INTO testTextKey (key, value) VALUES ('ru', 'Русский')
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Language l:languageList) {
                ContentValues cv = new ContentValues();
                cv.put(LanguagesDBHelper._IDENT,l.ident);
                cv.put(LanguagesDBHelper._TITLES,l.title);
                db.insertWithOnConflict(LanguagesDBHelper.LANG_TABLE_NAME,null,cv,SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        updateLanguagesCallback.onSuccess(true);
    }

    @Override
    public List<Language> getLanguages() {
        List<Language> res = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(LanguagesDBHelper.LANG_TABLE_NAME,null,null,null,null,null,null);
        for (;cursor.moveToNext();){
            Language language = null;
            int identCol = cursor.getColumnIndex(LanguagesDBHelper._IDENT);
            int titleCol = cursor.getColumnIndex(LanguagesDBHelper._TITLES);
            res.add(new Language(cursor.getString(identCol),cursor.getString(titleCol)));
        }
        return res;
    }

    @Override
    public Language getLanguageByIdent(String ident) {
        Language language = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(LanguagesDBHelper.LANG_TABLE_NAME,null,LanguagesDBHelper._IDENT+" = ?",new String[]{ident},null,null,null,"1");
        for (;cursor.moveToNext();){
            int identCol = cursor.getColumnIndex(LanguagesDBHelper._IDENT);
            int titleCol = cursor.getColumnIndex(LanguagesDBHelper._TITLES);
            language = new Language(cursor.getString(identCol),cursor.getString(titleCol));
        }
        return language;
    }
}
