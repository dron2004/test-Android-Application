package ru.dron2004.translateapp.actions;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.storage.api.YandexPredictorHelper;
import ru.dron2004.translateapp.storage.api.YandexTranslateHelper;
import ru.dron2004.translateapp.storage.dao.LanguageDAO;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SyncDBonStartImplTest {
    SyncDBonStartImpl action;
    SyncDBonStart.Callback callback = mock(SyncDBonStart.Callback.class);
    LanguageDAO languageDAO = mock(LanguageDAO.class);

    //Ссылки на API
    YandexTranslateHelper translateHelper = mock(YandexTranslateHelper.class);
    YandexPredictorHelper predictorHelper = mock(YandexPredictorHelper.class);

    String appLocale = "ru";
    List<Language> testLanguageList = mock(ArrayList.class);

    List<Language> predictorLanguages = new ArrayList<>();
    {
        predictorLanguages.add(new Language("ru","Russian"));
        predictorLanguages.add(new Language("en","English"));
        predictorLanguages.add(new Language("kz","Kazahian"));
    }
    List<Language> translateLanguages = new ArrayList<>();
    {
        translateLanguages.add(new Language("ru","Russian"));
        translateLanguages.add(new Language("en","English"));
        translateLanguages.add(new Language("br","Braziliaaan"));
    }
    List<Language> appSupportLanguages = new ArrayList<>();
    {
        appSupportLanguages.add(new Language("ru","Russian"));
        appSupportLanguages.add(new Language("en","English"));
    }

    private String testError = "ErrorMessage for Test";

    @Before
    public void setUp() throws Exception {
        action = new SyncDBonStartImpl(callback);
        Whitebox.setInternalState(action,"languageDAO",languageDAO);
        Whitebox.setInternalState(action,"translateHelper",translateHelper);
        Whitebox.setInternalState(action,"predictorHelper",predictorHelper);
        Whitebox.setInternalState(action,"appLocale",appLocale);
    }

    @Test
    public void syncDBAction() throws Exception {
        action.syncDBAction(appLocale);
        verify(predictorHelper,times(1)).getSupportLanguages();
    }

    @Test
    public void onPredictorSupportLanguagesSuccess() throws Exception {
        action.onPredictorSupportLanguagesSuccess(predictorLanguages);
        verify(translateHelper,times(1)).getLanguageTitles(appLocale);
    }

    @Test
    public void onTranslateSupportLanguage() throws Exception {
        //TODO Описать логику пересечения полученных массивов языков
        //Типа возвращенные языки из предиктора
        Whitebox.setInternalState(action,"predictorLanguages",predictorLanguages);
        action.onTranslateSupportLanguage(translateLanguages);

        //Проверяем установление языков из переводчика
        assertEquals(
                "inner Translate Support Languages not equals that come from API Callback",
                translateLanguages,
                Whitebox.getInternalState(action,"translateLanguages"));
        assertEquals("intersection language Failed",
                appSupportLanguages,
                Whitebox.getInternalState(action,"appSupportLanguages")
                );
    }

    @Test
    public void onTranslateAPIError() throws Exception {
        action.onTranslateAPIError(testError);
        verify(callback,times(1)).onErrorSync(testError);
    }

    @Test
    public void onPredictorAPIError() throws Exception {
        action.onPredictorAPIError(testError);
        verify(callback,times(1)).onErrorSync(testError);
    }

    @Test
    public void onCompleteSuccess() throws Exception {
        //Not Used
    }

    @Test
    public void onTranslateSuccess() throws Exception {
        //Not Used
    }

    @Test
    public void onSuccess() throws Exception {
        Whitebox.setInternalState(action,"appSupportLanguages",testLanguageList);
        action.onSuccess(true);
        verify(callback,times(1)).onSuccessSync(testLanguageList);
    }

    @Test
    public void onError() throws Exception {
        action.onError(testError);
        verify(callback,times(1)).onErrorSync(testError);
    }

}