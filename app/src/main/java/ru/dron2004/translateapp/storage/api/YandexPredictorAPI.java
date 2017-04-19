package ru.dron2004.translateapp.storage.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexPredictorAPI {

    @GET("/api/v1/predict.json/getLangs")
    public Call<String> getSupportedLanguages(@Query("key")String yaPredictorKey);

    @GET("/api/v1/predict.json/complete")
    public Call<String> getComplete(@Query("key")String yaPredictorKey,@Query("lang")String language,@Query("q")String text,@Query("limit")int limit);

}