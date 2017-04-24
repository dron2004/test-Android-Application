package ru.dron2004.translateapp.storage.api;

import java.util.List;

import ru.dron2004.translateapp.model.dr.DictionaryResponce;

public interface YandexDictionaryCallback {

    void onDictionaryLanguagesSuccess(List<String> supportedPairs);
    void onLookupSuccess(DictionaryResponce dictionaryResponce);
    void onDictionaryAPIError(String errorMsg);

}
