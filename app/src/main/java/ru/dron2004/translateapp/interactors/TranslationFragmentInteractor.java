package ru.dron2004.translateapp.interactors;

import java.util.List;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.storage.dao.SettingDAO;

public interface TranslationFragmentInteractor {

    /**
     * Получить доступ к настройкам
     * @return
     */
    SettingDAO getSettingDAO();
    /**
     * Зарегистрировать Обратный вызов перевода
     * @param callbackListner
     */
    void registerTranslationCallback(TranslationInteractorCallback callbackListner);

    /**
     * Зарегистрировать Обратный вызов перевода
     * @param callbackListner
     */
    void registerTipsCallback(TipsInteractorCallback callbackListner);

    /**
     * Выполнить перевода текста
     * @param text
     */
    void getTranslation(String text);

    /**
     * Получить список подсказок
     * @param text
     */
    void getTipsForText(String text);

    /**
     * Возвращает направление перевода
     * @return
     */
    TranslateDirection getTranslateDirection();

    /**
     * Переключить флаг избранного у перевода в истории
     * @param translation
     * @return
     */
    boolean toggleFavorite(Translation translation);

    List<Language> getLanguages();

    /**
     * Поменять направление перевода toLanguage
     * @param to
     */
    void changeTranslateDirectionTo(Language to);

    /**
     * Поменять направление перевода FromLanguage
     * @param from
     */
    void changeTranslateDirectionFrom(Language from);

    //API обратного вызова
    interface TranslationInteractorCallback {
        /**
         * Обратный вызов в случае успешного выполнения
         * @param response - Запрашиваемый объект
         */
        void onTranslateSuccess(Translation response);

        /**
         * Обратный вызов в случае ошибки
         * @param errorMsg
         */
        void onError(String errorMsg);

    }
    interface TipsInteractorCallback {
        /**
         * Обратный вызов в случае успешного выполнения
         * @param response - Запрашиваемый объект
         */
        void onTipsSuccess(List<String> response);

        /**
         * Обратный вызов в случае ошибки
         * @param errorMsg
         */
        void onError(String errorMsg);
    }

}
