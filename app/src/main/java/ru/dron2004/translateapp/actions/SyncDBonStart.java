package ru.dron2004.translateapp.actions;

import java.util.List;

import ru.dron2004.translateapp.model.Language;

public interface SyncDBonStart {
    public void syncDBAction(String locale);
    public interface Callback {
        /**
         * Успешная синхронизация БД
         * @param appLanguageList
         */
        void onSuccessSync(List<Language> appLanguageList);

        /**
         * Ошибка при синхронизации БД
         * @param errorMsg
         */
        void onErrorSync(java.lang.String errorMsg);
    }
}
