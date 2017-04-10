package ru.dron2004.translateapp.storage.dao;

import java.util.List;

import ru.dron2004.translateapp.model.Language;

public interface LangTitlesDAO {

    /**
     * Актуализировать список языков
     * @param languageList
     */
    void updateTitles(List<Language> languageList);
}
