package ru.dron2004.translateapp.ui.presenters;

import java.lang.ref.WeakReference;

import ru.dron2004.translateapp.interactors.MainActivityInteractor;
import ru.dron2004.translateapp.ui.views.MainActivityView;

public class MainActivityPresenterImpl implements MainActivityPresenter {
    private MainActivityInteractor interactor;
    private WeakReference<MainActivityView> view;

    public MainActivityPresenterImpl(MainActivityInteractor i){
        interactor = i;
        interactor.registerCallback(this);
    }

    @Override
    public void setView(MainActivityView view) {
        this.view = new WeakReference<MainActivityView>(view);
    }

    @Override
    public void unsetView() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }

    @Override
    public void onStart() {
        MainActivityView v = view.get();
    }

    @Override
    public void onPause() {
        if(view != null) {
            view.clear();
            view = null;
        }
    }

    @Override
    public void firstStart() {
        MainActivityView v = view.get();
        interactor.checkAPIServices();
    }

    @Override
    public void showTranslateFragment() {
        MainActivityView v = view.get();
        if (v != null) {
            v.showTranslateFragment();
        }
    }

    @Override
    public void showFavoritesFragment() {
        MainActivityView v = view.get();
        if (v != null) {
            v.showFavoritesFragment();
        }
    }

    @Override
    public void showAboutFragment() {
        MainActivityView v = view.get();
        if (v != null) {
            v.showAboutFragment();
        }
    }

    @Override
    public void checkAPISuccess() {
        //При успешной проверке и сихронизации БД Отображаем первый фрагмент (Переводчик)
        MainActivityView v = view.get();
        if (v != null) {
            v.showTranslateFragment();
        }

    }

    @Override
    public void checkAPIError(String errorMsg) {
        MainActivityView v = view.get();
        if (v != null) {
            v.showError(errorMsg);
        }
    }
}
