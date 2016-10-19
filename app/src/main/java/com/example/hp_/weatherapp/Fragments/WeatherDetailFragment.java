package com.example.hp_.weatherapp.Fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp_.weatherapp.Model.Forecast;
import com.example.hp_.weatherapp.R;
import com.example.hp_.weatherapp.RecyclerAdapter;
import com.example.hp_.weatherapp.Model.Weather;
import com.example.hp_.weatherapp.Recievers.WeatherDataReciever;

import java.util.ArrayList;

public class WeatherDetailFragment extends Fragment {
    WeatherDataReciever mReciever=new WeatherDataReciever();
    TextView temperatureLabel,locationLabel,timeLabel,precipValue,precipType,humidityValue;
    RelativeLayout relativeLayout;
    ImageView iconView;
    Weather mWeather;
    int mPosition;
    public static final String KEY_WEATHER="KEY_WEATHER";
    public static Fragment createInstance(Weather weather,int color)
    {
        WeatherDetailFragment fragment=new WeatherDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(RecyclerAdapter.KEY_BACKGROUND_COLOR,color);
        bundle.putParcelable(KEY_WEATHER,weather);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.detail_weather,container,false);
        bindViews(v);
        mWeather=getArguments().getParcelable(KEY_WEATHER);
        ArrayList<Weather>list=Forecast.getInstance().getList();
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i)==mWeather)
            {
                mPosition=i;
            }
        }
        int color=getArguments().getInt(RecyclerAdapter.KEY_BACKGROUND_COLOR);
        updateUI();
        relativeLayout.setBackgroundColor(color);
        return v;
    }
    public void bindViews(View v)
    {
        temperatureLabel=(TextView)v.findViewById(R.id.temperatureLabel);
        locationLabel=(TextView)v.findViewById(R.id.locationLabel);
        timeLabel=(TextView)v.findViewById(R.id.timeLabel);
        humidityValue=(TextView)v.findViewById(R.id.humidityValue);
        precipValue=(TextView)v.findViewById(R.id.precipValue);
        precipType=(TextView)v.findViewById(R.id.precipLabel);
        iconView=(ImageView)v.findViewById(R.id.iconView);
        relativeLayout=(RelativeLayout)v.findViewById(R.id.relLayout);
    }
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReciever);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mReciever,new IntentFilter("com.example.hp_.weatherapp.WeatherAction"));
    }

    public void updateUI()
    {
        Weather weather=Forecast.getInstance().getList().get(mPosition);
        if(weather!=mWeather)
        {
            mWeather=weather;
        }
        temperatureLabel.setText(mWeather.getMaxTemperature().toString());
        humidityValue.setText(mWeather.getHumidity().toString());
        if(mWeather.getPrecipType()!=null) {
            precipType.setText(mWeather.getPrecipType());
        }
        precipValue.setText(Math.round(mWeather.getPrecipProbability()*100)+"%");
        timeLabel.setText(getResources().getString(R.string.time,mWeather.getTime()));
        iconView.setImageDrawable(getResources().getDrawable(mWeather.getIcon()));
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location=sp.getString("KEY_LOCATION",null);
        if(location!=null)
        {
            locationLabel.setText(location);
        }
    }
}
