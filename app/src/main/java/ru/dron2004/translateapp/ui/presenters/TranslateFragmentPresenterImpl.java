package ru.dron2004.translateapp.ui.presenters;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.dron2004.translateapp.interactors.TranslationFragmentInteractor;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui.views.TranslateFragmentView;

public class TranslateFragmentPresenterImpl
        implements  TranslateFragmentPresenter {
    private WeakReference<TranslateFragmentView> view;
    private TranslationFragmentInteractor interactor;

    private Translation translationModel;
    private List<String> tipsList;
    private Translation currentTranslation;

    public TranslateFragmentPresenterImpl(TranslationFragmentInteractor interactor){
        this.interactor = interactor;
        interactor.registerTipsCallback(this);
        interactor.registerTranslationCallback(this);
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

            //Восстановим список языков
            v.setLanguagesList(interactor.getLanguages());

            //Если есть полученный перевод
            if (currentTranslation != null) {
                //Выставляем текст для перевода
                //Решил воспользоваться сохранением состояния самого фрагмента по ID EditText
//                v.setTextToTranslate(currentTranslation.textToTranslate);
//                //Выставляем направление перевода
                v.setTranslateDirection(currentTranslation.translateDirection);
//                //Выставляем переведенный текст
                v.setTranslatedText(currentTranslation.translatedText);
                v.showAddToFavoritesBtn(currentTranslation.isFavorite());
            } else {
            //Если нет полученного перевода - перевернули в процессе
                v.setTranslateDirection(interactor.getTranslateDirection());
                //Если был введен текст,
                // показать кнопку перевести
                if (v.getTextToTranslate().isEmpty()) {
                    v.hideTranslateBtn();
                } else {
                    v.showTranslateBtn();
                }
            }



//// TODO: 18.04.2017 Узнать жизненный цикл ListPopupWindow
//            if (tipsList != null) {
//                Log.d("happy","Есть сохраненные типсы:"+tipsList);
//                if (tipsList.size()>0)
//                    v.showTipsList(tipsList);
//            }
        }

    }

    @Override
    public void onPause() {
        TranslateFragmentView v = view.get();
        if (v != null) {
            //Опустошаем список подсказок
            v.hideTipsList();
        }
        //Зануляем view
        if(view != null) view = null;
    }

    @Override
    public void onTextToTranslateTyped(String text) {
        Log.d("happy","Presenter has new text:"+text);
        TranslateFragmentView v = view.get();
        if (v != null) {
            if (!text.isEmpty()) {
                //Отобразить загрузку типсов
                v.showTipsLoadingProgress();
                //Показать кнопку перевода
                v.showTranslateBtn();
                interactor.getTipsForText(text);
            } else {
                v.hideTranslateBtn();
                v.hideTipsLoadingProgress();
                v.hideTipsList();
            }
        }
    }

    @Override
    public void translateText(String text) {
        interactor.getTranslation(text);
    }

    @Override
    public boolean toggleFavorite() {
        return interactor.toggleFavorite(currentTranslation);
    }

    @Override
    public void selectedFrom(Language selectedItem) {
        interactor.changeTranslateDirectionFrom(selectedItem);
    }

    @Override
    public void selectedTo(Language selectedItem) {
        interactor.changeTranslateDirectionTo(selectedItem);
    }

    //Обратные вызовы из интерактора
    //Получение перевода
    @Override
    public void onTranslateSuccess(Translation translation) {
        //Закешируем перевод
        currentTranslation = translation;
        //Перевод готов - нате
        TranslateFragmentView v = view.get();
        if (v != null) {
            v.setTranslatedText(translation.translatedText);
            v.showAddToFavoritesBtn(translation.isFavorite());
        }
    }

    //Получение подсказок
    @Override
    public void onTipsSuccess(final List<String> response) {
        //Сохраним в презентере для перестраения фрагмента
        tipsList = response;
        Log.d("happy","Cached Tips:"+tipsList);
        //Подсказки получены - берите
        final TranslateFragmentView v = view.get();
        if (v != null) {
            v.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    v.hideTipsLoadingProgress();
                    v.showTipsList(response);
                }
            });

        }
    }

    @Override
    public void onError(String errorMsg) {
        TranslateFragmentView v = view.get();
        if (v != null)
            v.showError(errorMsg);
    }

    @Override
    public TranslateDirection exchangeTranslateDirection() {
        TranslateDirection newTD = interactor.exchangeTranslateDirection();
        TranslateFragmentView v = view.get();
        if (v != null) {
            v.setTranslateDirection(newTD);
        }
        return newTD;
    }

}
