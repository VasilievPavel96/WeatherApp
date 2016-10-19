package com.example.hp_.weatherapp.Fragments;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.hp_.weatherapp.R;
import com.example.hp_.weatherapp.Recievers.WeatherDataReciever;
import com.example.hp_.weatherapp.Services.WeatherFetchService;
import com.github.machinarius.preferencefragment.PreferenceFragment;

/**
 * Created by HP- on 05.10.2016.
 */
public class WeatherPreferences extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    boolean isFrequencyChanged = false;
    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.prefs);
        PreferenceManager manager=getPreferenceManager();
        manager.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("frequency")) {
            isFrequencyChanged = true;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean turnOn = sp.getBoolean("update", false);
        boolean isOn = WeatherFetchService.isAlarmOn(getActivity());
        if ((isOn) && (!turnOn)) {
            WeatherFetchService.setServiceAlarm(getActivity(), false);
        } else if ((!isOn) && (turnOn)) {
            WeatherFetchService.setServiceAlarm(getActivity(), true);
        } else if ((isOn) && (isFrequencyChanged)) {
            WeatherFetchService.setServiceAlarm(getActivity(), false);
            WeatherFetchService.setServiceAlarm(getActivity(), true);
        }
        sp.unregisterOnSharedPreferenceChangeListener(this);
    }
}
