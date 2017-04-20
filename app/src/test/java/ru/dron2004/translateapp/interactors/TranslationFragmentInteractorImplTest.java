package ru.dron2004.translateapp.interactors;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import ru.dron2004.translateapp.model.PackageModel;
import ru.dron2004.translateapp.storage.dao.SettingDAO;
import ru.dron2004.translateapp.storage.dao.SettingDAOImpl;

import static org.mockito.Mockito.mock;

/**
 * Created by Андрей on 20.04.2017.
 */
public class TranslationFragmentInteractorImplTest {
    TranslationFragmentInteractor.TranslationInteractorCallback translationInteractorCallback = mock(TranslationFragmentInteractor.TranslationInteractorCallback.class);
    TranslationFragmentInteractor.TipsInteractorCallback tipsInteractorCallback = mock(TranslationFragmentInteractor.TipsInteractorCallback.class);

    TranslationFragmentInteractor interactor;

    Context context = mock(Context.class);
    SettingDAO settingDAO = new SettingDAOImpl(context);
    PackageModel packageModel = mock(PackageModel.class);


    @Before
    public void setUp() throws Exception {
//        when(interactor.getSettingDAO()).thenReturn(settingDAO);
//        interactor = new TranslationFragmentInteractorImpl(translationInteractorCallback,tipsInteractorCallback);
    }

    @Test
    public void registerTranslationCallback() throws Exception {
//        Assert.assertEquals(
//                "Translation callback Not Registred",
//                translationInteractorCallback,
//                Whitebox.getInternalState(interactor,"translationCallback")
//        );
    }

    @Test
    public void registerTipsCallback() throws Exception {

    }

    @Test
    public void getTranslateDirection() throws Exception {

    }

    @Test
    public void toggleFavorite() throws Exception {

    }

    @Test
    public void getLanguages() throws Exception {

    }

    @Test
    public void changeTranslateDirectionTo() throws Exception {

    }

    @Test
    public void changeTranslateDirectionFrom() throws Exception {

    }

    @Test
    public void getTranslation() throws Exception {

    }

    @Test
    public void onTranslationSuccess() throws Exception {

    }

    @Test
    public void onTranslationError() throws Exception {

    }

    @Test
    public void getTipsForText() throws Exception {

    }

    @Test
    public void onTipsSuccess() throws Exception {

    }

    @Test
    public void onTipsError() throws Exception {

    }

    @Test
    public void onTipsEmpty() throws Exception {

    }

    @Test
    public void onCompleteSuccess() throws Exception {

    }

    @Test
    public void onPredictorSupportLanguagesSuccess() throws Exception {

    }

    @Test
    public void onPredictorAPIError() throws Exception {

    }

    @Test
    public void onTranslateSuccess() throws Exception {

    }

    @Test
    public void onTranslateSupportLanguage() throws Exception {

    }

    @Test
    public void onTranslateAPIError() throws Exception {

    }

}