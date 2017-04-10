package ru.dron2004.translateapp.storage.api;

import java.util.List;

import retrofit2.Retrofit;

public class YandexPredictorHelper {
//    private DictRepository repository;
//    private String apiUrl = "https://predictor.yandex.net/";
//    private String apiKey = "pdct.1.1.20170404T051714Z.82447fd42d079c19.fecd4bcd585d3e5069051f336efda707a64081bc";
//    private Retrofit retrofit;
//    private YandexPredictorAPI api;
//
//    public YandexPredictorHelper(DictRepository repository){
//        this.repository = repository;
//        retrofit = new Retrofit.Builder()
//                .baseUrl(apiUrl) //Базовая часть адреса
//                .addConverterFactory(new ToStringConverterFactory()) //Конвертер, необходимый для преобразования JSON'а в объекты
//                .build();
//        api = retrofit.create(YandexPredictorAPI.class);
//    }
//
//
//    public void getSupportLanguages(){
//        api.getSupportedLanguages(apiKey).enqueue(new PredictorSupportLangs(repository));
//    }
//
//    public List<String> getHelpers(String text) {
//        api.getHelpers(apiKey, text, 15);
//        return null;
//    }
}
