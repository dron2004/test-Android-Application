package ru.dron2004.translateapp.storage.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.app.MainApplication;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.storage.helpers.TipsDBHelper;

public class TipsDAOImpl implements TipsDAO {
    private TipsCallBack listner;
    private TipsDBHelper dbHelper;

    public TipsDAOImpl(TipsCallBack callBack){
        listner = callBack;
        dbHelper = new TipsDBHelper(MainApplication.getAppContext());
    }

    @Override
    public void getTips(String text, Language language, int limit) {
        List<String> resultList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                TipsDBHelper._TABLE_NAME,
                null,
                TipsDBHelper._LANG+" = ? AND "+TipsDBHelper._TIP+" LIKE ?",
                new String[]{language.ident,text+"%"},
                null,
                null,
                null,
                Integer.toString(limit)
                );

        for (;cursor.moveToNext();){
            int tipCol = cursor.getColumnIndex(TipsDBHelper._TIP);
            resultList.add(cursor.getString(tipCol));
        }
        if (resultList.size() > 0)
            listner.onTipsSuccess(resultList);
        else
            listner.onTipsEmpty(text,language);
    }

    @Override
    public void updateTips(List<String> tips,Language language) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (String s:tips) {
                ContentValues cv = new ContentValues();
                cv.put(TipsDBHelper._TIP,s);
                cv.put(TipsDBHelper._LANG,language.ident);
                db.insertWithOnConflict(TipsDBHelper._TABLE_NAME,null,cv,SQLiteDatabase.CONFLICT_IGNORE);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        //Возвращаемся
    }
}
