package ru.dron2004.translateapp.storage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.model.TranslateResponse;
import ru.dron2004.translateapp.model.TranslateSupportLanguagesResponse;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.utility.LocaleUtils;

public class YandexTranslateHelper {
    private String apiUrl = "https://translate.yandex.net/";
    private String apiKey = "trnsl.1.1.20170404T051044Z.d9827db52a8e7880.9a77d457ce601cac0a1146710fc73f5e874c157f";
    private Retrofit retrofit;
    private YandexTranslateAPI api;

    private YandexTranslateCallback listner;
    private String locale;

    public YandexTranslateHelper(YandexTranslateCallback callback,String locale){
        listner = callback;
        this.locale = locale;
        retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(YandexTranslateAPI.class);
    }

    public void getTranslation(final String text, final TranslateDirection translateDirection) {
        api.translate(apiKey,text,translateDirection.toString()).enqueue(new Callback<TranslateResponse>() {
            @Override
            public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
//                Log.d("happy","Translate call:"+call.request());
                if (response.body() != null) {
                    String translatedText = "";
                    //Пришел форматированный ответ - собираем
                    for (String trans :response.body().text) {
                        translatedText = trans;
                        //Берем первый перевод
                        break;
                    }
                    //Вызываем калбек со списком языков
                    listner.onTranslateSuccess(new Translation(translateDirection,text,translatedText));
                } else {
                    if (response.code() > 200) {
                        callbackWithError(response.code(),response.message());
                    } else {
                        //Сюда попадаем с кодом менее 201 - успешный - но с неразобранным JSON
                        listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.traslate_api_changeFormat));
                    }
                }
            }

            @Override
            public void onFailure(Call<TranslateResponse> call, Throwable t) {
//                Log.d("happy","Error:"+ t.getMessage());
//                Log.d("happy","Error:"+ t.getLocalizedMessage());
                listner.onTranslateAPIError(t.getLocalizedMessage());
            }
        });

    }

    public void getLanguageTitles(String locale){
        api.getSupportedLanguages(apiKey,locale).enqueue(new Callback<TranslateSupportLanguagesResponse>() {
            @Override
            public void onResponse(Call<TranslateSupportLanguagesResponse> call, Response<TranslateSupportLanguagesResponse> response) {
//                Log.d("happy","Translate support call:"+call.request());
                if (response.body() != null) {
                    //Пришел форматированный ответ - собираем
                    List<Language> resultList = new ArrayList<Language>();
                    for (Map.Entry<String, String> lang :response.body().langs.entrySet()) {
                        resultList.add(new Language(lang.getKey(),lang.getValue()));
                    }
//                    Log.d("happy","Language List:"+resultList);
                    //Вызываем калбек со списком языков
                    listner.onTranslateSupportLanguage(resultList);
                } else {
//                    Log.d("happy","BODYNULL:"+response);
                    if (response.code() > 200) {
                        callbackWithError(response.code(),response.message());
                    } else {
                        //Сюда попадаем с кодом менее 201 - успешный - но с неразобранным JSON
                        listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.traslate_api_changeFormat));
                    }
                }
            }

            @Override
            public void onFailure(Call<TranslateSupportLanguagesResponse> call, Throwable t) {
//                Log.d("happy","Error:"+ t.getMessage());
//                Log.d("happy","Error:"+ t.getLocalizedMessage());
                listner.onTranslateAPIError(t.getLocalizedMessage());
            }
        });
    }


    private void callbackWithError(int code, String message) {
        switch (code){
            case 401:
                listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.translate_apikey_notvalid));
                break;
            case 402:
                listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.translate_apikey_blocked));
                break;
            case 404:
                listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.translate_apikey_daily_exceeded));
                break;
            case 413:
                listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.translate_apikey_text_to_long_error));
                break;
            case 422:
                listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.translate_apikey_textnot_translable));
                break;
            case 501:
                listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.translate_apikey_unsupported_language));
                break;
            default:
                listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.translate_unknown_error));
                break;
        }
    }

}
