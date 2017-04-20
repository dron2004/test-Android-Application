package ru.dron2004.translateapp.ui.presenters;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.dron2004.translateapp.interactors.TranslationFragmentInteractor;
import ru.dron2004.translateapp.model.Language;
import ru.dron2004.translateapp.model.TranslateDirection;
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
    TranslateDirection fakeTranslateDirection = mock(TranslateDirection.class);
    Language fakeLanguage = mock(Language.class);
    List<String> fakeTipsList = new ArrayList<>();
    List<Language> fakeLanguageList = new ArrayList<>();

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
    public void testInitializeViewFirstStart_not_emptyCurrentTranslation() {
        //При сохраненном переводе
        // - смена ориентации
        // - восстановиление из загашника
        Whitebox.setInternalState(presenter,"currentTranslation",fakeTranslation);
        presenter.onStart();

        //Получем список языков из Интерактора - отдаем во фрагмент
        when(fakeInteractor.getLanguages()).thenReturn(fakeLanguageList);

        verify(fakeInteractor,times(1)).getLanguages();
        verify(fakeView,times(1)).setLanguagesList(fakeLanguageList);

        //При не пустом хранящемся переводе
        verify(fakeView,times(1)).setTranslateDirection(fakeTranslation.translateDirection);
        verify(fakeView,times(1)).setTranslatedText(fakeTranslation.textToTranslate);
        verify(fakeView,times(1)).showAddToFavoritesBtn(fakeTranslation.isFavorite());

    }


    @Test
    public void testInitializeViewFirstStart_emptyCurrentTranslation_text_toTranslate_notEmpty() {
        //При недоделанном переводе
        // - смена ориентации
        // - восстановиление из загашника
        when(fakeView.getTextToTranslate()).thenReturn(testText);
        //Передаем направление перевода во Фрагмент
        when(fakeInteractor.getTranslateDirection()).thenReturn(fakeTranslateDirection);
        //Получем список языков из Интерактора - отдаем во фрагмент
        when(fakeInteractor.getLanguages()).thenReturn(fakeLanguageList);
        presenter.onStart();



        verify(fakeInteractor,times(1)).getLanguages();
        verify(fakeView,times(1)).setLanguagesList(fakeLanguageList);



        verify(fakeInteractor,times(1)).getTranslateDirection();
        verify(fakeView,times(1)).setTranslateDirection(fakeTranslateDirection);


        //При пустом хранящемся переводе
        //Анализируем введенный для перевода текст
//        when(fakeView.getTranslatedText()).thenReturn(testText);

        verify(fakeView,times(1)).showTranslateBtn();
    }

    @Test
    public void testInitializeViewFirstStart_emptyCurrentTranslation_text_toTranslate_Empty() {
        //При недоделанном переводе
        // - смена ориентации
        // - восстановиление из загашника
        //Получем список языков из Интерактора - отдаем во фрагмент
        when(fakeInteractor.getLanguages()).thenReturn(fakeLanguageList);
        //Из View возвращаем пустую строку для перевода
        when(fakeView.getTextToTranslate()).thenReturn(emptyString);
        //Передаем направление перевода во Фрагмент
        when(fakeInteractor.getTranslateDirection()).thenReturn(fakeTranslateDirection);
        presenter.onStart();


        verify(fakeInteractor,times(1)).getLanguages();
        verify(fakeView,times(1)).setLanguagesList(fakeLanguageList);
        verify(fakeInteractor,times(1)).getTranslateDirection();
        //При пустом хранящемся переводе
        //Анализируем введенный для перевода текст
        verify(fakeView,times(1)).hideTranslateBtn();
    }

    @Test
    public void testInitializeViewReCreate_No_Translation_completed() {
//        //При перезапуске фрагмента текст для перевода присутствет а переведенный отсутствует
//        when(fakeView.getTextToTranslate()).thenReturn(testText);
//        when(fakeView.getTranslatedText()).thenReturn(emptyString);
//        presenter.onStart();
//        //Убедиться что кнопки Перевести и Добавить в избранное скрываются
//        verify(fakeView,times(1)).showTranslateBtn();
//        verify(fakeView,times(1)).hideAddToFavoritesBtn();
    }

    @Test
    public void testInitializeViewReCreate_Translation_completed() {
        //При перезапуске фрагмента текст для перевода присутствет и переведенный присутствует
        when(fakeView.getTextToTranslate()).thenReturn(testText);
        when(fakeView.getTranslatedText()).thenReturn(testText);
        presenter.onStart();
        //Убедиться что кнопки Перевести и Добавить в избранное скрываются
//        verify(fakeView,times(1)).showTranslateBtn();
//        verify(fakeView,times(1)).showAddToFavoritesBtn(false);
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
//        verify(fakeView,times(1)).showAddToFavoritesBtn();
    }

    @Test
    public void testCallbackTipsCreated(){
        Activity fakeActivity = mock(Activity.class);
        when(fakeView.getActivity()).thenReturn(fakeActivity);
        //По пришествии подсказок
        presenter.onTipsSuccess(fakeTipsList);

        verify(fakeView,times(1)).getActivity();
        //Убедиться что во View отправили сами подсказки
//        verify(fakeView,times(1)).showTipsList(fakeTipsList);
    }

    @Test
    public void onPause() {
        presenter.setView(fakeView);
        presenter.onPause();
        Assert.assertNull(Whitebox.getInternalState(presenter,"view"));
        verify(fakeView,times(1)).hideTipsList();
    }

    @Test
    public void selectedFrom() {
        presenter.selectedFrom(fakeLanguage);
        verify(fakeInteractor,times(1)).changeTranslateDirectionFrom(fakeLanguage);
    }

    @Test
    public void selectedTo() {
        presenter.selectedTo(fakeLanguage);
        verify(fakeInteractor,times(1)).changeTranslateDirectionTo(fakeLanguage);
    }

    @Test
    public void toggleFavorite() {
        Whitebox.setInternalState(presenter,"currentTranslation",fakeTranslation);

        presenter.toggleFavorite();
        verify(fakeInteractor,times(1)).toggleFavorite(fakeTranslation);

    }

    @Test
    public void onError(){
        presenter.onError(testText);
        verify(fakeView,times(1)).showError(testText);
    }
}