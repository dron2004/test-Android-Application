package ru.dron2004.translateapp.storage.api;

import java.util.List;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.Translation;

public interface YandexTranslateCallback {

    //При получении перевода
    void onTranslateSuccess(Translation translation);

    //При получении поддерживаемых языков переводчиком
    void onTranslateSupportLanguage(List<Language> supportLanguage);

    //При возникновении ошибки
    void onTranslateAPIError(java.lang.String errorMsg);
}
