package ru.dron2004.translateapp.interactors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
import ru.dron2004.translateapp.storage.dao.LanguageDAO;
import ru.dron2004.translateapp.storage.dao.TipsDAO;
import ru.dron2004.translateapp.storage.dao.TranslationDAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.dron2004.translateapp.interactors.TranslationFragmentInteractor.TipsInteractorCallback;
import static ru.dron2004.translateapp.interactors.TranslationFragmentInteractor.TranslationInteractorCallback;

public class TranslationFragmentInteractorImplTest {
    String textToTranslate = "Just simple text to translate";
    String textToTips = "Just simple text for getting tips";

    Language from = new Language("ru","Русский");
    Language to = new Language("en","Английский");
    TranslateDirection translateDirection = new TranslateDirection(from,to);

    TranslationInteractorCallback fakeTranslationCallBack = mock(TranslationInteractorCallback.class);
    TipsInteractorCallback fakeTipsCallBack = mock(TipsInteractorCallback.class);

    TipsDAO fakeTipsDAO = mock(TipsDAO.class);
    LanguageDAO fakeLanguageDAO = mock(LanguageDAO.class);
    TranslationDAO fakeTranslationDAO = mock(TranslationDAO.class);

    TranslationFragmentInteractor interactor;

    @Before
    public void setUp() throws Exception {
        interactor = new TranslationFragmentInteractorImpl(fakeTranslationCallBack,fakeTipsCallBack);
    }

    @Test
    public void test_AfterConstruction_All_DAOs_and_Callback_initted(){
        //Проверяем что после конструирования интерактора там есть слушатель перевода
        Assert.assertEquals("Translation callback not equals that in constructor",
                fakeTranslationCallBack,
                Whitebox.getInternalState(interactor,"translationCallback"));

        //Проверяем что после конструирования интерактора там есть слушатель подсказок
        Assert.assertEquals("Tips callback not equals that in constructor",
                fakeTipsCallBack,
                Whitebox.getInternalState(interactor,"tipsCallBack"));

        //Проверяем что после конструирования интерактора там есть DAO переводов
        Assert.assertNotNull("Inteactor has no translationDAO",Whitebox.getInternalState(interactor,"translationDAO"));
        Assert.assertNotNull("Inteactor has no tipsDAO",Whitebox.getInternalState(interactor,"tipsDAO"));
        Assert.assertNotNull("Inteactor has no languageDAO",Whitebox.getInternalState(interactor,"languageDAO"));
    }

    @Test
    public void getTranslation() throws Exception {
        //Подставим фейковую реализацию DAO
        Whitebox.setInternalState(interactor,"translationDAO",fakeTranslationDAO);

        interactor.getTranslation(textToTranslate);

        //Убедиться что текст для перевода ушел в DAO
        verify(fakeTranslationDAO,times(1)).getTranslation(textToTranslate,translateDirection);
    }

    @Test
    public void getTipsForText() throws Exception {
        //Подставим фейковую реализацию DAO
        Whitebox.setInternalState(interactor,"tipsDAO",fakeTipsDAO);

        interactor.getTipsForText(textToTips);

        //Убедиться что текст для подсказок ушел в DAO
        verify(fakeTipsDAO,times(1)).getTips(textToTips,from,5);
    }

}