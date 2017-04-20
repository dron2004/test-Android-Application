package ru.dron2004.translateapp.ui.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.interactors.MainActivityInteractorImpl;
import ru.dron2004.translateapp.ui.presenters.MainActivityPresenter;
import ru.dron2004.translateapp.ui.presenters.MainActivityPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private static final String TRANSLATE_FRAGMENT = "Translate Fragment";
    private static final String HISTORY_FRAGMENT = "History Fragment";
    private static final String SETTING_FRAGMENT = "Setting Fragment";
    private String lastShowingFragment;
    private String showingFragment;
    private BottomNavigationView navigation;
    private MainActivityPresenter presenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_translate:
                    presenter.showTranslateFragment();
                    return true;
                case R.id.navigation_favorite:
                    presenter.showFavoritesFragment();
                    return true;
                case R.id.navigation_about:
                    presenter.showAboutFragment();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenterImpl(new MainActivityInteractorImpl(presenter));

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        presenter.setView(this);

        presenter.onStart();

        if (savedInstanceState == null){
            presenter.firstStart();
        }
        Log.d("happy","Activity onCreate");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {

        }
    }

    @Override
    public void showTranslateFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.placeForFragment, new TranslateFragment());
        transaction.addToBackStack(TRANSLATE_FRAGMENT);
        lastShowingFragment = showingFragment;
        showingFragment = TRANSLATE_FRAGMENT;
        transaction.commit();
        Log.d("happy","Count fragments:"+getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void showFavoritesFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.placeForFragment, new FavoriteFragment());
        transaction.addToBackStack(HISTORY_FRAGMENT);
        lastShowingFragment = showingFragment;
        showingFragment = HISTORY_FRAGMENT;
        transaction.commit();
        Log.d("happy","Count fragments:"+getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void showAboutFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.placeForFragment, new AboutFragment());
        transaction.addToBackStack(SETTING_FRAGMENT);
        lastShowingFragment = showingFragment;
        showingFragment = SETTING_FRAGMENT;
        transaction.commit();
        Log.d("happy","Count fragments:"+getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void makeSelectionNavigator(int navigationID) {
        navigation.setSelectedItemId(navigationID);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
