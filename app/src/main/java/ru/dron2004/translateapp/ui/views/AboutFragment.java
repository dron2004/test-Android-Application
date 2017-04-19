package ru.dron2004.translateapp.ui.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.ui._BaseFragment;
import ru.dron2004.translateapp.ui.presenters.AboutFragmentPresenter;
import ru.dron2004.translateapp.ui.presenters.AboutFragmentPresenterImpl;
import ru.dron2004.translateapp.ui.presenters.PresenterFactory;

public class AboutFragment extends _BaseFragment<AboutFragmentPresenter> implements AboutFragmentView {
    public static final String predictorURL = "https://tech.yandex.ru/predictor/";
    public static final String translateURL = "http://translate.yandex.ru/";

    private TextView text_title_aboutCopyTR,text_title_aboutCopyPD;

    public AboutFragment(){
        //Устанавливаем собиратель презентера
        setPresenterFactory(new PresenterFactory<AboutFragmentPresenter>() {
            @NonNull
            @Override
            public AboutFragmentPresenter createPresenter() {
                return new AboutFragmentPresenterImpl();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            text_title_aboutCopyPD = (TextView) getView().findViewById(R.id.text_title_aboutCopyPD);
            text_title_aboutCopyTR = (TextView) getView().findViewById(R.id.text_title_aboutCopyTR);

            text_title_aboutCopyPD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(predictorURL));
                    startActivity(i);
                }
            });
            text_title_aboutCopyTR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(translateURL));
                    startActivity(i);
                }
            });
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }
}
