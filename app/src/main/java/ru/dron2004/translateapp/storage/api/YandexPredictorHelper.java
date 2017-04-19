package ru.dron2004.translateapp.storage.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.utility.LocaleUtils;

public class YandexPredictorHelper {
    private  YandexPredictorCallback listner;
    private String apiUrl = "https://predictor.yandex.net/";
    private String apiKey = "pdct.1.1.20170404T051714Z.82447fd42d079c19.fecd4bcd585d3e5069051f336efda707a64081bc";
    private Retrofit retrofit;
    private YandexPredictorAPI api;

    public YandexPredictorHelper(YandexPredictorCallback callback){
        this.listner = callback;
        retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl) //Базовая часть адреса
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new ToStringConverterFactory())
                .build();
        api = retrofit.create(YandexPredictorAPI.class);
    }


    public void getSupportLanguages(){
        Log.d("happy","Try to get support languages from Predictor");
        api.getSupportedLanguages(apiKey).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("happy","Predictor support call:"+call.request());
                if (response.body() != null) {
//                    Log.d("happy","Success:" + response.body());

                    JsonParser parser = new JsonParser();
                    JsonElement jsonElement = parser.parse(response.body());
                    Gson gson = new Gson();
                    List<String> supportLangsIndents = gson.fromJson(jsonElement,ArrayList.class);

                    List<Language> resultList = new ArrayList<Language>();

                    for (String id :supportLangsIndents) {
                        resultList.add(new Language(id));
                    }

                    listner.onPredictorSupportLanguagesSuccess(resultList);

                } else {
//                    Log.d("happy","BODYNULL:"+response);
                    if (response.code() > 200) {
                        callbackWithError(response.code(),response.message());
                    } else {
                        //Сюда попадаем с кодом менее 201 - успешный - но с неразобранным JSON
                        listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_api_changeFormat));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("happy","Error:"+ t.getMessage());
                Log.d("happy","Error:"+ t.getLocalizedMessage());
                listner.onPredictorAPIError(t.getLocalizedMessage());
            }
        });
    }


    public void getTipForText(String text, final Language language, int limit) {
        api.getComplete(apiKey,language.ident,text,limit).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("happy","Complete call:"+call.request());
                if (response.body() != null) {
//                    Log.d("happy","Success:" + response.body());

                    JsonParser parser = new JsonParser();
                    JsonElement jsonElement = parser.parse(response.body());
                    Gson gson = new Gson();
                    ArrayList<String> complete = gson.fromJson(jsonElement.getAsJsonObject().get("text"),ArrayList.class);
                    listner.onCompleteSuccess(complete,language);

                } else {
//                    Log.d("happy","BODYNULL:"+response);
                    if (response.code() > 200) {
                        callbackWithError(response.code(),response.message());
                    } else {
                        //Сюда попадаем с кодом менее 201 - успешный - но с неразобранным JSON
                        listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_api_changeFormat));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("happy","Error:"+ t.getMessage());
                Log.d("happy","Error:"+ t.getLocalizedMessage());
                listner.onPredictorAPIError(t.getLocalizedMessage());
            }
        });
    }


    private void callbackWithError(int code, String message) {
        switch (code){
            case 401:
                listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_apikey_notvalid));
                break;
            case 402:
                listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_apikey_blocked));
                break;
            case 403:
                listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_apikey_daily_exceeded));
                break;
            case 404:
                listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_apikey_daily_exceeded));
                break;
            case 413:
                listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_apikey_text_to_long_error));
                break;
            case 501:
                listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_apikey_unsupported_language));
                break;
            default:
                listner.onPredictorAPIError(LocaleUtils.getLocaleStringResource(R.string.predictor_unknown_error));
                break;
        }
    }
}
