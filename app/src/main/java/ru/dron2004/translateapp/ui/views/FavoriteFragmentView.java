package ru.dron2004.translateapp.ui.views;

import android.app.Activity;

import java.util.List;

import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui._BaseView;

public interface FavoriteFragmentView extends _BaseView {
    /**
     * Установить список истории
     * @param translationList
     */
    void setHistory(List<Translation> translationList);

    /**
     * Открыть фрагмент переводчика
     * @param translation
     */
    void showTranslateFragment(Translation translation);

    /**
     * Показать пустой список
     */
    void setEmptyHistory();

    /**
     * Вернуть ссылку на активность
     * @return
     */
    Activity getActivity();
}
