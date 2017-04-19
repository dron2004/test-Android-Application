package ru.dron2004.translateapp.ui.presenters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.interactors.TranslationFragmentInteractor;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui.views.TranslateFragmentView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TranslateFragmentPresenterImplTest {
    TranslateFragmentView fakeView = mock(TranslateFragmentView.class);
    TranslationFragmentInteractor fakeInteractor = mock(TranslationFragmentInteractor.class);
//    TranslationFragmentInteractor.TranslationInteractorCallback fakeInteractorCallBackOnTranslation = mock(TranslationFragmentInteractor.TranslationInteractorCallback.class);
//    TranslationFragmentInteractor.TipsInteractorCallback fakeInteractorCallBackOnTips = mock(TranslationFragmentInteractor.TipsInteractorCallback.class);
    Translation fakeTranslation = mock(Translation.class);
    List<String> fakeTipsList = new ArrayList<>();

    TranslateFragmentPresenterImpl presenter;

    String testText = "Text to Translate";
    String emptyString = "";

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new TranslateFragmentPresenterImpl(fakeInteractor);
        presenter.setView(fakeView);
    }



    @Test
    public void testThatTextToTranslateSendToInteractor() throws Exception {
        //Вызываем метод
        presenter.translateText(testText);
        //Проверяем что вызов с текстом ушел в интерактор
        verify(fakeInteractor).getTranslation(testText);
    }



    @Test
    public void testThatViewContainsLinkAfterSetView(){
        presenter.setView(fakeView);
//        WeakReference<TranslateFragmentView> weakFakeView = new WeakReference<TranslateFragmentView>(fakeView);
        Assert.assertEquals("Presenter has not instance view after setView",fakeView,((WeakReference)Whitebox.getInternalState(presenter,"view")).get());
        Assert.assertNotNull("Presenter contains NULL reference on VIEW after setView(View)",Whitebox.getField(presenter.getClass(),"view"));
    }

    @Test
    public void testThatViewNullAfterUnsetView(){
        presenter.unsetView();
        Assert.assertNull("Presenter still contains reference on VIEW after unsetView()",Whitebox.getInternalState(presenter,"view"));
    }

    @Test
    public void testThatViewNullAfterOnPause(){
        presenter.onPause();
        Assert.assertNull("Presenter still contains reference on VIEW after onPause()",Whitebox.getInternalState(presenter,"view"));
    }

    @Test
    public void testInitializeViewFirstStart() {
        //При старте приложения текст для перевода отсутствует
        when(fakeView.getTextToTranslate()).thenReturn(emptyString);
        when(fakeView.getTranslatedText()).thenReturn(emptyString);
        presenter.onStart();
        //Убедиться что кнопки Перевести и Добавить в избранное скрываются
        verify(fakeView).hideTranslateBtn();
        verify(fakeView).hideAddToFavoritesBtn();
    }

    @Test
    public void testInitializeViewReCreate_No_Translation_completed() {
        //При перезапуске фрагмента текст для перевода присутствет а переведенный отсутствует
        when(fakeView.getTextToTranslate()).thenReturn(testText);
        when(fakeView.getTranslatedText()).thenReturn(emptyString);
        presenter.onStart();
        //Убедиться что кнопки Перевести и Добавить в избранное скрываются
        verify(fakeView,times(1)).showTranslateBtn();
        verify(fakeView,times(1)).hideAddToFavoritesBtn();
    }

    @Test
    public void testInitializeViewReCreate_Translation_completed() {
        //При перезапуске фрагмента текст для перевода присутствет и переведенный присутствует
        when(fakeView.getTextToTranslate()).thenReturn(testText);
        when(fakeView.getTranslatedText()).thenReturn(testText);
        presenter.onStart();
        //Убедиться что кнопки Перевести и Добавить в избранное скрываются
        verify(fakeView,times(1)).showTranslateBtn();
        verify(fakeView,times(1)).showAddToFavoritesBtn();
    }

    @Test
    public void testThatInputTextSendToIterator(){
        presenter.onTextToTranslateTyped(testText);
        //Проверяем что вызов с текстом ушел в интерактор
        verify(fakeInteractor,times(1)).getTipsForText(testText);
    }

    @Test
    public void testCallbackTranslationComplete(){
        //По пришествии перевода
        presenter.onTranslateSuccess(fakeTranslation);

        //Убедиться что во View выставили текст перевода
        verify(fakeView,times(1)).setTranslatedText(fakeTranslation.translatedText);
        //Показали кнопку добавить в избранное
        verify(fakeView,times(1)).showAddToFavoritesBtn();
    }

    @Test
    public void testCallbackTipsCreated(){
        //По пришествии подсказок
        presenter.onTipsSuccess(fakeTipsList);

        //Убедиться что во View отправили сами подсказки
        verify(fakeView,times(1)).showTipsList(fakeTipsList);
    }
}