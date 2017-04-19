package ru.dron2004.translateapp.interactors;

public interface MainActivityInteractor {

    /**
     * Зарегистрировать Обратный вызов
     * @param callbackListner
     */
    void registerCallback(Callback callbackListner);

    /**
     * Проверка доступности интернета и работоспособности ключей API
     */
    void checkAPIServices();

    // API обратного вызова
    interface Callback {
        void checkAPISuccess();
        void checkAPIError(String errorMsg);
    }

}
