package ru.dron2004.translateapp.storage.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.dron2004.translateapp.model.TranslateResponse;
import ru.dron2004.translateapp.model.TranslateSupportLanguagesResponse;

public interface YandexTranslateAPI {
    @GET("/api/v1.5/tr.json/getLangs")
    public Call<TranslateSupportLanguagesResponse> getSupportedLanguages(@Query("key") String apiKey, @Query("ui") String ui);

    @GET("/api/v1.5/tr.json/translate?format=plain&options=1")
    public Call<TranslateResponse> translate(
            @Query("key") String apiKey,
            @Query("text") String textToTranslate,
            @Query("lang") String translateDirection
    );
}
