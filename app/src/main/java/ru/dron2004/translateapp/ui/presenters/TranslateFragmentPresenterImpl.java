package ru.dron2004.translateapp.ui.presenters;

import android.content.Intent;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.dron2004.translateapp.interactors.TranslationFragmentInteractor;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui.views.DetailActivity;
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
        if (view  != null) {
            TranslateFragmentView v = view.get();
            if (v != null) {

                //Восстановим список языков
                v.setLanguagesList(interactor.getLanguages());

                //Если есть полученный перевод
                if (currentTranslation != null) {
                    //Выставляем текст для перевода
                    //Решил воспользоваться сохранением состояния самого фрагмента по ID EditText - теперь можно - при програмном обновлении поля мы гасим слушатель текста
                    v.setTextToTranslate(currentTranslation.textToTranslate);
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

            }
        }

    }

    @Override
    public void onPause() {
        if (view  != null) {
            TranslateFragmentView v = view.get();
            if (v != null) {
                //Опустошаем список подсказок
                v.hideTipsList();
            }
        }
        //Зануляем view
        if(view != null) view = null;
    }

    @Override
    public void onTextToTranslateTyped(String text) {
//        Log.d("happy","Presenter has new text:"+text);
        if (view  != null) {
            TranslateFragmentView v = view.get();
            if (v != null) {
                if (!text.isEmpty()) {
                    //Отобразить загрузку типсов
                    v.showTipsLoadingProgress();
                    //Показать кнопку перевода
                    v.showTranslateBtn();
                    interactor.getTipsForText(text);
                } else {
                    //Если пришел пустой текст
                    //Скрыть кнопку перевода
                    v.hideTranslateBtn();
                    //Скрыть прогресс загрузки
                    v.hideTipsLoadingProgress();
                    //Скрыть попап подсказок
                    v.hideTipsList();
                    //Обнулить перевод если был
                    if (currentTranslation != null) {
                        currentTranslation = null;
                        v.setTranslatedText("");
                        v.hideAddToFavoritesBtn();
                    }
                }
            }
        } else {
            Log.d("happy","VIEW IS NULL");
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
        if (view  != null) {
            TranslateFragmentView v = view.get();
            if (v != null) {
                v.setTranslatedText(translation.translatedText);
                v.showAddToFavoritesBtn(translation.isFavorite());
            }
        }
    }

    //Получение подсказок
    @Override
    public void onTipsSuccess(final List<String> response) {
        //Сохраним в презентере для перестраения фрагмента
        tipsList = response;
//        Log.d("happy","Cached Tips:"+tipsList);
        //Подсказки получены - берите
        if (view  != null) {
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
    }

    @Override
    public void onError(final String errorMsg) {
        if (view  != null) {
            final TranslateFragmentView v = view.get();
            if (v != null) {
                v.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Ошибка могла возникнуть в момент подбора подсказок - Скрыть прогресс
                        v.hideTipsLoadingProgress();
                        //Отобразить ошибку
                        v.showError(errorMsg);
                    }
                });
            }
        }
    }

    @Override
    public TranslateDirection exchangeTranslateDirection() {
        TranslateDirection newTD = interactor.exchangeTranslateDirection();
        if (view!=null) {
            TranslateFragmentView v = view.get();
            if (v != null) {
                v.setTranslateDirection(newTD);
            }
        }
        return newTD;
    }

    @Override
    public void translateClicked() {
        if (currentTranslation != null) {
            if (view!=null) {
                TranslateFragmentView v = view.get();
                if (v != null) {
                    Intent intent = new Intent(v.getActivity(), DetailActivity.class);
                    intent.putExtra("TRANSLATION", currentTranslation);
                    v.getActivity().startActivity(intent);
                }
            }
        }
    }

}
