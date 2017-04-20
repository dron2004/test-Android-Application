package ru.dron2004.translateapp.ui.views;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.interactors.TranslationFragmentInteractorImpl;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.ui._BaseFragment;
import ru.dron2004.translateapp.ui.presenters.PresenterFactory;
import ru.dron2004.translateapp.ui.presenters.TranslateFragmentPresenter;
import ru.dron2004.translateapp.ui.presenters.TranslateFragmentPresenterImpl;
import ru.dron2004.translateapp.ui.view_adapters.TipsAdapter;
import ru.dron2004.translateapp.utility.KeyBoardUtils;
import ru.dron2004.translateapp.utility.LocaleUtils;

public class TranslateFragment extends _BaseFragment<TranslateFragmentPresenter> implements TranslateFragmentView {
    private EditText textToTranslate;
    private TextView translatedTextView,yandexTranslateCopy;
    private Button buttonAddToFavorites;
    private Button buttonTranslate;
    private ProgressBar inputTextProgress;
    private Spinner spinnerFrom,spinnerTo;
    private ImageView exchangeTranslateDirection;

    private ListPopupWindow popUpWindow;

    private List<String> tipsList = new ArrayList<>();

    private ListAdapter tipsAdapter;
    private boolean tipsShow;
    private List<Language> languagesList;


    public TranslateFragment() {
        //Устанавливаем собиратель презентера
        setPresenterFactory(new PresenterFactory<TranslateFragmentPresenter>() {
            @NonNull
            @Override
            public TranslateFragmentPresenter createPresenter() {
                return  new TranslateFragmentPresenterImpl(
                        new TranslationFragmentInteractorImpl(getPresenter(),getPresenter()));
            }
        });

    }

    public static TranslateFragment newInstance() {
        TranslateFragment fragment = new TranslateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            translatedTextView = (TextView) getView().findViewById(R.id.translatedTextView);
            textToTranslate = (EditText) getView().findViewById(R.id.textToTranslate);
            buttonTranslate = (Button) getView().findViewById(R.id.buttonTranslate);
            buttonAddToFavorites = (Button) getView().findViewById(R.id.buttonAddToFavorites);
            inputTextProgress = (ProgressBar) getView().findViewById(R.id.inputTextProgress);
            yandexTranslateCopy = (TextView) getView().findViewById(R.id.yandexTranslateCopy);

            spinnerFrom = (Spinner) getView().findViewById(R.id.spinnerFrom);
            spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getPresenter().selectedFrom(languagesList.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });


