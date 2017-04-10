package ru.dron2004.translateapp.interactors;

public interface MainActivityInteractor {

    /**
     * Зарегистрировать Обратный вызов
     * @param callbackListner
     */
    void registerCallback(Callback callbackListner);

    /**
     * Проверка актуальности базы поддерживаемых языков
     */
    void checkAppConstantDB();


    // API обратного вызова
    interface Callback {

    }

}
