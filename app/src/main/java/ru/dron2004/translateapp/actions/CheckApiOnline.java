package ru.dron2004.translateapp.actions;

public interface CheckApiOnline {
    /**
     * Проверка разрешений интернета
     */
    public boolean checkInternetPermission();

    /**
     * Проверка доступности сети
     * @return
     */
    public boolean checkInternetConnection();

    /**
     * Проверка работоспособнеости ключей API
     */
    public void checkAPIKeys();

    public interface Callback {
        /**
         * Обратный вызов при успешной проверке ключей
         */
        public void onAPIKeyIsValid();

        /**
         * Обратный вызов при ошибке при проверке ключей
         */
        public void onAPIKeyIsError(String errorMsg);
    }
}
