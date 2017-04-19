package ru.dron2004.translateapp.ui.presenters;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.dron2004.translateapp.interactors.FavoriteFragmentInteactor;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui.views.FavoriteFragmentView;

public class FavoriteFragmentPresenterImpl implements FavoriteFragmentPresenter, FavoriteFragmentInteactor.HistoryCallback {
    private WeakReference<FavoriteFragmentView> view;
    private FavoriteFragmentInteactor inteactor;
    private List<Translation> translationsList;

    public FavoriteFragmentPresenterImpl(FavoriteFragmentInteactor inteactor){
        this.inteactor = inteactor;
        this.inteactor.registerHistoryCallback(this);
    }

    @Override
    public void setView(FavoriteFragmentView view) {
        this.view = new WeakReference<FavoriteFragmentView>(view);
    }

    @Override
    public void unsetView() {
        view = null;
    }

    @Override
    public void onStart() {
        //Запрашиваем интерактор список переводов
        inteactor.getTranslationHistory();
    }

    @Override
    public void onPause() {
        view = null;
    }

    @Override
    public void clickOnHistoryItem(Translation selectedTranslation) {

    }


    @Override
    public boolean toggleHistoryFavorite(Translation translation) {
        return inteactor.toggleHistoryFavorite(translation);
    }

    @Override
    public List<Translation> getTranslationHistory() {
        return null;
    }

    @Override
    public void historyItemSwipeLeft(Translation translation) {
        //Окрыть перевод
//        FavoriteFragmentView v = view.get();
//        if (v != null) {
//            v.showTranslateFragment(translation);
//        }
        inteactor.removeTranslation(translation);
    }

    @Override
    public void historyItemSwipeRight(Translation translation) {
        inteactor.removeTranslation(translation);
    }


    //Возврат истории
    @Override
    public void onHistorySuccess(List<Translation> translationList) {
        //Закешируем список переводов
        this.translationsList = translationList;
        FavoriteFragmentView v = view.get();
        if (v != null) {
            if (translationList.size() > 0)
                v.setHistory(translationList);
            else
                v.setEmptyHistory();
        }
    }

    @Override
    public void onHistoryEmpty() {

    }

    @Override
    public void onHistoryError(String errorMsg) {

    }
}
