package com.fantasy1022.hackathon.presentation.filing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fantasy1022.hackathon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filing);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.filing_title);
    }
}
