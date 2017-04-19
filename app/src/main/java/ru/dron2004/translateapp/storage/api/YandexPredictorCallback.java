package ru.dron2004.translateapp.storage.api;

import java.util.List;

import ru.dron2004.translateapp.model.Language;


public interface YandexPredictorCallback {
    //При успешном возврате водсказки
    void onCompleteSuccess(List<String> tips, Language language);
    //При успешном возврате поддерживаемых языков
    void onPredictorSupportLanguagesSuccess(List<Language> supportLanguagesIdents);
    //При возникновении какой либо ошибки
    void onPredictorAPIError(String errorMsg);
}
