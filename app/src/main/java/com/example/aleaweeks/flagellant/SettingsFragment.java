package com.example.aleaweeks.flagellant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by aleaweeks on 2/23/18.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements
SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.prefs);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditTextPreference userPref = (EditTextPreference) findPreference("pref_donation");
        userPref.setSummary(userPref.getText());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_donation_key))) {
            EditTextPreference userPref = (EditTextPreference) findPreference(key);
            userPref.setSummary(userPref.getText());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
