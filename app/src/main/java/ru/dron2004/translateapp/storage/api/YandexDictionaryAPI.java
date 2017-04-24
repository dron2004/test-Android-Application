package ru.dron2004.translateapp.storage.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexDictionaryAPI {

    @GET("/api/v1/dicservice.json/getLangs")
    public Call<List<String>> getSupportedLanguages(@Query("key")String yaDictionaryKey, @Query("ui") String locale);

    @GET("/api/v1/dicservice.json/getLangs")
    public Call<List<String>> getSupportedLanguages(@Query("key")String yaDictionaryKey, @Query("lang") String translateDirections, @Query("text") String text);
}
