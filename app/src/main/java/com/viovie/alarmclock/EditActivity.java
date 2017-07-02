package com.viovie.alarmclock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PARAM_ITEM = "EditActivity.item";
    public static final String PARAM_ITEM_POSITION = "EditActivity.item.position";

    private TextView mDateText;
    private TextView mTimeText;
    private CheckBox mRepeatCheckbox;
    private LinearLayout mWeekLayout;
    private TextView[] mWeekText;
    private EditText mTitleInput;
    private EditText mContentInput;

    private int mPosition;
    private AlarmItem mAlarmItem;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setActionBar();
        initialData();
        initialView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        for (int i = 0, n = menu.size() ; i < n ; i++) {
            Drawable icon = menu.getItem(i).getIcon();
            icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        return true;
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

    private void initialData() {
        mAlarmItem = new AlarmItem();
        mPosition = -1;
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mAlarmItem = (AlarmItem)bundle.get(PARAM_ITEM);
            mPosition = bundle.getInt(PARAM_ITEM_POSITION, -1);
            bundle = null;
        }

        Calendar calendar = mAlarmItem.datetime;
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    private void initialView() {
        mDateText = (TextView) findViewById(R.id.text_date);
        mTimeText = (TextView) findViewById(R.id.text_time);
        mRepeatCheckbox = (CheckBox) findViewById(R.id.checkbox_repeat);
        mWeekLayout = (LinearLayout) findViewById(R.id.layout_week);
        mWeekText = new TextView[7];
        mWeekText[0] = (TextView) findViewById(R.id.text_week_1);
        mWeekText[1] = (TextView) findViewById(R.id.text_week_2);
        mWeekText[2] = (TextView) findViewById(R.id.text_week_3);
        mWeekText[3] = (TextView) findViewById(R.id.text_week_4);
        mWeekText[4] = (TextView) findViewById(R.id.text_week_5);
        mWeekText[5] = (TextView) findViewById(R.id.text_week_6);
        mWeekText[6] = (TextView) findViewById(R.id.text_week_7);
        mTitleInput = (EditText) findViewById(R.id.input_title);
        mContentInput = (EditText) findViewById(R.id.input_content);

        mDateText.setText(String.format("%d-%d-%d", mYear, mMonth+1, mDay));
        mDateText.setOnClickListener(this);

        mTimeText.setText(String.format("%d:%d", mHour, mMinute));
        mTimeText.setOnClickListener(this);

        mRepeatCheckbox.setChecked(mAlarmItem.isRepeat);
        mRepeatCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarmItem.isRepeat = isChecked;
                mWeekLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        mWeekLayout.setVisibility(mRepeatCheckbox.isChecked() ? View.VISIBLE : View.GONE);

        for (int i = 0 ; i < mWeekText.length ; i++) mWeekText[i].setOnClickListener(this);
        if (mRepeatCheckbox.isChecked()) {
            for (int i = 0 ; i < mAlarmItem.weekRepeat.length ; i++) {
                mWeekText[i].setBackgroundResource(mAlarmItem.weekRepeat[i] ? R.drawable.week_selected : 0);
            }
        }

        mTitleInput.setText(mAlarmItem.title);
        mContentInput.setText(mAlarmItem.content);
    }

    private void createDatePicker(int year, int month, int day) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                mAlarmItem.datetime.set(mYear, mMonth, mDay);
                mDateText.setText(String.format("%d-%d-%d", year, month+1, dayOfMonth));
            }
        }, year, month, day).show();
    }

    private void createTimePicker(int hour, int minute) {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                mAlarmItem.datetime.set(mYear, mMonth, mDay, mHour, mMinute);
                mTimeText.setText(String.format("%d:%d", hourOfDay, minute));
            }
        }, hour, minute, true).show();
    }

    private void setWeekTextBackground(int day) {
        mAlarmItem.weekRepeat[day] = !mAlarmItem.weekRepeat[day];
        mWeekText[day].setBackgroundResource(mAlarmItem.weekRepeat[day] ? R.drawable.week_selected : 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                setResult(RESULT_CANCELED);
                finish();
                break;
            }
            case R.id.action_save: {
                mAlarmItem.title = mTitleInput.getText().toString();
                mAlarmItem.content = mContentInput.getText().toString();

                if (mPosition >= 0) {
                    DataStorage.update(this, mPosition, mAlarmItem);
                } else {
                    DataStorage.add(this, mAlarmItem);
                }

                Intent intent = new Intent();
                intent.putExtra(PARAM_ITEM, mAlarmItem);
                intent.putExtra(PARAM_ITEM_POSITION, mPosition);
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_date: {
                createDatePicker(mYear, mMonth, mDay);
                break;
            }
            case R.id.text_time: {
                createTimePicker(mHour, mMinute);
                break;
            }
            case R.id.text_week_1: {
                setWeekTextBackground(0);
                break;
            }
            case R.id.text_week_2: {
                setWeekTextBackground(1);
                break;
            }
            case R.id.text_week_3: {
                setWeekTextBackground(2);
                break;
            }
            case R.id.text_week_4: {
                setWeekTextBackground(3);
                break;
            }
            case R.id.text_week_5: {
                setWeekTextBackground(4);
                break;
            }
            case R.id.text_week_6: {
                setWeekTextBackground(5);
                break;
            }
            case R.id.text_week_7: {
                setWeekTextBackground(6);
                break;
            }
        }
    }
}
