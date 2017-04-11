package ru.dron2004.translateapp.storage.dao;

import java.util.List;

import ru.dron2004.translateapp.interactors._BaseCallback;

public interface TipsDAO {
    /**
     * Получить список подсказок для текста
     * @param text
     */
    void getTips(String text);

    interface TipsCallBack extends _BaseCallback<List<String>>{}

}
