package ru.dron2004.translateapp.ui;

import android.os.Bundle;

public interface _BasePresenter<V extends _BaseView> {
    /**
     * Добавляем interface View
     * Сохраняем WeakReference
     */
    void setView(V view);

    /**
     * Зануляем interface View
     */
    void unsetView();


    /**
     * Фрагмент запущен
     * Тут можно проинициализировать контролы View
     */
    void onStart();

    /**
     * Обработать события при приостановке фрагмента
     */
    void onPause();

}