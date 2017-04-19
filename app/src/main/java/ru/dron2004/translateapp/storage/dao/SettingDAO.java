package ru.dron2004.translateapp.storage.dao;

import ru.dron2004.translateapp.model.Language;

public interface SettingDAO {
    /**
     * Получить сохраненный язык с которого будет осуществляться перевод
     * @return
     */
    public String getFromLanguageIdent();

    /**
     * Получить сохраненный язык с которого будет осуществляться перевод
     * @return
     */
    public void setFromLanguage(Language language);

    /**
     * Получить сохраненный язык на который будет осуществляться перевод
     * @return
     */
    public String getToLanguageIdent();

    /**
     * Получить сохраненный язык на который будет осуществляться перевод
     * @return
     */
    public void setToLanguage(Language language);
    /**
     * Получить дату последней синхронизации БД констант
     * @param locale
     * @return
     */
    public long getLastDateSyncLanguages(String locale);

    /**
     * Установить дату последней проверки списка языков на сейчас
     * @param locale
     */
    public void setLastDateSyncLanguagesNow(String locale);

    /**
     * Установить на сейчас время последней проверки ключей API
     */
    public void setLastDateCheckAPI();

    /**
     * Получить время последней проверки ключей API
     * @return
     */
    public long getLastDateCheckAPI();

}
