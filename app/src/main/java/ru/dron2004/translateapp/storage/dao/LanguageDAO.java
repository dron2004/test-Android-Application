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

}
