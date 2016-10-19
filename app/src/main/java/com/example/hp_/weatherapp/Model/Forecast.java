package com.example.hp_.weatherapp.Model;

import java.util.ArrayList;

/**
 * Created by HP- on 03.10.2016.
 */
public class Forecast {
    ArrayList<Weather> mList;
    private static Forecast mInstance;

    public ArrayList<Weather> getList() {
        return mList;
    }

    public static Forecast getInstance() {

        if(mInstance==null)
        {
            mInstance=new Forecast();
        }
        return mInstance;

    }

    public void setList(ArrayList<Weather> list) {
        mList = list;
    }

    private Forecast() {
    }
}
