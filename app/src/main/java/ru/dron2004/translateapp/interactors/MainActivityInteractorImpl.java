package ru.dron2004.translateapp.interactors;

import android.util.Log;

import java.util.List;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.actions.CheckApiOnline;
import ru.dron2004.translateapp.actions.CheckApiOnlineImpl;
import ru.dron2004.translateapp.actions.SyncDBonStart;
import ru.dron2004.translateapp.actions.SyncDBonStartImpl;
import ru.dron2004.translateapp.app.MainApplication;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.storage.dao.LanguageDAO;
import ru.dron2004.translateapp.storage.dao.LanguageDAOImpl;
import ru.dron2004.translateapp.storage.dao.SettingDAO;
import ru.dron2004.translateapp.storage.dao.SettingDAOImpl;
import ru.dron2004.translateapp.utility.LocaleUtils;

public class MainActivityInteractorImpl implements MainActivityInteractor, SyncDBonStart.Callback, CheckApiOnline.Callback {
    private Callback listner;

    //Ссылки на Хранилища
    private LanguageDAO languageDAO;
    private SettingDAO settingDAO;

    private List<Language> appSupportLanguages;
    private final long checkLanguageTablePeriod = (long) 30*24*60*60; //30дней по 24 часа по 60 минут по 60 секунд
    private final long checkAPIKeysPeriod = (long) 12*60*60; //12 часов по 60 минут по 60 секунд

    public MainActivityInteractorImpl(Callback callback){
        registerCallback(callback);

        //Инициализируем БД и АПИ
        languageDAO = new LanguageDAOImpl();
        settingDAO = new SettingDAOImpl(MainApplication.getAppContext());

    }

    @Override
    public void registerCallback(Callback callbackListner) {
        this.listner = callbackListner;
    }

    @Override
    public void checkAPIServices() {
        CheckApiOnline checkApiOnline = new CheckApiOnlineImpl(this);
        if (checkApiOnline.checkInternetPermission()) {
            if (checkApiOnline.checkInternetConnection()) {
                if ((settingDAO.getLastDateSyncLanguages(LocaleUtils.getLocale()) + checkLanguageTablePeriod) < (System.currentTimeMillis() / 1000L)) {
                    //Если дата синхронизации языков больше чем последняя проверка + 30 дней
                    //Ушли в операцию синхронизации
                    new SyncDBonStartImpl(this).syncDBAction(LocaleUtils.getLocale());
                    //Вернемся в каллбек
                } else {
                    //Если синхронизация языков не требуется
                    //Ушли в операцию пропинговки API
                    if ((settingDAO.getLastDateCheckAPI() + checkAPIKeysPeriod) < (System.currentTimeMillis() / 1000L)) {
                        //Пошли пинговать ключи
                        new CheckApiOnlineImpl(this).checkAPIKeys();
                    } else {
                        //Если ключи проверяли в указанный период - возвращаемся успешно
                        listnerSuccess();
                    }
                }
            } else {
                //Нет коннекшена в интернет
                listner.checkAPIError(LocaleUtils.getLocaleStringResource(R.string.no_internet_connection));
            }
        } else {
            //Нет пермишена на интернет
            listner.checkAPIError(LocaleUtils.getLocaleStringResource(R.string.no_internet_permission));
        }
    }


    @Override
    public void onSuccessSync(List<Language> appLanguageList) {
        //Тут после синхронизации языков
        appSupportLanguages = appLanguageList;
        //Устанавливаем дату последней синхронизации
        settingDAO.setLastDateSyncLanguagesNow(LocaleUtils.getLocale());
        //Устанавливаем время последней пропинговки
        settingDAO.setLastDateCheckAPI();
        //Уже достаточно попользовались ключами - значит работают - возвращаемся
        listnerSuccess();
    }

    @Override
    public void onErrorSync(String errorMsg) {
        //Ошибка синхронизации БД языков
        listner.checkAPIError(errorMsg);
    }

    @Override
    public void onAPIKeyIsValid() {
        //Успешная пропинговка ключей апи
        //Устанавливаем время последней пропинговки
        settingDAO.setLastDateCheckAPI();
        listnerSuccess();
    }

    @Override
    public void onAPIKeyIsError(String errorMsg) {
        //Ошибка из пропинговки ключей
        listner.checkAPIError(errorMsg);
    }

    private void listnerSuccess(){
        Log.d("happy", "listnerSuccess: " + listner);
        listner.checkAPISuccess();
    }
}
