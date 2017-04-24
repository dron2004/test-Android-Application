package ru.dron2004.translateapp.interactors;

import java.util.List;

import ru.dron2004.translateapp.app.MainApplication;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.storage.dao.LanguageDAO;
import ru.dron2004.translateapp.storage.dao.LanguageDAOImpl;
import ru.dron2004.translateapp.storage.dao.SettingDAO;
import ru.dron2004.translateapp.storage.dao.SettingDAOImpl;
import ru.dron2004.translateapp.storage.dao.TipsDAO;
import ru.dron2004.translateapp.storage.dao.TranslationDAO;
import ru.dron2004.translateapp.storage.dao.TranslationDAOImpl;

public class FavoriteFragmentInteactorImpl implements FavoriteFragmentInteactor, TranslationDAO.TranslationCallback, TranslationDAO.TranslationsListCallback {
    private HistoryCallback historyListner;

    //Ссылки на Хранилища
    private LanguageDAO languageDAO;
    private TranslationDAO translationDAO;
    private TipsDAO tipsDAO;
    private SettingDAO settingDAO;

    public FavoriteFragmentInteactorImpl() {
        translationDAO = new TranslationDAOImpl(this,this);
        languageDAO = new LanguageDAOImpl();
        settingDAO = new SettingDAOImpl(MainApplication.getAppContext());

    }

    @Override
    public void registerHistoryCallback(HistoryCallback callback) {
        historyListner = callback;
    }

    @Override
    public void removeTranslation(Translation translation) {
        translationDAO.removeTranslation(translation);
    }

    @Override
    public boolean toggleHistoryFavorite(Translation translation) {
        return translationDAO.toggleFavoriteTranslation(translation).isFavorite();
    }

    @Override
    public void getTranslationHistory() {
        translationDAO.getAllTranslation(false);
    }



    //Возврат переводов из БД
    @Override
    public void onTranslationSuccess(Translation translation) {

    }

    @Override
    public void onTranslationError(String errorMsg) {

    }

    @Override
    public void onTranslationListSuccess(List<Translation> translationList) {
//        Log.d("happy","Вернулись с историей в интерактор");
        //TODO по уму - надо заполнять названия языков
        historyListner.onHistorySuccess(translationList);
    }

    @Override
    public void onTranslationListError(String errorMsg) {

    }
}
