package com.example.hp_.weatherapp;

import android.util.Log;

import com.example.hp_.weatherapp.Model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HP- on 25.09.2016.
 */
public class DataUtils {
    public static ArrayList<Weather> parseDarkskyJSON(String json)
    {
        ArrayList<Weather> arrayList=new ArrayList<>();
        try {
            JSONObject forecast=new JSONObject(json);
            JSONObject daily=forecast.getJSONObject("daily");
            JSONArray days=daily.getJSONArray("data");
            for(int i=0;i<days.length();i++) {
                JSONObject day=(JSONObject)days.get(i);
                Weather weather=new Weather();
                weather.setDate(new Date(day.getLong("time")*1000));
                weather.setMinTemperature(day.getDouble("temperatureMin"));
                weather.setMaxTemperature(day.getDouble("temperatureMax"));
                weather.setPressure(day.getDouble("pressure"));
                weather.setHumidity(day.getDouble("humidity"));
                weather.setWindSpeed(day.getDouble("windSpeed"));
                weather.setPrecipProbability(Float.parseFloat(Double.valueOf(day.getDouble("precipProbability")).toString()));
                weather.setPrecipType(day.optString("precipType","NO PRECIPATION"));
                weather.setIcon(day.getString("icon"));
                arrayList.add(weather);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public static String[] parseYandexMapsJson(String json)
    {
        String result[]=null;
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONObject response=jsonObject.getJSONObject("response");
            JSONObject GeoObjectCollection=response.getJSONObject("GeoObjectCollection");
            JSONArray featureMember=GeoObjectCollection.getJSONArray("featureMember");
            JSONObject object=(JSONObject)featureMember.get(0);
            JSONObject GeoObject=object.getJSONObject("GeoObject");
            result=new String[2];
            result[0]=GeoObject.getJSONObject("Point").getString("pos");
              result[1]=GeoObject.getJSONObject("metaDataProperty").
                      getJSONObject("GeocoderMetaData").
                      getJSONObject("AddressDetails").
                      getJSONObject("Country").
                      getJSONObject("AdministrativeArea").
                      getString("AdministrativeAreaName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static ArrayList<Weather> parseXML(String xml)
    {
        return null;
    }
}
