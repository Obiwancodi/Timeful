package com.thinkful.timeful;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.view.View;

import java.util.Calendar;

public class StartDateCalActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private int y;
    private int mon;
    private int d;
    private int hour;
    private int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_date_cal);
        timePicker = (TimePicker) findViewById(R.id.timePickerStart);
        this.initCal();
    }

    private void initCal ()
    {
        CalendarView cv = (CalendarView) this.findViewById(R.id.calendarViewStart);
        cv.setFirstDayOfWeek(1);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                y = year;
                mon = month;
                d = dayOfMonth;
            }
        });
    }

    public void startButtonClicked(View v)
    {
        hour = timePicker.getCurrentHour();
        min = timePicker.getCurrentMinute();
        Calendar c = Calendar.getInstance();
        c.set(y, mon, d, hour, min);
        TimefulCore.theStartDate= c.getTime();
        TimefulCore.inprogressTask.setStart(TimefulCore.theStartDate);
        TimefulCore.inprogressTask.saveInBackground();
        Intent intent = new Intent(this,RepeatActivity.class);
        startActivity(intent);

    }
}