            spinnerTo = (Spinner) getView().findViewById(R.id.spinnerTo);
            spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getPresenter().selectedTo(languagesList.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });


            exchangeTranslateDirection = (ImageView) getView().findViewById(R.id.exchangeTranslateDirection);

            //Отправляем в презентер клики по кнопке Перевода
            buttonTranslate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textToTranslate.clearFocus();
                    KeyBoardUtils.hide(getActivity());
                    getPresenter().translateText(getTextToTranslate());
                }
            });

            buttonAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddToFavoritesBtn(getPresenter().toggleFavorite());
                }
            });
            //Отправляем в презентер набираемый текст
            textToTranslate.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    getPresenter().onTextToTranslateTyped(s.toString());
                }
                @Override public void afterTextChanged(Editable s) {}
            });

        } else {
            //TODO Активность почему то не подцепилась к фрагменту - ???
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    public String getTextToTranslate() {
        return textToTranslate.getText().toString();
    }

    @Override
    public void setTextToTranslate(String text) {
        textToTranslate.setText(text);
    }


    @Override
    public void setTranslatedText(String text) {
        yandexTranslateCopy.setText(LocaleUtils.getLocaleStringResource(R.string.yandex_translate_copy));
        translatedTextView.setText(text.trim());

        //TODO Использовать увеличение только в случае одного слова
//        float maxTextSizePx = getResources().getDimensionPixelSize(R.dimen.max_text_size);
//        float maxTextWidthPx = getResources().getDimensionPixelSize(R.dimen.max_text_width);
//        WidgetUtils.fitText(translatedTextView, text.trim(), maxTextSizePx, maxTextWidthPx);
    }

    @Override
    public String getTranslatedText() {
        return translatedTextView.getText().toString();
    }

    @Override
    public void clearTextToTranslate() {
        textToTranslate.setText("");
    }

    @Override
    public void showTipsLoadingProgress() {
        inputTextProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTipsLoadingProgress() {
        inputTextProgress.setVisibility(View.GONE);
    }

    @Override
    public void showTipsList(List<String> tipsList) {
        //Сохранили подсказки
        this.tipsList = tipsList;
//        Log.d("happy","Tipsы привалили:"+tipsList);
        if (getActivity() != null) {
            showPopUpListView(textToTranslate.getText().toString());
        }
    }


    private void showPopUpListView(String before) {
        if (popUpWindow == null) {
//            Log.d("happy","PopUp was NULL - create to activity:"+getActivity());
            popUpWindow = new ListPopupWindow(getActivity());
        }
        if (popUpWindow.isShowing()) {
//            Log.d("happy","PopUp is show - change ADAPTER");
            popUpWindow.setAdapter(createTipsAdapter(before,tipsList));
        } else {
//            Log.d("happy","PopUp need to show - create new tipsAdapter and show");
            popUpWindow.setAnchorView(textToTranslate);
            popUpWindow.setVerticalOffset(getActivity().getResources().getDimensionPixelOffset(R.dimen.tips_popup_vertical_offset));
            popUpWindow.setAdapter(createTipsAdapter(before,tipsList));
            popUpWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    textToTranslate.setText(((TipsAdapter) parent.getAdapter()).getSelectedTip(position));
                    textToTranslate.setSelection(textToTranslate.length());
                    popUpWindow.dismiss();
                }
            });
            popUpWindow.show();
        }
    }

    private void hidePopUpListView(){
        if (popUpWindow != null) {
            popUpWindow.dismiss();
            popUpWindow = null;
//            Log.d("happy", "PopUp is Dissmissed");
        }
    }

    @Override
    public void hideTipsList() {
        hidePopUpListView();
        this.tipsList.clear();
    }

    private TipsAdapter createTipsAdapter(String before,List<String> tips){
        ArrayList<SpannableString> tipsSpanned = new ArrayList<>();
        String newBefore = before;
        String wordBegin;
        if (before.contains(" ")) {
            //Если есть пробел то подсказка к последнему слову
            wordBegin = before.substring(before.lastIndexOf(" ")+1);
        } else {
            //если пробелов нет то ко всему тексту
            wordBegin = before;
        }
        Log.d("happy","WordBegin:"+wordBegin);

        for (String tip : tips) {
                String newTip = tip;
                int startSpanned = 0;
                int lastSpanned = 0;
                String addonSpace = " ";
                if (tip.startsWith(wordBegin)) {
                    if (!wordBegin.isEmpty())
                        newTip = tip.substring(wordBegin.length());
                    lastSpanned = before.length();
                    addonSpace = "";
                } else {
                    //TODO куст серени
//                    if (!wordBegin.isEmpty()){
////                        newTip = tip;
//                        lastSpanned = before.length()-1-wordBegin.length()-1;
//                        newBefore = before.substring(0,before.length()-wordBegin.length());
////                        addonSpace = " ";
//                    }
                }
                SpannableString tipSpanned = new SpannableString(newBefore + addonSpace + newTip);
                tipSpanned.setSpan(new ForegroundColorSpan(Color.BLUE), startSpanned, lastSpanned,0);
                tipsSpanned.add(tipSpanned);
        }
        return new TipsAdapter(getActivity(),R.layout.tip_element,tipsSpanned);
    }

    @Override
    public void setTranslateDirection(TranslateDirection dir) {
        spinnerFrom.setSelection(languagesList.indexOf(dir.from));
        spinnerTo.setSelection(languagesList.indexOf(dir.to));
    }

    @Override
    public void showAddToFavoritesBtn(boolean favorite) {
        Drawable img;
        String buttonText;
        if (favorite) {
            //Если избранное
            img = getContext().getResources().getDrawable( R.drawable.ic_favorite_border_red_24dp );
            img.setBounds( 0, 0, 60, 60 );
            buttonText = LocaleUtils.getLocaleStringResource(R.string.remove_from_favorite_button);
        } else {
            //Если не избранное
            img = getContext().getResources().getDrawable( R.drawable.ic_favorite_border_black_24dp);
            img.setBounds( 0, 0, 60, 60 );
            buttonText = LocaleUtils.getLocaleStringResource(R.string.add_to_favorite_button);
        }
        buttonAddToFavorites.setCompoundDrawables( null, img, null, null );
        buttonAddToFavorites.setText(buttonText);
        buttonAddToFavorites.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAddToFavoritesBtn() {
        buttonAddToFavorites.setVisibility(View.GONE);
    }

    @Override
    public void showTranslateBtn() {
        buttonTranslate.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTranslateBtn() {
        buttonTranslate.setVisibility(View.GONE);
    }

    @Override
    public void setLanguagesList(List<Language> languagesList) {
        Language[] languages = languagesList.toArray(new Language[languagesList.size()]);
        this.languagesList = languagesList;
        ArrayAdapter spinnerAdapterFrom = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, languages);
        spinnerAdapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(spinnerAdapterFrom);
        ArrayAdapter spinnerAdapterTo = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, languages);
        spinnerAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(spinnerAdapterTo);
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        }
    }



}
