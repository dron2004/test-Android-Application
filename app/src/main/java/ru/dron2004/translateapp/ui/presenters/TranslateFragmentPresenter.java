package ru.dron2004.translateapp.ui.presenters;

import ru.dron2004.translateapp.ui._BasePresenter;
import ru.dron2004.translateapp.ui.views.TranslateFragmentView;

public interface TranslateFragmentPresenter extends _BasePresenter<TranslateFragmentView> {

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

}
