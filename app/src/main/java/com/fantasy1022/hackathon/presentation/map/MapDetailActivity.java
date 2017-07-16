package com.fantasy1022.hackathon.presentation.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasy1022.hackathon.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapDetailActivity extends AppCompatActivity {

    private static final String TAG = MapDetailActivity.class.getSimpleName();
    public static final String KEY_DETAIL_URL = "KEY_DETAIL_URL";
    public static final String KEY_INFO_TXT = "KEY_INFO_TXT";
    private String info;
    private int thumbNum;
    @BindView(R.id.detailImg)
    ImageView detailImg;
    @BindView(R.id.infoTxt)
    TextView infoTxt;
    @BindView(R.id.thunmbImg)
    ImageView thunmbImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra(KEY_DETAIL_URL);
        Picasso.with(this)
                .load(url)
                .fit()
                .centerCrop()
                .into(detailImg);
        info = getIntent().getStringExtra(KEY_INFO_TXT);
        int index = info.indexOf("按讚人數:");
        Log.d(TAG, "index:" + index);
        int indexSolve = info.indexOf("已解決:");
        Log.d(TAG, "indexSolve:" + indexSolve);
        //thumbNum = Integer.parseInt((info.substring(index + 5, indexSolve).replaceAll("\"","")));

        infoTxt.setText(info);
    }

    public static void newIntent(Activity activity, String url, String info) {
        Intent intent = new Intent(activity, MapDetailActivity.class);
        intent.putExtra(KEY_DETAIL_URL, url);
        intent.putExtra(KEY_INFO_TXT, info);
        activity.startActivity(intent);
    }

    @OnClick(R.id.thunmbImg)
    public void onViewClicked() {
        String updateInfo = info.substring(0, info.indexOf("按讚人數:")) + thumbNum++ + info.substring(info.indexOf("已解決:"));

        Toast.makeText(this, "已按讚", Toast.LENGTH_SHORT).show();
    }
}
