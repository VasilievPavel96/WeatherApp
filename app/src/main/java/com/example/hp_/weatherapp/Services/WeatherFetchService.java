package com.example.hp_.weatherapp.Services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.hp_.weatherapp.DataUtils;
import com.example.hp_.weatherapp.Model.Forecast;
import com.example.hp_.weatherapp.Model.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class WeatherFetchService extends IntentService {

    public WeatherFetchService() {
        super("WeatherFetchService");
    }
    public static void setServiceAlarm(Context context,boolean isOn)
    {
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(context);
        int freqValue=Integer.valueOf(sp.getString("frequency","1"));
        int repeatTime=1000*60*60;
        switch (freqValue)
        {
            case 1:break;
            case 12:repeatTime=1000*60*60*12; break;
            case 24:repeatTime=1000*60*60*24; break;
        }
        Intent intent=new Intent(context,WeatherFetchService.class);
        PendingIntent pi=PendingIntent.getService(context,0,intent,0);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);
        if(isOn)
        {
            alarmManager.setRepeating(AlarmManager.RTC,System.currentTimeMillis()+repeatTime,repeatTime,pi);
        }
        else
        {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
    public static boolean isAlarmOn(Context context)
    {
        Intent intent=new Intent(context,WeatherFetchService.class);
        PendingIntent pi=PendingIntent.getService(context,0,intent,PendingIntent.FLAG_NO_CREATE);
        return pi!=null;
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<Weather> list=null;
        URL url= null;
        try {
            SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
            String latitude=sp.getString("KEY_LATITUDE","-1");
            String longtitude=sp.getString("KEY_LONGTITUDE","-1");
            if((!latitude.equals("-1"))&&(!longtitude.equals("-1"))) {
                String units = sp.getString("KEY_UNITS", "us");
                url = new URL("https://api.darksky.net/forecast/45ba7ef9be9ecd60bc9848b349d3e690/" + latitude + "," + longtitude + "?units=" + units);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream io = httpURLConnection.getInputStream();
                String JsonString = convertToString(io);
                list = DataUtils.parseDarkskyJSON(JsonString);
                Forecast.getInstance().setList(list);
                sendBroadcast(new Intent("com.example.hp_.weatherapp.WeatherAction"));
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
