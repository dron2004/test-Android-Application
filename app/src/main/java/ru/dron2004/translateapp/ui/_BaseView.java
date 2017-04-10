package ru.dron2004.translateapp.ui;

/**
 * Базовый интерфейс для создания интерфейсов которые будут реализовывать Активности и Фрагменты
 */
public interface _BaseView {
    /**
     * Отобразить ошибку во View
     * @param message Сообщение об ошибке.
     */
    void showError(String message);
}