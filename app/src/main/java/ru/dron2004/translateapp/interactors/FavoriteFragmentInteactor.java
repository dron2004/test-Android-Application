package ru.dron2004.translateapp.interactors;

import java.util.List;

import ru.dron2004.translateapp.model.Translation;

public interface FavoriteFragmentInteactor {
    /**
     * Получить список истории
     */
    void getTranslationHistory();
    void registerHistoryCallback(HistoryCallback callback);

    /**
     * Удалить перевод
     * @param translation
     */
    void removeTranslation(Translation translation);

    boolean toggleHistoryFavorite(Translation translation);

    /**
     * Обратный вызов списка истории
     */
    public interface HistoryCallback{
        void onHistorySuccess(List<Translation> translationList);
        void onHistoryEmpty();
        void onHistoryError(String errorMsg);
    }
}
