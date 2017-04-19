package ru.dron2004.translateapp.ui.presenters;

import ru.dron2004.translateapp.interactors.MainActivityInteractor;
import ru.dron2004.translateapp.ui._BasePresenter;
import ru.dron2004.translateapp.ui.views.MainActivityView;

public interface MainActivityPresenter extends _BasePresenter<MainActivityView>,MainActivityInteractor.Callback {

    /**
     * Запуск активность (не пересоздание)
     */
    void firstStart();
    /**
     * Подготовить и отдать команду на отображение Фрагмента переводчика
     */
    void showTranslateFragment();
    /**
     * Подготовить и отдать команду на отображение Фрагмента избранного
     */
    void showFavoritesFragment();
    /**
     * Подготовить и отдать команду на отображение Фрагмента настроек
     */
    void showAboutFragment();

}
