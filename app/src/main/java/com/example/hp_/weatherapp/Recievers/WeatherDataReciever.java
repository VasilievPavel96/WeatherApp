package com.example.hp_.weatherapp.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.hp_.weatherapp.Fragments.TodayWeatherFragment;
import com.example.hp_.weatherapp.Fragments.WeatherDetailFragment;
import com.example.hp_.weatherapp.Fragments.WeatherListFragment;

import java.util.List;

public class WeatherDataReciever extends BroadcastReceiver {
    public WeatherDataReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        FragmentActivity activity=(FragmentActivity)context;
        FragmentManager fm=activity.getSupportFragmentManager();
        //TodayWeatherFragment f1=(TodayWeatherFragment)fm.findFragmentByTag("TodayWeatherFragment");
        //WeatherListFragment f2=(WeatherListFragment)fm.findFragmentByTag("WeatherListFragment");
        List<Fragment> fragments=fm.getFragments();
        for(int i=0;i<fragments.size();i++)
        {
            if(fragments.get(i)!=null)
            {
                Fragment fragment=fragments.get(i);
                if( fragment instanceof TodayWeatherFragment)
                {
                    ((TodayWeatherFragment) fragment).updateUI();
                }
                else if(fragment instanceof WeatherListFragment)
                {
                    ((WeatherListFragment) fragment).updateUI();
                }
                else if(fragment instanceof WeatherDetailFragment)
                {
                    ((WeatherDetailFragment) fragment).updateUI();
                }
            }
        }
       /* if((f1!=null)&&(f2!=null))
        {
            Log.d("MyTag","TodayWeatherFragment UPDATE_UI,WeatherListFragment UPDATE_UI");
            f1.updateUI();
            f2.updateUI();
        }*/

    }
}
