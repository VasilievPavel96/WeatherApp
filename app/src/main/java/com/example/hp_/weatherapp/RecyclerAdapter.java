package com.example.hp_.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp_.weatherapp.Activities.ViewPagerActivity;
import com.example.hp_.weatherapp.Model.Weather;

import java.util.ArrayList;

/**
 * Created by HP- on 23.09.2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public static final String KEY_WEATHER_ITEM = "KEY_WEATHER_ITEM";
    public static final String KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR";
    ArrayList<Weather> list;
    public static int colorList[] = new int[]{Color.parseColor("#b0120a"), Color.parseColor("#b0120a"),
            Color.parseColor("#c41411"), Color.parseColor("#d01716"), Color.parseColor("#dd191d"),
            Color.parseColor("#e51c23"), Color.parseColor("#e84e40"), Color.parseColor("#f36c60")};
    Context ctx;

    public RecyclerAdapter(ArrayList<Weather> arrayList, Context context) {
        list = arrayList;
        ctx = context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_forecast_item, parent, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ViewPagerActivity.class);
                intent.putExtra(KEY_WEATHER_ITEM, position + 1);
                ctx.startActivity(intent);
            }
        });
        holder.dayLabel.setText(list.get(position + 1).getDayName());
        holder.minTempLabel.setText(list.get(position + 1).getMinTemperature().toString());
        holder.maxTempLabel.setText(list.get(position + 1).getMaxTemperature().toString());
        holder.imageView.setImageDrawable(ctx.getResources().getDrawable(list.get(position + 1).getIcon()));
        holder.relativeLayout.setBackgroundColor(colorList[position % colorList.length]);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size() - 1;
        } else return 0;
    }

    public void updateList(ArrayList<Weather> data) {
        list = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayLabel, minTempLabel, maxTempLabel;
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            dayLabel = (TextView) itemView.findViewById(R.id.dayLabel);
            minTempLabel = (TextView) itemView.findViewById(R.id.minTempLabel);
            maxTempLabel = (TextView) itemView.findViewById(R.id.maxTempLabel);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

}
