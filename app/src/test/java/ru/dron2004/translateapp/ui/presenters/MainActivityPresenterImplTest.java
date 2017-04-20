package ru.dron2004.translateapp.ui.presenters;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import ru.dron2004.translateapp.interactors.MainActivityInteractor;
import ru.dron2004.translateapp.ui.views.MainActivityView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MainActivityPresenterImplTest {
    String errorMsg = "just simple error string";
    MainActivityView fakeView = mock(MainActivityView.class);
    MainActivityInteractor fakeInteractor = mock(MainActivityInteractor.class);

    MainActivityPresenter presenter = new MainActivityPresenterImpl(fakeInteractor);

    @Before
    public void setUp() throws Exception {
        presenter.setView(fakeView);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setView() throws Exception {
        presenter.setView(fakeView);
        Assert.assertNotNull(Whitebox.getField(presenter.getClass(),"view"));
    }

    @Test
    public void unsetView() throws Exception {
        presenter.unsetView();
        Assert.assertNull(Whitebox.getInternalState(presenter,"view"));
    }

    @Test
    public void onStart() throws Exception {
        presenter.onStart();
        //Убедиться что в презентере в момент старта уже сохраненена ссылка на View
        Assert.assertNotNull(Whitebox.getField(presenter.getClass(),"view"));
    }

    @Test
    public void onPause() throws Exception {
        presenter.onPause();
        Assert.assertNull(Whitebox.getInternalState(presenter,"view"));
    }

    @Test
    public void firstStart(){
        presenter.firstStart();
//        убедиться что вызов уходит в интерактор для проверки настроек
        verify(fakeInteractor,times(1)).checkAPIServices();
    }

    @Test
    public void showTranslateFragment() throws Exception {
        presenter.showTranslateFragment();
        verify(fakeView,times(1)).showTranslateFragment();
    }

    @Test
    public void showFavoritesFragment() throws Exception {
        presenter.showFavoritesFragment();
        verify(fakeView,times(1)).showFavoritesFragment();
    }

    @Test
    public void showSettingFragment() throws Exception {
        presenter.showAboutFragment();
        verify(fakeView,times(1)).showAboutFragment();
    }

    @Test
    public void checkAPISuccess() {
        //При успешной проверке и сихронизации БД Отображаем первый фрагмент (Переводчик)
        presenter.checkAPISuccess();
        verify(fakeView,times(1)).showTranslateFragment();
    }

    @Test
    public void checkAPIError() {
        presenter.checkAPIError(errorMsg);
        verify(fakeView,times(1)).showError(errorMsg);
    }

}