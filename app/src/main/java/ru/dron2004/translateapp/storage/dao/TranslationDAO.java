package ru.dron2004.translateapp.storage.dao;

import java.util.List;

import ru.dron2004.translateapp.interactors._BaseCallback;
import ru.dron2004.translateapp.model.Translation;

public interface TranslationDAO {
    /**
     * Получить перевод
     * @param text
     * @return
     */
    Translation getTranslation(String text);

    /**
     * Сохранить перевод
     * @param translation
     */
    void saveTranslation(Translation translation);

    interface TranslationCallback extends _BaseCallback<Translation> {}

    //TODO Убедиться что новое подходит и Убить старое
//    //API обратного вызова
//    interface TranslationCallback {
//        /**
//         * Перевод получен успешно
//         * @param translation
//         */
//        void onTranslationSuccess(Translation translation);
//
//        /**
//         * Ошибка получения перевода
//         * @param errorMsg - текст ошибки
//         */
//        void onTranslationFail(String errorMsg);
//    }
}
