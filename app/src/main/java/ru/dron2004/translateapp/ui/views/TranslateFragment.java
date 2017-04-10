package ru.dron2004.translateapp.ui.views;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.interactors.TranslationFragmentInteractorImpl;
import ru.dron2004.translateapp.ui.helpers.CustomTokenizer;
import ru.dron2004.translateapp.ui.presenters.TranslateFragmentPresenterImpl;
import ru.dron2004.translateapp.utility.WidgetUtils;

public class TranslateFragment extends Fragment implements TranslateFragmentView {

    private TranslateFragmentPresenterImpl presenter;

    private MultiAutoCompleteTextView textToTranslate;
    private TextView translatedTextView;
    private FloatingActionButton buttonAddToFavorites;
    private Button buttonTranslate;
    private ProgressBar inputTextProgress;

    private boolean firstStart;

    private List<String> tipsList = new ArrayList<>();


    public TranslateFragment() {
        // Required empty public constructor
        presenter = new TranslateFragmentPresenterImpl(new TranslationFragmentInteractorImpl());
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
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter.setView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.unsetView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            translatedTextView = (TextView) getView().findViewById(R.id.translatedTextView);
            textToTranslate = (MultiAutoCompleteTextView) getView().findViewById(R.id.textToTranslate);
            buttonTranslate = (Button) getView().findViewById(R.id.buttonTranslate);
            buttonAddToFavorites = (FloatingActionButton) getView().findViewById(R.id.buttonAddToFavorites);
            inputTextProgress = (ProgressBar) getView().findViewById(R.id.inputTextProgress);
            //Подключаем адаптер для отображения подсказок
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, tipsList);
            textToTranslate.setAdapter(adapter);
            //Устанавливаем разделитель по пробелу
            textToTranslate.setTokenizer(new CustomTokenizer(" ".charAt(0)));
            //Отправляем в презентер клики по кнопке
            buttonTranslate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.translateText(getTextToTranslate());
                }
            });

//            setTranslatedText("YAYAYdskaj ");
        } else {
            //TODO Активность почему то не подцепилась к фрагменту - ???
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onStart();
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
        translatedTextView.setText(text.trim());

        //TODO Использовать уселичение только в случае одного слова
        float minTextSizePx = getResources().getDimensionPixelSize(R.dimen.max_text_size);
        float maxTextWidthPx = getResources().getDimensionPixelSize(R.dimen.max_text_width);
        WidgetUtils.fitText(translatedTextView, text.trim(), minTextSizePx, maxTextWidthPx);
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
        this.tipsList = tipsList;
        //TODO Отобразить подсказки принудительно
//        textToTranslate
    }

    @Override
    public void hideTipsList() {
        this.tipsList.clear();
        //TODO Скрыть подсказки принудительно
    }

    @Override
    public void showAddToFavoritesBtn() {
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
    public void showError(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        }
    }
}
