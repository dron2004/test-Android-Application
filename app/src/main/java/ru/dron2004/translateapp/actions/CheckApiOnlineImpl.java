package ru.dron2004.translateapp.actions;

import java.util.List;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.storage.api.YandexPredictorCallback;
import ru.dron2004.translateapp.storage.api.YandexPredictorHelper;
import ru.dron2004.translateapp.storage.api.YandexTranslateCallback;
import ru.dron2004.translateapp.storage.api.YandexTranslateHelper;
import ru.dron2004.translateapp.utility.LocaleUtils;
import ru.dron2004.translateapp.utility.NetworkUtils;

public class CheckApiOnlineImpl implements CheckApiOnline, YandexPredictorCallback, YandexTranslateCallback {
    private CheckApiOnline.Callback listner;

    private YandexPredictorHelper predictorHelper;
    private YandexTranslateHelper translateHelper;

    public CheckApiOnlineImpl(CheckApiOnline.Callback callback){
        listner = callback;
        predictorHelper = new YandexPredictorHelper(this);
        translateHelper = new YandexTranslateHelper(this, LocaleUtils.getLocale());
    }

    @Override
    public boolean checkInternetPermission() {
        //Всегла разрешено - не опастное
        //Выше API23 не запрашивается в рантайме
        //Ниже API23 запрашивается при установке
        return true;
    }

    @Override
    public boolean checkInternetConnection() {
        return NetworkUtils.checkIsOnline();
    }

    @Override
    public void checkAPIKeys() {
        predictorHelper.getSupportLanguages();
    }

    @Override
    public void onCompleteSuccess(List<String> tips,Language language) {/*не используем для пинга*/}

    @Override
    public void onPredictorSupportLanguagesSuccess(List<Language> supportLanguagesIdents) {
        //Если языки получены то API предиктора работает
        //Если предиктор работает - проверим переводчик
        translateHelper.getLanguageTitles(LocaleUtils.getLocale());
    }

    @Override
    public void onPredictorAPIError(String errorMsg) {errorCheck(errorMsg);}



    @Override
    public void onTranslateSuccess(Translation translation) {/*не используем для пинга*/}

    @Override
    public void onTranslateSupportLanguage(List<Language> supportLanguage) {
        //Если языки получены то и переводчик работает - можем возвращаться
        successCheck();
    }

    @Override
    public void onTranslateAPIError(String errorMsg) {listner.onAPIKeyIsError(errorMsg);}


    private void successCheck(){
        listner.onAPIKeyIsValid();
    }

    private void errorCheck(String errorMsg) {
        listner.onAPIKeyIsError(errorMsg);
    }
}
