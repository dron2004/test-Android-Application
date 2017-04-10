package ru.dron2004.translateapp.ui.views;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dron2004.translateapp.R;

public class SettingFragment extends PreferenceFragmentCompat {
    private ListPreference fromLangPreference,toLangPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        fromLangPreference = (ListPreference)  getPreferenceManager().findPreference("preference_key");
////        fromLangPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
////            @Override
////            public boolean onPreferenceChange(Preference preference, Object newValue) {
////                // your code here
////                Log.d("happy","New Pref value:"+newValue);
////                return false;
////            }
////        });
//        return inflater.inflate(R.layout.fragment_settings, container, false);
//    }
}
