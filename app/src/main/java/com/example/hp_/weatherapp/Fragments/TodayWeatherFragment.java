package com.example.hp_.weatherapp.Fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp_.weatherapp.Model.Forecast;
import com.example.hp_.weatherapp.R;
import com.example.hp_.weatherapp.Model.Weather;
import com.example.hp_.weatherapp.Services.LocationService;
import com.example.hp_.weatherapp.Services.WeatherFetchService;

/**
 * Created by HP- on 03.10.2016.
 */
public class TodayWeatherFragment extends Fragment {
    TextView minTempLabel, maxTempLabel, pressureLabel, windSpeedLabel, dateLabel, currentLocation;
    ImageView mainImage, searchImage;
    EditText searchEditText;
    Weather mWeather;
    RelativeLayout front, back;
    Animator frontAnimator, backAnimator;
    boolean isBackVisible = false;
    AnimatorSet set;

    public static Fragment createInstance() {
        TodayWeatherFragment fragment = new TodayWeatherFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_forecast, container, false);
        bindViews(v);
        searchEditText.setVisibility(View.INVISIBLE);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setVisibility(View.VISIBLE);
                ScaleAnimation anim=new ScaleAnimation(0.0f,1.0f,1.0f,1.0f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.5f);
                anim.setDuration(2000);
                anim.setFillAfter(true);
                searchEditText.startAnimation(anim);
            }
        });
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent intent = new Intent(getActivity(), LocationService.class);
                    intent.putExtra("KEY_INPUT_LOCATION", ((EditText) v).getText().toString());
                    getActivity().startService(intent);
                }
                return false;
            }
        });

        back.setAlpha(0.0f);
        frontAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.front_animator);
        backAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.back_animator);
        v.findViewById(R.id.frameLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor=sp.edit();
                if (!isBackVisible) {
                    frontAnimator.setTarget(front);
                    backAnimator.setTarget(back);
                    set = new AnimatorSet();
                    set.playTogether(frontAnimator, backAnimator);
                    set.start();
                    isBackVisible = true;
                    editor.putString("KEY_UNITS","si");
                } else {
                    frontAnimator.setTarget(back);
                    backAnimator.setTarget(front);
                    set = new AnimatorSet();
                    set.playTogether(frontAnimator, backAnimator);
                    set.start();
                    isBackVisible = false;
                    editor.putString("KEY_UNITS","us");
                }
                editor.commit();
                Intent intent = new Intent(getActivity(), WeatherFetchService.class);
                getActivity().startService(intent);
            }
        });
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        front.setCameraDistance(scale);
        back.setCameraDistance(scale);
        updateUI();
        return v;
    }
    public void bindViews(View v)
    {
        maxTempLabel = (TextView) v.findViewById(R.id.maxTempLabel);
        minTempLabel = (TextView) v.findViewById(R.id.minTempLabel);
        pressureLabel = (TextView) v.findViewById(R.id.pressureLabel);
        windSpeedLabel = (TextView) v.findViewById(R.id.windSpeedLabel);
        mainImage = (ImageView) v.findViewById(R.id.mainImage);
        searchEditText = (EditText) v.findViewById(R.id.searchEditText);
        dateLabel = (TextView) v.findViewById(R.id.dateLabel);
        currentLocation = (TextView) v.findViewById(R.id.currentLocation);
        searchImage = (ImageView) v.findViewById(R.id.searchImage);
        front = (RelativeLayout) v.findViewById(R.id.front);
        back = (RelativeLayout) v.findViewById(R.id.back);
    }
    public void updateUI() {
        if(Forecast.getInstance().getList()!=null) {
            mWeather = Forecast.getInstance().getList().get(0);
            maxTempLabel.setText(mWeather.getMaxTemperature() + "");
            minTempLabel.setText(mWeather.getMinTemperature() + "");
            pressureLabel.setText(mWeather.getPressure().toString());
            windSpeedLabel.setText(mWeather.getWindSpeed() + "");
            dateLabel.setText(mWeather.getStringDate() + ", " + mWeather.getDayName());
            mainImage.setImageDrawable(getResources().getDrawable(mWeather.getMainWeatherIcon()));
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String location = sp.getString("KEY_LOCATION", null);
            if (location != null) {
                currentLocation.setText(location);
            }
        }
    }
}
