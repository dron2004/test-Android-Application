package ru.dron2004.translateapp.ui.presenters;

import java.lang.ref.WeakReference;
import java.util.List;
import ru.dron2004.translateapp.model.*;
import ru.dron2004.translateapp.interactors.*;
import ru.dron2004.translateapp.ui.views.*;

public class TranslateFragmentPresenterImpl implements TranslateFragmentPresenter, TranslationFragmentInteractor.Callback {
    private WeakReference<TranslateFragmentView> view;
    private TranslationFragmentInteractor interactor;

    private Translation translationModel;
    private List<String> tipsList;

    public TranslateFragmentPresenterImpl(TranslationFragmentInteractor interactor){
        this.interactor = interactor;
        this.interactor.registerCallback(this);
    }

    @Override
    public void setView(TranslateFragmentView view) {
        this.view = new WeakReference<TranslateFragmentView>(view);
    }

    @Override
    public void unsetView() {
        this.view = null;
    }

    @Override
    public void onStart() {
        //Инициализируем интерфейс фрагмента
        TranslateFragmentView v = view.get();
        if (v != null){
            //Если был введен текст, показать кнопку перевести
            if (v.getTextToTranslate().isEmpty()) {
                v.hideTranslateBtn();
            } else {
                v.showTranslateBtn();
            }
            //Если был выполнен перевод, показать кнопку добавить в избранное
            if (v.getTranslatedText().isEmpty()) {
                v.hideAddToFavoritesBtn();
            } else {
                v.showAddToFavoritesBtn();
            }

        }
    }

    @Override
    public void onPause() {
        //Зануляем view
        if(view != null) view = null;
    }

    @Override
    public void onTextToTranslateTyped(String text) {
        interactor.getTipsForText(text);
    }

    @Override
    public void translateText(String text) {
        interactor.getTranslation(text);
    }

    //Обратные вызовы из интерактора
    @Override
    public void onTranslation(Translation translation) {
        //Перевод готов - нате
        TranslateFragmentView v = view.get();
        if (v != null) {
            v.setTranslatedText(translation.translatedText);
            v.showAddToFavoritesBtn();
        }
    }

    @Override
    public void onTipsCreated(List<String> tips) {
        //Подсказки получены - берите
        TranslateFragmentView v = view.get();
        if (v != null)
            v.showTipsList(tips);
    }
}
