package com.example.hp_.weatherapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp_.weatherapp.Model.Forecast;
import com.example.hp_.weatherapp.R;
import com.example.hp_.weatherapp.RecyclerAdapter;
import com.example.hp_.weatherapp.Model.Weather;

import java.util.ArrayList;

/**
 * Created by HP- on 03.10.2016.
 */
public class WeatherListFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    ArrayList<Weather> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_weather,container,false);
        mRecyclerView=(RecyclerView)v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mList= Forecast.getInstance().getList();
        mAdapter=new RecyclerAdapter(mList,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        updateUI();
        return v;
    }
    public static Fragment createInstance()
    {
        WeatherListFragment fragment=new WeatherListFragment();
        return  fragment;
    }
    public void updateUI()
    {
        mList= Forecast.getInstance().getList();
        mAdapter.updateList(mList);
    }
}
