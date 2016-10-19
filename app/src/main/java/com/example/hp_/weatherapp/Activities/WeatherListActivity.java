package com.example.hp_.weatherapp.Activities;

import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.hp_.weatherapp.Fragments.TodayWeatherFragment;
import com.example.hp_.weatherapp.Fragments.WeatherListFragment;
import com.example.hp_.weatherapp.Fragments.WeatherPreferences;
import com.example.hp_.weatherapp.Model.Forecast;
import com.example.hp_.weatherapp.R;
import com.example.hp_.weatherapp.Model.Weather;
import com.example.hp_.weatherapp.Recievers.WeatherDataReciever;

import java.util.ArrayList;

public class WeatherListActivity extends AppCompatActivity{
    WeatherDataReciever mReciever = new WeatherDataReciever();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment todayFragment = fm.findFragmentById(R.id.mainContainer);
            Fragment listFragment = fm.findFragmentById(R.id.listContainer);
            fm.beginTransaction().hide(todayFragment)
                    .hide(listFragment).replace(R.id.mainContainer, new WeatherPreferences()).addToBackStack(null).commit();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "Settings");
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        addFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fm = getSupportFragmentManager();
        WeatherListFragment fragment = (WeatherListFragment) fm.findFragmentByTag("WeatherListFragment");
        if (fragment != null) {
            fragment.updateUI();
        }
        TodayWeatherFragment fragment2 = (TodayWeatherFragment) fm.findFragmentByTag("TodayWeatherFragment");
        if (fragment2 != null) {
            fragment2.updateUI();
        }
        registerReceiver(mReciever, new IntentFilter("com.example.hp_.weatherapp.WeatherAction"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReciever);
    }

    public void addFragments()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fr = fragmentManager.findFragmentById(R.id.listContainer);
        if (fr == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.listContainer, WeatherListFragment.createInstance(), "WeatherListFragment")
                    .commit();
        }
        Fragment fr2 = fragmentManager.findFragmentById(R.id.mainContainer);
        if (fr2 == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.mainContainer, TodayWeatherFragment.createInstance(), "TodayWeatherFragment")
                    .commit();
        }
    }
}
