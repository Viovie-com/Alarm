package com.viovie.alarmclock;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private TextView mDateText;
    private TextView mTimeText;
    private CheckBox mRepeatCheckbox;
    private LinearLayout mWeekLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setActionBar();
        initialView();
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable close = getResources().getDrawable(R.drawable.ic_close_black);
            close.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(close);
        }
    }

    private void initialView() {
        mDateText = (TextView) findViewById(R.id.text_date);
        mTimeText = (TextView) findViewById(R.id.text_time);
        mRepeatCheckbox = (CheckBox) findViewById(R.id.checkbox_repeat);
        mWeekLayout = (LinearLayout) findViewById(R.id.layout_week);

        mWeekLayout.setVisibility(mRepeatCheckbox.isChecked() ? View.VISIBLE : View.GONE);

        mRepeatCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mWeekLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
