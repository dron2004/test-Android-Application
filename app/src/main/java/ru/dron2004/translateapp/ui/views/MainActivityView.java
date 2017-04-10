package ru.dron2004.translateapp.ui.views;


import ru.dron2004.translateapp.ui._BaseView;

public interface MainActivityView extends _BaseView {

    /**
     * Запросить доступ в Интернет
     */
    void requestInternetPermission(PermissionCallback listner);

    /**
     * Показать фрагмент переводчика
     */
    void showTranslateFragment();

    /**
     * Показать фрагмент Избраного
     */
    void showFavoritesFragment();

    /**
     * Показать фрагмент Настроек
     */
    void showSettingFragment();

    interface PermissionCallback {
        /**
         * Вызывается в момент получения разрешения/запрещения использования интернета
         * @param isGranted
         */
        void onRequestInternetPermission(boolean isGranted);
    }
}
