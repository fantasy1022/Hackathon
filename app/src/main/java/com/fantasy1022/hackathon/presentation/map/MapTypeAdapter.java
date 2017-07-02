package com.fantasy1022.hackathon.presentation.map;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.fantasy1022.hackathon.R;

/**
 * Created by fantasy_apple on 2017/7/2.
 */

public class MapTypeAdapter extends BaseAdapter implements SpinnerAdapter {

    private String[] items;
    private int[] colors;

    public MapTypeAdapter(String[] items,int[] colors) {
        this.items = items;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_map_type, null);
        TextView textView = (TextView) view.findViewById(R.id.map_type_text);
        textView.setText(items[position]);
        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_map_type_dropdown, null);
        TextView textView = (TextView) view.findViewById(R.id.map_type_text);
        textView.setText(items[position]);

        ImageView imageView = (ImageView) view.findViewById(R.id.color_icon_view);
        GradientDrawable bgShape = (GradientDrawable) imageView.getBackground();
        bgShape.setColor(colors[position]);

        //imageView.setImageDrawable(ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.round_icon));
        return view;
    }
}
