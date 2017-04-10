package ru.dron2004.translateapp.ui.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.model.PackageModel;
import ru.dron2004.translateapp.ui.presenters.*;
import ru.dron2004.translateapp.interactors.*;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private static final int REQUEST_INTERNET_PERMISSION = 23022;
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
                case R.id.navigation_setting:
                    presenter.showSettingFragment();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenterImpl(new PackageModel(getApplicationContext()),new MainActivityInteractorImpl(new PackageModel(getApplicationContext())));
        presenter.setView(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Log.d("happy","Activity onCreate");

    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();

        presenter.firstStart();
    }

    private PermissionCallback permissionCallback;

    /**
     * Проверка разрешения доступа к сети
     * а оно оказывается не опастное - привет Google!
     * @param listner
     */
    @Override
    public void requestInternetPermission(PermissionCallback listner) {
        Log.d("happy","In Activity");
        this.permissionCallback = listner;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            Log.d("happy","Permission Not Granted");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d("happy","SDK >= M");
                requestPermissions(
                        new String[]{
                                Manifest.permission.INTERNET
                        } ,
                        REQUEST_INTERNET_PERMISSION
                );
            } else {
                Log.d("happy","SDK < M");
            }
        } else {
            listner.onRequestInternetPermission(true);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_INTERNET_PERMISSION && permissions[0].equals(Manifest.permission.INTERNET) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionCallback.onRequestInternetPermission(true);
        } else {
            permissionCallback.onRequestInternetPermission(false);
        }
    }



    @Override
    public void showTranslateFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.placeForFragment, TranslateFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showFavoritesFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.placeForFragment, FavoriteFragment.newInstance("",""));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showSettingFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.placeForFragment, new SettingFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
