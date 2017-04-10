package ru.dron2004.translateapp.interactors;


import ru.dron2004.translateapp.ui.presenters.TranslateFragmentPresenter;

public class TranslationFragmentInteractorImpl implements TranslationFragmentInteractor {

    private Callback listner;

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
