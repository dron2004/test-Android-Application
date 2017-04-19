package ru.dron2004.translateapp.ui.presenters;

import java.util.List;

import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui._BasePresenter;
import ru.dron2004.translateapp.ui.views.FavoriteFragmentView;

public interface FavoriteFragmentPresenter extends _BasePresenter<FavoriteFragmentView> {

    /**
     * Клик на элементе истории
     * @param selectedTranslation
     */
    void clickOnHistoryItem(Translation selectedTranslation);

    /**
     * Изменить избранность элемента истории
     * @param translation
     */
    boolean toggleHistoryFavorite(Translation translation);


    List<Translation> getTranslationHistory();

    /**
     * При свайпе влево
     * @param translation
     */
    void historyItemSwipeLeft(Translation translation);

    /**
     * При свайпе вправо
     * @param translation
     */
    void historyItemSwipeRight(Translation translation);
}
