package ru.dron2004.translateapp.interactors;

import ru.dron2004.translateapp.model.PackageModel;

public class MainActivityInteractorImpl implements MainActivityInteractor {
    private Callback listner;
    private PackageModel app;

    public MainActivityInteractorImpl(PackageModel packageModel){
        this.app = packageModel;
    }

    @Override
    public void registerCallback(Callback callbackListner) {
        this.listner = callbackListner;
    }

    @Override
    public void checkAppConstantDB() {

    }
}
