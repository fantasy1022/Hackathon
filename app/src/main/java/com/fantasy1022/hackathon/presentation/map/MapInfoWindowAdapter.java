package com.fantasy1022.hackathon.presentation.map;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasy1022.hackathon.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fantasy_apple on 2017/7/8.
 */

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    @BindView(R.id.event_image)
    ImageView eventImage;
    @BindView(R.id.content_txt)
    TextView contentTxt;


    public MapInfoWindowAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.adapter_map_info_window, null);
        ButterKnife.bind(this, view);
        contentTxt.setText(marker.getTitle());
        Picasso.with(context)
                .load(marker.getSnippet())
                .resizeDimen(R.dimen.map_info_window_width, R.dimen.map_info_window_height)
                .centerCrop()
                .into(eventImage);
        return view;
    }
}
