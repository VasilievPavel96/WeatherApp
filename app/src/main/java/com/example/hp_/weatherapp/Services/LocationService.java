package com.example.hp_.weatherapp.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.hp_.weatherapp.DataUtils;
import com.example.hp_.weatherapp.Model.Forecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class LocationService extends IntentService {


    public LocationService() {
        super("LocationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String location=intent.getStringExtra("KEY_INPUT_LOCATION");
        URL url= null;
        try {
            url = new URL("https://geocode-maps.yandex.ru/1.x/?geocode="+location+"&format=json&results=1");
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream io=httpURLConnection.getInputStream();
            String JsonString=convertToString(io);
            String locationDetails[]=DataUtils.parseYandexMapsJson(JsonString);
            String locationCoords=locationDetails[0];
            if(locationCoords!=null) {

                String arr[]=locationCoords.split(" ");
                SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
                sp.edit().putString("KEY_LATITUDE",arr[1]).
                        putString("KEY_LONGTITUDE",arr[0]).
                        putString("KEY_LOCATION",locationDetails[1]).
                        commit();
                Intent intent1=new Intent(getApplicationContext(),WeatherFetchService.class);
                getApplicationContext().startService(intent1);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  String convertToString(InputStream inputStream)
    {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder=new StringBuilder();
        String line;
        try {
            while((line=bufferedReader.readLine())!=null)
            {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
