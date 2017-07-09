package com.fantasy1022.hackathon.presentation.report;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blankj.utilcode.utils.TimeUtils;
import com.fantasy1022.hackathon.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final String TAG = ReportActivity.class.getSimpleName();
    @BindView(R.id.dateTxt)
    TextView dateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        dateTxt.setText(TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy/MM/dd")));
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateTxt.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
    }


    @OnClick(R.id.dateTxt)
    public void onViewClicked() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ReportActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
}
