package ru.dron2004.translateapp.ui.presenters;

import android.support.annotation.NonNull;

import ru.dron2004.translateapp.ui._BasePresenter;

public interface PresenterFactory<P extends _BasePresenter> {

    /**
     * Create a new instance of a Presenter
     *
     * @return The Presenter instance
     */
    @NonNull
    P createPresenter();
}