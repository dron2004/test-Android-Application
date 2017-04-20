package ru.dron2004.translateapp.ui.views;


import ru.dron2004.translateapp.ui._BaseView;

public interface MainActivityView extends _BaseView {

    /**
     * Показать фрагмент переводчика
     */
    void showTranslateFragment();

    /**
     * Показать фрагмент Избраного
     */
    void showFavoritesFragment();

    /**
     * Показать фрагмент Настроек
     */
    void showAboutFragment();

    void makeSelectionNavigator(int navigationID);

}
