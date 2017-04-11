package ru.dron2004.translateapp.interactors;

import java.util.List;

import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui.presenters.TranslateFragmentPresenter;

public interface TranslationFragmentInteractor {

    /**
     * Зарегистрировать Обратный вызов перевода
     * @param callbackListner
     */
    void registerTranslationCallback(TranslationInteractorCallback callbackListner);

    /**
     * Зарегистрировать Обратный вызов перевода
     * @param callbackListner
     */
    void registerTipsCallback(TipsInteractorCallback callbackListner);

    /**
     * Выполнить перевода текста
     * @param text
     */
    void getTranslation(String text);

    /**
     * Получить список подсказок
     * @param text
     */
    void getTipsForText(String text);

    //API обратного вызова
    interface TranslationInteractorCallback extends _BaseCallback<Translation>{}
    interface TipsInteractorCallback extends _BaseCallback<List<String>>{}
    //TODO Убедиться что новое подходит и Убить старое
//    interface Callback {
//        /**
//         * Перевод выполнен
//         * @param translation
//         */
//        void onTranslation(Translation translation);
//
//        /**
//         * Подсказки получены
//         * @param tips
//         */
//        void onTipsCreated(List<String> tips);
//    }
}
