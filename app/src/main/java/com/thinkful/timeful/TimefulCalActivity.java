package com.thinkful.timeful;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimefulCalActivity extends AppCompatActivity
{
    private TimePicker timePicker;
    private int y = 0;
    private int mon;
    private int d;
    private int hour;
    private int min;
    private List userTasks;
    FragmentManager frag;
    private RadioGroup repeatGroup;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeful_cal);
        repeatGroup =  (RadioGroup) findViewById(R.id.radioGroupComplete);
        repeatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.repeatNever) {
                    TimefulCore.inprogressTask.setReType("Never");
                    TimefulCore.inprogressTask.saveInBackground();

                } else if (checkedId == R.id.repeatDay) {
                    TimefulCore.inprogressTask.setReType("day");
                    TimefulCore.inprogressTask.saveInBackground();
                }
                else if (checkedId == R.id.repeatWeek) {
                    TimefulCore.inprogressTask.setReType("week");
                    TimefulCore.inprogressTask.saveInBackground();

                } else if (checkedId == R.id.repeatMonth) {

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
       CalendarView cv = (CalendarView) this.findViewById(R.id.calendarView);
       cv.setFirstDayOfWeek(1);
       Calendar x = Calendar.getInstance();
       date = x.getTime();
       long lonDate = date.getTime();
       cv.setMinDate(lonDate);
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

    public void finishButtonClicked(View v)
    {
        Calendar c = Calendar.getInstance();
        hour = 23;
        min = 59;

        c.set(y, mon, d, hour, min);

        TimefulCore.theDate= c.getTime();
        TimefulCore.inprogressTask.setEnd(TimefulCore.theDate);
        TimefulCore.inprogressTask.saveInBackground();
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

        TimefulCore.isSaved = true;
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }





}
