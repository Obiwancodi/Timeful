package com.thinkful.timeful;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

public class StartDateCalActivity extends AppCompatActivity {


    private int y;
    private int mon;
    private int d;
    private int hour;
    private int min;
    private RadioGroup repeatGroup;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_date_cal);

        RadioButton never = (RadioButton) findViewById(R.id.repeatNeverEdit);
        RadioButton day = (RadioButton) findViewById(R.id.repeatDayEdit);
        RadioButton week = (RadioButton) findViewById(R.id.repeatWeekEdit);
        RadioButton month = (RadioButton) findViewById(R.id.repeatMonthEdit);


        repeatGroup =  (RadioGroup) findViewById(R.id.radioGroupCompleteEdit);
        if (TimefulCore.inprogressTask.getReType().equals("Never"))
        {
           never.setChecked(true);
        }

        else if (TimefulCore.inprogressTask.getReType().equals("day"))
        {
            day.setChecked(true);
        }

        else if (TimefulCore.inprogressTask.getReType().equals("week"))
        {
            week.setChecked(true);
        }

        else if (TimefulCore.inprogressTask.getReType().equals("month"))
        {
            month.setChecked(true);
        }
        repeatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.repeatNeverEdit) {
                    TimefulCore.inprogressTask.setReType("Never");
                    TimefulCore.inprogressTask.saveInBackground();

                } else if (checkedId == R.id.repeatDayEdit) {
                    TimefulCore.inprogressTask.setReType("day");
                    TimefulCore.inprogressTask.saveInBackground();
                }
                else if (checkedId == R.id.repeatWeekEdit) {
                    TimefulCore.inprogressTask.setReType("week");
                    TimefulCore.inprogressTask.saveInBackground();

                } else if (checkedId == R.id.repeatMonthEdit) {

                    TimefulCore.inprogressTask.setReType("month");
                    TimefulCore.inprogressTask.saveInBackground();
                } else {

                }
            }
        });
        this.initCal();
        TimefulCore.mContext =this;


    }

    private void initCal ()
    {
        CalendarView cv = (CalendarView) this.findViewById(R.id.calendarViewEdit);
        cv.setFirstDayOfWeek(1);
        long lDate = TimefulCore.editTask.getEnd().getTime();
        cv.setDate(lDate,false,true);
        Calendar x = Calendar.getInstance();
        date = x.getTime();
        if(android.os.Build.VERSION.SDK_INT >= 22) {
            cv.setMinDate(x.getTimeInMillis());
        }


        Calendar start = Calendar.getInstance();
        y = start.get(Calendar.YEAR);
        mon = start.get(Calendar.MONTH);
        d = start.get(Calendar.DAY_OF_MONTH);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                y = year;
                mon = month;
                d = dayOfMonth;
            }
        });
    }

    public void finishButtonClickedEdit(View v)
    {
        Calendar c = Calendar.getInstance();
        hour = 23;
        min = 59;

        c.set(y, mon, d, hour, min);

        TimefulCore.theDate= c.getTime();
        TimefulCore.inprogressTask.setEnd(TimefulCore.theDate);
        date = TimefulCore.theDate;

        Calendar cc = Calendar.getInstance();
        cc.setTime(date);
        System.out.println(TimefulCore.inprogressTask.getReType());

        if (TimefulCore.inprogressTask.getReType().equals("day"))
        {
            cc.add(Calendar.DATE, 1);
            date = cc.getTime();
            TimefulCore.inprogressTask.setRepeat(date);
        }

        else if (TimefulCore.inprogressTask.getReType().equals("week"))
        {
            cc.add(Calendar.DATE, 7);
            date = cc.getTime();
            TimefulCore.inprogressTask.setRepeat(date);
        }

        else if (TimefulCore.inprogressTask.getReType().equals("month"))
        {
            cc.add(Calendar.MONTH,1);
            date = cc.getTime();
            TimefulCore.inprogressTask.setRepeat(date);
        }

        TimefulCore.inprogressTask.saveInBackground();
        TimefulCore.isSaved = true;

        TimefulCore.editTask.setEdited(true);
        TimefulCore.editTask.saveInBackground();
        TimefulCore.userTasks.remove(TimefulCore.editTask);


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
