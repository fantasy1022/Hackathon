package com.fantasy1022.hackathon.presentation.bot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fantasy1022.hackathon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BotActivity extends AppCompatActivity {

    @BindView(R.id.botWebView)
    WebView botWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);
        ButterKnife.bind(this);
        WebSettings webSettings = botWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        botWebView.loadUrl("http://35.184.134.106/");
    }
}
