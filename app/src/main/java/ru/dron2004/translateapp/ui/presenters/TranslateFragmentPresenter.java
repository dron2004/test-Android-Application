package ru.dron2004.translateapp.ui.presenters;

import java.util.List;

import ru.dron2004.translateapp.interactors.TranslationFragmentInteractor;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui._BasePresenter;
import ru.dron2004.translateapp.ui.views.TranslateFragmentView;

public interface TranslateFragmentPresenter
        extends _BasePresenter<TranslateFragmentView>,
                TranslationFragmentInteractor.TipsInteractorCallback,
                TranslationFragmentInteractor.TranslationInteractorCallback
{

    /**
     * Изменение текста в поле ввода
     * @param text
     */
    void onTextToTranslateTyped(String text);
    /**
     * Выполнить перевод текста
     * @param text
     */
    void translateText(String text);

    /**
     * Изменяет состояние текущего перевода в избранном
     * @return
     */
    boolean toggleFavorite();

    /**
     * Изменить язык перевода с какого
     * @param selectedItem
     */
    void selectedFrom(Language selectedItem);
    /**
     * Изменить язык перевода на какой
     * @param selectedItem
     */
    void selectedTo(Language selectedItem);

    @Override
    void setView(TranslateFragmentView view);

    @Override
    void unsetView();

    @Override
    void onStart();

    @Override
    void onPause();


    //Обратные вызовы

    @Override
    void onTranslateSuccess(Translation response);

    @Override
    void onTipsSuccess(List<String> response);

    @Override
    void onError(String errorMsg);


    /**
     * Поменять направление перевода
     * @return
     */
    TranslateDirection exchangeTranslateDirection();
}
