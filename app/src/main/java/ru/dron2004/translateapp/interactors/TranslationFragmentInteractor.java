package ru.dron2004.translateapp.interactors;

import java.util.List;

import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui.presenters.TranslateFragmentPresenter;

public interface TranslationFragmentInteractor {

    /**
     * Зарегистрировать Обратный вызов
     * @param callbackListner
     */
    void registerCallback(Callback callbackListner);

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
    interface Callback {
        /**
         * Перевод выполнен
         * @param translation
         */
        void onTranslation(Translation translation);

        /**
         * Подсказки получены
         * @param tips
         */
        void onTipsCreated(List<String> tips);
    }
}
