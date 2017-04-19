package ru.dron2004.translateapp.storage.dao;

import java.util.List;

import ru.dron2004.translateapp.interactors._BaseCallback;
import ru.dron2004.translateapp.model.Language;

public interface LanguageDAO {

    /**
     * Актуализировать список языков
     * @param languageList
     */
    void updateLanguages(List<Language> languageList);

    /**
     * Получить список языков
     */
    List<Language> getLanguages();

    /**
     * Получить конкретный язык по идентификатору
     * @param ident - индентификатор языка
     */
    Language getLanguageByIdent(java.lang.String ident);

    interface LanguageCallback extends _BaseCallback<Language>{}
    interface LanguageListCallback extends _BaseCallback<List<Language>>{}
    interface UpdateLanguagesCallback extends _BaseCallback<Boolean>{}

    //TODO Убедиться что новое подходит и Убить старое
//    //API обратного вызова
//    interface LanguageCallback {
//        //Список языков
//        /**
//         * Возврат списка языков
//         * @param langs
//         */
//        void onGetLangsSuccess(List<Language> langs);
//
//        /**
//         * Ошибка получения списка языков
//         * @param errorMsg - текст ошибки
//         */
//        void onGetLangsError(Language errorMsg);
//
//        //Конкретный язык
//        /**
//         * Возврат языка
//         * @param language
//         */
//        void onGetLangSuccess(Language language);
//
//        /**
//         * Ошибка получения языка
//         * @param errorMsg - текст ошибки
//         */
//        void onGetLangError(Language errorMsg);
//
//
//        //Операции БД
//        /**
//         * Успешная синхронизация языков
//         */
//        void onUpdateSuccess();
//
//        /**
//         * Ошибка синхронизации языков
//         * @param errorMsg - текст ошибки
//         */
//        void onUpdateError(Language errorMsg);
//    }
}
