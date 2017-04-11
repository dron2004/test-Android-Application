package ru.dron2004.translateapp.interactors;

import java.util.List;

public interface _BaseCallback<R> {
    /**
     * Обратный вызов в случае успешного выполнения
     * @param response - Запрашиваемый объект
     */
    void onSuccess(R response);

    /**
     * Обратный вызов в случае ошибки
     * @param errorMsg
     */
    void onError(String errorMsg);
}
