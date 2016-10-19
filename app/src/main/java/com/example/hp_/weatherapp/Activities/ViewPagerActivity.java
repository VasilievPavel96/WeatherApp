package com.example.hp_.weatherapp.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp_.weatherapp.Fragments.WeatherDetailFragment;
import com.example.hp_.weatherapp.Model.Forecast;
import com.example.hp_.weatherapp.R;
import com.example.hp_.weatherapp.RecyclerAdapter;

public class ViewPagerActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager=(ViewPager)findViewById(R.id.viewPager);
        mTabLayout=(TabLayout)findViewById(R.id.tablLayout);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return WeatherDetailFragment.createInstance(Forecast.getInstance().getList().get(position),RecyclerAdapter.colorList[position]);
            }
            @Override
            public int getCount() {
                return Forecast.getInstance().getList().size();
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return Forecast.getInstance().getList().get(position).getDayName();
            }
        });
        Intent intent=getIntent();
        int postion=intent.getIntExtra(RecyclerAdapter.KEY_WEATHER_ITEM,0);
        mViewPager.setCurrentItem(postion);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
