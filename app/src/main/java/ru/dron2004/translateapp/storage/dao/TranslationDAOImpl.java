package ru.dron2004.translateapp.storage.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.app.MainApplication;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.storage.helpers.TranslationsDBHelper;

public class TranslationDAOImpl implements TranslationDAO {
    private TranslationCallback translationListner;
    private TranslationsListCallback translationsListListner;
    private TranslationsDBHelper dbHelper;

    public TranslationDAOImpl(TranslationCallback callback,TranslationsListCallback listCallback) {
        translationListner = callback;
        translationsListListner = listCallback;
        dbHelper = new TranslationsDBHelper(MainApplication.getAppContext());
    }


    @Override
    public Translation getTranslation(String text, TranslateDirection translateDirection) {
        Translation translation = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                TranslationsDBHelper._TABLE_NAME
                ,null,
                //                               1                                        2                                      3
                TranslationsDBHelper._FROM + " = ? AND "+TranslationsDBHelper._TEXT + " = ? AND "+TranslationsDBHelper._TO + " = ? ",
                new String[]{translateDirection.from.ident, text.trim(), translateDirection.to.ident}
                ,null
                ,null
                ,null
                ,"1");

        for (;cursor.moveToNext();){
            int translatedTextCol = cursor.getColumnIndex(TranslationsDBHelper._TRANSLATION);
            int favoriteCol = cursor.getColumnIndex(TranslationsDBHelper._FAVORITE);
            int idCol = cursor.getColumnIndex(TranslationsDBHelper._ID);

            translation = new Translation(translateDirection,text.trim(),cursor.getString(translatedTextCol));

            translation.setId(cursor.getInt(idCol));
            if (cursor.getInt(favoriteCol) > 0) translation.setFavorite(true);
        }
        return translation;
    }

    @Override
    public synchronized Translation saveTranslation(Translation translation) {
        Long rowID;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(TranslationsDBHelper._DATETIME,System.currentTimeMillis()/1000L);
            cv.put(TranslationsDBHelper._FROM,translation.translateDirection.from.ident);
            cv.put(TranslationsDBHelper._TO,translation.translateDirection.to.ident);
            cv.put(TranslationsDBHelper._TEXT,translation.textToTranslate);
            cv.put(TranslationsDBHelper._TRANSLATION,translation.translatedText);
            if (translation.isFavorite())
                cv.put(TranslationsDBHelper._FAVORITE,1);
            else
                cv.put(TranslationsDBHelper._FAVORITE,0);
            rowID = db.insert(TranslationsDBHelper._TABLE_NAME,null,cv);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        if (rowID != null)
            translation.setId(rowID);
        return translation;
    }

    @Override
    public synchronized Translation toggleFavoriteTranslation(Translation translation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            //Инвертируем флаг изббранного
            if (translation.isFavorite()) {
                cv.put(TranslationsDBHelper._FAVORITE, 0);
                translation.setFavorite(false);
            } else {
                cv.put(TranslationsDBHelper._FAVORITE, 1);
                translation.setFavorite(true);
            }
            db.update(TranslationsDBHelper._TABLE_NAME,cv,TranslationsDBHelper._ID + " = ?",new String[]{Long.toString(translation.getId())});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return translation;
    }

    @Override
    public void getAllTranslation(boolean favorite) {
        String where = null;
        String[] whereArgs = null;

        if (favorite) {
            //Поиск только избранных
            where = TranslationsDBHelper._FAVORITE + " > ? ";
            whereArgs = new String[]{"0"};
        }
        List<Translation> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                TranslationsDBHelper._TABLE_NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                TranslationsDBHelper._FAVORITE + " DESC, "+ TranslationsDBHelper._DATETIME + " DESC"
                );

        for (;cursor.moveToNext();){
            int originalTextCol = cursor.getColumnIndex(TranslationsDBHelper._TEXT);
            int fromTextCol = cursor.getColumnIndex(TranslationsDBHelper._FROM);
            int toTextCol = cursor.getColumnIndex(TranslationsDBHelper._TO);

            int translatedTextCol = cursor.getColumnIndex(TranslationsDBHelper._TRANSLATION);
            int favoriteCol = cursor.getColumnIndex(TranslationsDBHelper._FAVORITE);
            int idCol = cursor.getColumnIndex(TranslationsDBHelper._ID);
            Translation translation = new Translation(
                    new TranslateDirection(
                            new Language(cursor.getString(fromTextCol)),
                            new Language(cursor.getString(toTextCol))
                    ),
                    cursor.getString(originalTextCol),
                    cursor.getString(translatedTextCol));
            translation.setId(cursor.getInt(idCol));
            if (cursor.getInt(favoriteCol) > 0)
                translation.setFavorite(true);
            list.add(translation);
        }
        translationsListListner.onTranslationListSuccess(list);
    }

    @Override
    public synchronized void removeTranslation(Translation translation) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            //Инвертируем флаг изббранного
            if (translation.isFavorite()) {
                cv.put(TranslationsDBHelper._FAVORITE, 0);
                translation.setFavorite(false);
            } else {
                cv.put(TranslationsDBHelper._FAVORITE, 1);
                translation.setFavorite(true);
            }
            db.delete(TranslationsDBHelper._TABLE_NAME,TranslationsDBHelper._ID + " = ?",new String[]{Long.toString(translation.getId())});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
