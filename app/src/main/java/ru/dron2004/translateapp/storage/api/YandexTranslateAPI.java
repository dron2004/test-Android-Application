package ru.dron2004.translateapp.storage.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexTranslateAPI {
    @GET("/api/v1.5/tr.json/getLangs")
    public Call<String> getSupportedLanguages(@Query("key") String apiKey, @Query("ui") String ui);
}
