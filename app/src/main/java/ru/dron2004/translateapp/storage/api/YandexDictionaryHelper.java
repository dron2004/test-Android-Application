package ru.dron2004.translateapp.storage.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.utility.LocaleUtils;

public class YandexDictionaryHelper {
    private String apiUrl = "https://dictionary.yandex.net/";
    private String apiKey = "dict.1.1.20170403T144327Z.f4f7bf7aedb03779.aeecc2958d5a7c020003156b90fe39e447c5aa8c";
    private Retrofit retrofit;
    private YandexDictionaryAPI api;

    private YandexDictionaryCallback listner;
    private String locale = "en";

    public YandexDictionaryHelper(YandexDictionaryCallback callback){
        listner = callback;

        retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(YandexDictionaryAPI.class);
    }

    public void getTranslation(final String text, final TranslateDirection translateDirection) {
//        api.translate(apiKey,text,translateDirection.toString()).enqueue(new Callback<TranslateResponse>() {
//            @Override
//            public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
//                Log.d("happy","Dictionary call:"+call.request());
//                if (response.body() != null) {
//                    String translatedText = "";
//                    Log.d("happy","Translate Response Message:"+response.message());
////                    if (response.raw().
//                    try {
//                        Log.d("happy","Translate Response Raw Body:"+response.raw().body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    //Пришел форматированный ответ - собираем
//                    Log.d("happy","Translate Response body:"+response.body().text);
//                    for (String trans :response.body().text) {
//                        translatedText = trans;
//                        //Берем первый перевод
//                        break;
//                    }
//                    //Вызываем калбек со списком языков
////                    listner.onTranslateSuccess(new Translation(translateDirection,text,translatedText));
//                } else {
//                    if (response.code() > 200) {
//                        callbackWithError(response.code(),response.message());
//                    } else {
//                        //Сюда попадаем с кодом менее 201 - успешный - но с неразобранным JSON
////                        listner.onTranslateAPIError(LocaleUtils.getLocaleStringResource(R.string.traslate_api_changeFormat));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TranslateResponse> call, Throwable t) {
//                Log.d("happy","Error:"+ t.getMessage());
//                Log.d("happy","Error:"+ t.getLocalizedMessage());
//                listner.onDictionaryAPIError(t.getLocalizedMessage());
//            }
//        });

    }

    public void getSupportLanguagesPairs(){
        api.getSupportedLanguages(apiKey,locale).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.body() != null) {
                    //Пришел форматированный ответ - собираем
                    //Вызываем калбек со списком языков
                    listner.onDictionaryLanguagesSuccess(response.body());
                } else {
//                    Log.d("happy","BODYNULL:"+response);
                    if (response.code() > 200) {
                        callbackWithError(response.code(),response.message());
                    } else {
                        //Сюда попадаем с кодом менее 201 - успешный - но с неразобранным JSON
                        listner.onDictionaryAPIError(LocaleUtils.getLocaleStringResource(R.string.dictionary_api_changeFormat));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
//                Log.d("happy","Error:"+ t.getMessage());
//                Log.d("happy","Error:"+ t.getLocalizedMessage());
                listner.onDictionaryAPIError(t.getLocalizedMessage());
            }
        });
    }


    private void callbackWithError(int code, String message) {
        switch (code){
            case 401:
                listner.onDictionaryAPIError(LocaleUtils.getLocaleStringResource(R.string.dictionary_apikey_notvalid));
                break;
            case 402:
                listner.onDictionaryAPIError(LocaleUtils.getLocaleStringResource(R.string.dictionary_apikey_blocked));
                break;
            case 403:
                listner.onDictionaryAPIError(LocaleUtils.getLocaleStringResource(R.string.dictionary_apikey_daily_exceeded));
                break;
            case 413:
                listner.onDictionaryAPIError(LocaleUtils.getLocaleStringResource(R.string.dictionary_apikey_text_to_long_error));
                break;
            case 501:
                listner.onDictionaryAPIError(LocaleUtils.getLocaleStringResource(R.string.dictionary_apikey_unsupported_language));
                break;
            default:
                listner.onDictionaryAPIError(LocaleUtils.getLocaleStringResource(R.string.dictionary_unknown_error));
                break;
        }
    }

}
