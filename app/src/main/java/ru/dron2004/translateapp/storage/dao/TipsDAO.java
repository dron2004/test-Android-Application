package ru.dron2004.translateapp.storage.dao;

import java.util.List;

import ru.dron2004.translateapp.model.Language;

public interface TipsDAO {
    /**
     * Получить список подсказок для текста
     * @param text
     */
    void getTips(String text, Language language, int limit);

    void updateTips(List<String> tips,Language language);

    interface TipsCallBack{
        void onTipsSuccess(List<String> stringList);
        void onTipsEmpty(String text, Language language);
        void onTipsError(String errorMsg);
    }

}
