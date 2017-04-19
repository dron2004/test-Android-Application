package ru.dron2004.translateapp.actions;

import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.storage.api.YandexPredictorCallback;
import ru.dron2004.translateapp.storage.api.YandexPredictorHelper;
import ru.dron2004.translateapp.storage.api.YandexTranslateCallback;
import ru.dron2004.translateapp.storage.api.YandexTranslateHelper;
import ru.dron2004.translateapp.storage.dao.LanguageDAO;
import ru.dron2004.translateapp.storage.dao.LanguageDAOImpl;
import ru.dron2004.translateapp.storage.dao.SettingDAO;
import ru.dron2004.translateapp.storage.dao.SettingDAOImpl;
import ru.dron2004.translateapp.storage.dao.TipsDAO;
import ru.dron2004.translateapp.storage.dao.TranslationDAO;
import ru.dron2004.translateapp.utility.LocaleUtils;

public class SyncDBonStartImpl implements SyncDBonStart,YandexPredictorCallback, YandexTranslateCallback,LanguageDAO.UpdateLanguagesCallback {
    private SyncDBonStart.Callback listner;
    //Ссылки на Хранилища
    private LanguageDAO languageDAO;
    private TranslationDAO translationDAO;
    private TipsDAO tipsDAO;
    private SettingDAO settingDAO;

    //Ссылки на API
    private YandexTranslateHelper translateHelper;
    private YandexPredictorHelper predictorHelper;

    private String appLocale;
    private List<Language> predictorLanguages;
    private List<Language> translateLanguages;
    private List<Language> appSupportLanguages;

    public SyncDBonStartImpl(SyncDBonStart.Callback callback){
        listner = callback;

        languageDAO = new LanguageDAOImpl(this);
        settingDAO = new SettingDAOImpl();

        translateHelper = new YandexTranslateHelper(this, LocaleUtils.getLocale());
        predictorHelper = new YandexPredictorHelper(this);
    }

    @Override
    public void syncDBAction(String locale){
        appLocale = locale;
        startSync();
    }

    //Шаг 1 - получаем список языков из предиктора
    private void startSync(){
        predictorHelper.getSupportLanguages();
    }


    //Шаг 2 - получаем список языков из Translate
    @Override
    public void onPredictorSupportLanguagesSuccess(List<Language> supportLanguages) {
        predictorLanguages = supportLanguages;
        translateHelper.getLanguageTitles(appLocale);
    }

    @Override
    public void onTranslateSupportLanguage(List<Language> supportLanguage) {
        translateLanguages = supportLanguage;

        //Шаг 3 - вычисляем пересечение языков
        appSupportLanguages = intersectSupportLanguages();

        //Шаг 4 - обновляем список языков в БД
        updateLanguagesDB();
    }

    private List<Language> intersectSupportLanguages(){
        List<Language> res = new ArrayList<>();
        //Перебираем языки предиктора
        for (Language psl: predictorLanguages) {

            if (translateLanguages.contains(psl)){
                int translatorLanguageIndex =  translateLanguages.indexOf(psl);
                Language l = translateLanguages.get(translatorLanguageIndex);
                res.add(l);
            }
        }
        return res;
    }

    private void updateLanguagesDB(){
        languageDAO.updateLanguages(appSupportLanguages);
    }


    @Override
    public void onTranslateAPIError(String errorMsg) {listner.onErrorSync(errorMsg);}
    @Override
    public void onPredictorAPIError(String errorMsg) {listner.onErrorSync(errorMsg);}

    @Override
    public void onCompleteSuccess(List<String> tips,Language language) {}
    @Override
    public void onTranslateSuccess(Translation translation) {}

    @Override
    public void onSuccess(Boolean response) {
        listner.onSuccessSync(appSupportLanguages);
    }

    @Override
    public void onError(String errorMsg) {
        listner.onErrorSync(errorMsg);
    }
}
