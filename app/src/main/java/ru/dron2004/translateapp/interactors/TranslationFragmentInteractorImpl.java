package ru.dron2004.translateapp.interactors;


import ru.dron2004.translateapp.storage.dao.LanguageDAO;
import ru.dron2004.translateapp.storage.dao.TranslationDAO;
import ru.dron2004.translateapp.ui.presenters.TranslateFragmentPresenter;

public class TranslationFragmentInteractorImpl implements TranslationFragmentInteractor {

    private Callback listner;
    private LanguageDAO languageDAO;
    private TranslationDAO translationDAO;


    public TranslationFragmentInteractorImpl(Callback callback){
        registerCallback(callback);

    }

    @Override
    public void registerCallback(Callback callbackListner) {
        this.listner = callbackListner;
    }

    @Override
    public void getTranslation(String text) {

    }

    @Override
    public void getTipsForText(String text) {

    }
}
