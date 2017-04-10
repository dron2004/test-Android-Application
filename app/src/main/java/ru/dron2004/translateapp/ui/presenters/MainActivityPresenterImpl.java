package ru.dron2004.translateapp.ui.presenters;

import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.interactors.MainActivityInteractor;
import ru.dron2004.translateapp.model.PackageModel;
import ru.dron2004.translateapp.ui.presenters.MainActivityPresenter;
import ru.dron2004.translateapp.ui.views.MainActivity;
import ru.dron2004.translateapp.ui.views.MainActivityView;

public class MainActivityPresenterImpl implements MainActivityPresenter, MainActivityView.PermissionCallback {
    private MainActivityInteractor interactor;
    private WeakReference<MainActivityView> view;
    private PackageModel app;

    public MainActivityPresenterImpl(PackageModel packageModel,MainActivityInteractor i){
        app = packageModel;
        interactor = i;
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
        if (v != null) {
            v.requestInternetPermission(this);
        }
        interactor.checkAppConstantDB();
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
    public void showSettingFragment() {
        MainActivityView v = view.get();
        if (v != null) {
            v.showSettingFragment();
        }
    }

    @Override
    public void onRequestInternetPermission(boolean isGranted) {
        if (!isGranted) {
            MainActivityView v = view.get();
            if (v != null) {
                v.showError(app.getString(R.string.internet_connection_required));
            }
        }
    }
}
