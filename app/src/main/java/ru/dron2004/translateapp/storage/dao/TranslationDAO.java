package ru.dron2004.translateapp.storage.dao;

import java.util.List;

import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.model.Translation;

public interface TranslationDAO {

    /**
     * Получить перевод
     * @param text
     * @return
     */
    Translation getTranslation(String text, TranslateDirection translateDirection);

    /**
     * Сохранить перевод
     * @param translation
     */
    Translation saveTranslation(Translation translation);

    /**
     * Пометить избранным
     * @param translation
     */
    Translation toggleFavoriteTranslation(Translation translation);

    /**
     * Запросить список истории
     */
    void getAllTranslation(boolean favorite);

    /**
     * Удалить перевод из истории
     * @param translation
     */
    void removeTranslation(Translation translation);


    //API обратного вызова
    interface TranslationCallback {
        /**
         * Перевод получен успешно
         * @param translation
         */
        void onTranslationSuccess(Translation translation);

        /**
         * Ошибка получения перевода
         * @param errorMsg - текст ошибки
         */
        void onTranslationError(String errorMsg);
    }
    //API обратного вызова
    interface TranslationsListCallback {
        /**
         * Перевод получен успешно
         * @param translationList
         */
        void onTranslationListSuccess(List<Translation> translationList);

        /**
         * Ошибка получения перевода
         * @param errorMsg - текст ошибки
         */
        void onTranslationListError(String errorMsg);
    }
}
