package ru.dron2004.translateapp.ui.views;

import android.app.Activity;

import java.util.List;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.ui._BaseView;

public interface TranslateFragmentView extends _BaseView {

    //Вспомогательные методы
    /**
     * Получить текст для перевода
     * @return
     */
    String getTextToTranslate();

    /**
     * Установить текст для перевода
     */
    void setTextToTranslate(String text);

    /**
     * Установить переведенный текст
     * @param text
     */
    void setTranslatedText(String text);

    /**
     * Получить текст из блока текста для перевода
     */
    String getTranslatedText();

    //В момент инициализации фрагмента
    /**
     * Очистить поле ввода
     */
    void clearTextToTranslate();

    //В процессе ввода текста для перевода
    /**
     * Отобразить прогресс загрузки подсказок
     */
    void showTipsLoadingProgress();

    /**
     * Скрыть прогресс загрузки подсказок
     */
    void hideTipsLoadingProgress();

    /**
     * Установить и отобразить подсказки
     * @param tipsList
     */
    void showTipsList(List<String> tipsList);

    /**
     * Скрыть подсказки
     */
    void hideTipsList();

    /**
     * Отобразить направление перевода
     * @param direction
     */
    void setTranslateDirection(TranslateDirection direction);


    //Кнопка добавления в избранное
    /**
     * Отобразить кнопку добавления в Избранное
     */
    void showAddToFavoritesBtn(boolean favorite);

    /**
     * Скрыть кнопку добавления в Избранное
     */
    void hideAddToFavoritesBtn();

    //Кнопка перевести
    /**
     * Отобразить кнопку добавления в Избранное
     */
    void showTranslateBtn();

    /**
     * Скрыть кнопку добавления в Избранное
     */
    void hideTranslateBtn();


    void setLanguagesList(List<Language> languagesList);

    /**
     * Get Fragment activity
     */
    Activity getActivity();
}
