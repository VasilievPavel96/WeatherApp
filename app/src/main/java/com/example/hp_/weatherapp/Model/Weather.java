package com.example.hp_.weatherapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hp_.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather implements Parcelable{
    Date mDate;
    Double mMinTemperature;
    Double mMaxTemperature;
    Double mHumidity;
    Double mWindSpeed;
    Double mPressure;
    int mIcon;
    String mPrecipType;
    Float mPrecipProbability;
    String mWeatherLabel;

    public Float getPrecipProbability() {
        return mPrecipProbability;
    }

    public void setPrecipProbability(Float precipProbability) {
        mPrecipProbability = precipProbability;
    }

    public Weather()
    {

    }
    protected Weather(Parcel in) {
        mDate=(Date)in.readSerializable();
        mMinTemperature=in.readDouble();
        mMaxTemperature=in.readDouble();
        mHumidity=in.readDouble();
        mWindSpeed=in.readDouble();
        mPressure=in.readDouble();
        mIcon = in.readInt();
        mPrecipType=in.readString();
        mPrecipProbability=in.readFloat();
        mWeatherLabel=in.readString();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public String getDay()
    {
        SimpleDateFormat sdf=new SimpleDateFormat("E");
        return sdf.format(mDate);
    }
    public String getDayName()
    {
        SimpleDateFormat sdf=new SimpleDateFormat("EEEE");
        return sdf.format(mDate);
    }
    public String getStringDate()
    {
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(mDate);
    }
    public String getTime()
    {
        SimpleDateFormat sdf=new SimpleDateFormat("h:mm a");
        return  sdf.format(mDate);
    }
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Long getMinTemperature() {
        return Math.round(mMinTemperature);
    }

    public void setMinTemperature(Double minTemperature) {
        mMinTemperature = minTemperature;
    }

    public Long getMaxTemperature() {
        return Math.round(mMaxTemperature);
    }

    public void setMaxTemperature(Double maxTemperature) {
        mMaxTemperature = maxTemperature;
    }

    public Double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Double humidity) {
        mHumidity = humidity;
    }

    public Double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public Double getPressure() {
        return mPressure;
    }

    public void setPressure(Double pressure) {
        mPressure = pressure;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "mWeatherLabel='" + mWeatherLabel + '\'' +
                ", mMinTemperature=" + mMinTemperature +
                ", mMaxTemperature=" + mMaxTemperature +
                ", mHumidity=" + mHumidity +
                ", mWindSpeed=" + mWindSpeed +
                ", mPressure=" + mPressure +
                '}';
    }

    public int getIcon() {
        return mIcon;
    }

    public int getMainWeatherIcon() {
        int ic;
        switch (mWeatherLabel)
        {
            case "clear-day":ic= R.drawable.main_clear_day;break;
            case "clear-night":ic=R.drawable.main_clear_night;break;
            case "cloudy":ic=R.drawable.main_cloudy;break;
            case "fog":ic=R.drawable.main_fog;break;
            case "partly-cloudy-day":ic=R.drawable.main_partly_cloudy_day;break;
            case "partly-cloudy-night":ic=R.drawable.main_partly_cloudy_night;break;
            case "rain":ic=R.drawable.main_rain;break;
            case "sleet":ic=R.drawable.main_sleet;break;
            case "snow":ic=R.drawable.main_snow;break;
            case "wind":ic=R.drawable.main_wind;break;
            default:ic=R.drawable.main_warning;break;
        }
        return ic;
    }

    public void setIcon(String icon) {
        int ic;
        mWeatherLabel=icon;

        switch (icon)
        {
            case "clear-day":ic=R.drawable.clear_day;break;
            case "clear-night":ic=R.drawable.clear_night;break;
            case "cloudy":ic=R.drawable.cloudy;break;
            case "fog":ic=R.drawable.fog;break;
            case "partly-cloudy-day":ic=R.drawable.partly_cloudy_day;break;
            case "partly-cloudy-night":ic=R.drawable.partly_cloudy_night;break;
            case "rain":ic=R.drawable.rain;break;
            case "sleet":ic=R.drawable.sleet;break;
            case "snow":ic=R.drawable.snow;break;
            case "wind":ic=R.drawable.wind;break;
            default:ic=R.drawable.warning;break;

        }
        mIcon=ic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPrecipType() {
        return mPrecipType;
    }

    public void setPrecipType(String precipType) {
        mPrecipType = precipType.toUpperCase();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mDate);
        dest.writeDouble(mMinTemperature);
        dest.writeDouble(mMaxTemperature);
        dest.writeDouble(mHumidity);
        dest.writeDouble(mWindSpeed);
        dest.writeDouble(mPressure);
        dest.writeInt(mIcon);
        dest.writeString(mPrecipType);
        dest.writeFloat(mPrecipProbability);
        dest.writeString(mWeatherLabel);
    }
}
