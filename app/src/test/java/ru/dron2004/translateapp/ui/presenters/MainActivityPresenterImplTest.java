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
        //TODO Разобраться
//        presenter.unsetView();
//        Assert.assertNull(Whitebox.getField(presenter.getClass(),"view"));
    }

    @Test
    public void onStart() throws Exception {

    }

    @Test
    public void onPause() throws Exception {
        //TODO Разобраться
//        presenter.onPause();
//        Assert.assertNull(Whitebox.getField(presenter.getClass(),"view"));
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

}