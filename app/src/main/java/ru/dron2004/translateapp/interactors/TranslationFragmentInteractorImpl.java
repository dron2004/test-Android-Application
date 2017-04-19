package ru.dron2004.translateapp.interactors;


import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
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
import ru.dron2004.translateapp.storage.dao.TipsDAOImpl;
import ru.dron2004.translateapp.storage.dao.TranslationDAO;
import ru.dron2004.translateapp.storage.dao.TranslationDAOImpl;
import ru.dron2004.translateapp.utility.LocaleUtils;

public class TranslationFragmentInteractorImpl
        implements TranslationFragmentInteractor,
        TranslationDAO.TranslationCallback,
        TipsDAO.TipsCallBack,
        YandexPredictorCallback,
        YandexTranslateCallback {

    private static final int MIN_LENGTH_FOR_API_TIPS = 2;
    private static final int TIPS_COUNT = 5;
    private static final long DELAY_BEFORE_TIPS_MILLIS = 1000;
    private TranslationInteractorCallback translationCallback;
    private TipsInteractorCallback tipsCallBack;

    //Ссылки на Хранилища
    private LanguageDAO languageDAO;
    private TranslationDAO translationDAO;
    private TipsDAO tipsDAO;
    private SettingDAO settingDAO;

    private Thread tipsThread;

    private TranslateDirection translateDirection;
    private List<Language> languagesList;
    private Translation currentTranslation;


    public TranslationFragmentInteractorImpl(
            TranslationInteractorCallback translationCallback,
            TipsInteractorCallback tipsCallBack)
    {
        registerTranslationCallback(translationCallback);
        registerTipsCallback(tipsCallBack);

        translationDAO = new TranslationDAOImpl(this,null);
        tipsDAO = new TipsDAOImpl(this);
        languageDAO = new LanguageDAOImpl();
        settingDAO = new SettingDAOImpl();

//        translateHelper = new YandexTranslateHelper(this, LocaleUtils.getLocale());
//        predictorHelper = new YandexPredictorHelper();

        //Иницализируем направление перевода из настроек
        translateDirection = new TranslateDirection(
                languageDAO.getLanguageByIdent(settingDAO.getFromLanguageIdent()),
                languageDAO.getLanguageByIdent(settingDAO.getToLanguageIdent())
        );

    }



    @Override
    public void registerTranslationCallback(TranslationInteractorCallback callbackListner) {
        translationCallback = callbackListner;
    }

    @Override
    public void registerTipsCallback(TipsInteractorCallback callbackListner) {
        tipsCallBack = callbackListner;
    }





    @Override
    public TranslateDirection getTranslateDirection() {
        return translateDirection;
    }

    @Override
    public boolean toggleFavorite(Translation translation) {
        return translationDAO.toggleFavoriteTranslation(translation).isFavorite();
    }

    @Override
    public List<Language> getLanguages() {
        if (languagesList == null) {
            languagesList = languageDAO.getLanguages();
        }
        return languagesList;
    }

    @Override
    public void changeTranslateDirectionTo(Language to) {
        translateDirection.to = to;
        settingDAO.setToLanguage(to);
    }

    @Override
    public void changeTranslateDirectionFrom(Language from) {
        translateDirection.from = from;
        settingDAO.setFromLanguage(from);
    }

    //ПОЛУЧЕНИЕ ПЕРЕВОДА
    @Override
    public void getTranslation(String text) {
        Translation translation = translationDAO.getTranslation(text,translateDirection);
        if (translation == null) {
            YandexTranslateHelper translateHelper = new YandexTranslateHelper(this, LocaleUtils.getLocale());
            translateHelper.getTranslation(text,translateDirection);
        } else {
            //Перевод нашелся в БД
            translationCallback.onTranslateSuccess(translation);
        }

    }

    @Override
    public void onTranslationSuccess(Translation response) {
        translationCallback.onTranslateSuccess(response);
    }

    @Override
    public void onTranslationError(String errorMsg) {
        translationCallback.onError(errorMsg);
    }


    //ПОДСКАЗКИ
    // проверить в БД
    // если таких нет - проверить в API
    // и сохранить в БД для кеша

    private long timeLastTextTyped;
    @Override
    public void getTipsForText(final String text) {
        //TODO Сделать задержку перед запросом (MB Thread.sleep)
            //Так как в базе подсказок храняться только слова
            // - отправлять запрос с базу только не содержащий пробелов
            // - остальное отправлять в API Predictor

            //При следующем вводе прерываем подбор значений
            if (tipsThread != null && tipsThread.isAlive()) tipsThread.interrupt();

            tipsThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Language from = languageDAO.getLanguageByIdent(settingDAO.getFromLanguageIdent());
                    try {
                        //Ждем паузу - вдруг продолжит печатать
                        Thread.sleep(DELAY_BEFORE_TIPS_MILLIS);
                        if (!text.contains(" ")) {
                            tipsDAO.getTips(text, from,TIPS_COUNT);
                        } else {
                            onTipsEmpty(text,from);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            tipsThread.start();

//            timeLastTextTyped = System.currentTimeMillis();
    }

    @Override
    public void onTipsSuccess(List<String> stringList) {
        //Подсказки пришли из БД - отправляем в презентер
        tipsCallBack.onTipsSuccess(stringList);
    }
    @Override
    public void onTipsError(String errorMsg) {
        tipsCallBack.onError(errorMsg);
    }

    @Override
    public void onTipsEmpty(String text, Language language) {
        //Подсказок не найдено
        // запросить в API Predictor
        // если текст длиной более MIN_LENGTH
        if (text.length() > MIN_LENGTH_FOR_API_TIPS) {
            YandexPredictorHelper predictorHelper = new YandexPredictorHelper(this);
            predictorHelper.getTipForText(text, language,TIPS_COUNT);
        } else {
           tipsCallBack.onTipsSuccess(new ArrayList<String>());
        }
    }


    @Override
    public void onCompleteSuccess(List<String> tips,Language language) {
        //Tipsы привалили
        //Надо сохранить в БД - для подсказок начала фраз
        tipsDAO.updateTips(tips,language);
        tipsCallBack.onTipsSuccess(tips);
    }

    @Override
    public void onPredictorSupportLanguagesSuccess(List<Language> supportLanguagesIdents) {}

    @Override
    public void onPredictorAPIError(String errorMsg) {
        tipsCallBack.onError(errorMsg);
    }


    //Возврат из переводчика
    @Override
    public void onTranslateSuccess(Translation translation) {
        //Надо сохранить перевод в БД - попутно запишем в него ID
        translationDAO.saveTranslation(translation);
        //Закешируем
        currentTranslation = translation;
        //Передадим в презентер
        translationCallback.onTranslateSuccess(translation);
    }

    @Override
    public void onTranslateSupportLanguage(List<Language> supportLanguage) {/*Не используем тут*/}

    @Override
    public void onTranslateAPIError(String errorMsg) {
        translationCallback.onError(errorMsg);
    }
}
