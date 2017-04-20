package ru.dron2004.translateapp.actions;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.storage.api.YandexPredictorHelper;
import ru.dron2004.translateapp.storage.api.YandexTranslateHelper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CheckApiOnlineImplTest {
    CheckApiOnlineImpl action;

    YandexPredictorHelper predictorHelper = mock(YandexPredictorHelper.class);
    YandexTranslateHelper translateHelper = mock(YandexTranslateHelper.class);
    CheckApiOnline.Callback callback = mock(CheckApiOnline.Callback.class);

    List<Language> fakeLanguagesList = new ArrayList<>();
    String fakeLocale = "ru";
    private String testError = "This is test error message";

    @Before
    public void setUp() throws Exception {
        action = new CheckApiOnlineImpl(callback);
        Whitebox.setInternalState(action,"predictorHelper",predictorHelper);
        Whitebox.setInternalState(action,"translateHelper",translateHelper);
    }

    @Test
    public void checkInternetPermission() throws Exception {
        Assert.assertTrue(action.checkInternetPermission());
    }

    @Test
    public void checkInternetConnection() throws Exception {
        //Use Statiic Utility
        //Assert.assertTrue(action.checkInternetConnection());
    }

    @Test
    public void checkAPIKeys() throws Exception {
        action.checkAPIKeys();
        verify(predictorHelper,times(1)).getSupportLanguages();
    }

    @Test
    public void onCompleteSuccess() throws Exception {
        //Not Used
    }

    @Test
    public void onPredictorSupportLanguagesSuccess() throws Exception {
        action.onPredictorSupportLanguagesSuccess(fakeLanguagesList);
        verify(translateHelper,times(1)).getLanguageTitles(fakeLocale);
    }

    @Test
    public void onPredictorAPIError() throws Exception {
        action.onPredictorAPIError(testError);
        verify(callback,times(1)).onAPIKeyIsError(testError);
    }

    @Test
    public void onTranslateSuccess() throws Exception {
        //Not Used
    }

    @Test
    public void onTranslateSupportLanguage() throws Exception {
        action.onTranslateSupportLanguage(fakeLanguagesList);
        verify(callback,times(1)).onAPIKeyIsValid();
    }

    @Test
    public void onTranslateAPIError() throws Exception {
        action.onTranslateAPIError(testError);
        verify(callback,times(1)).onAPIKeyIsError(testError);
    }

}