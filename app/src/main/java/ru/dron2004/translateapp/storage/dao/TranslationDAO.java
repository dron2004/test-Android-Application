package ru.dron2004.translateapp.storage.dao;

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
}
