package com.thinkful.timeful;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bolts.Task;

public class TimefulCalActivity extends AppCompatActivity
{
    private TimePicker timePicker;
    private int y;
    private int mon;
    private int d;
    private int hour;
    private int min;
    private List userTasks;
    FragmentManager frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeful_cal);
        this.initCal();
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        TimefulCore.mContext =this;

    }

   private void initCal ()
   {
       CalendarView cv = (CalendarView) this.findViewById(R.id.calendarView);
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

    public void finishButtonClicked(View v)
    {
        hour = timePicker.getCurrentHour();
        min = timePicker.getCurrentMinute();
        Calendar c = Calendar.getInstance();
        c.set(y, mon, d, hour, min);
        TimefulCore.theDate= c.getTime();
        TimefulCore.inprogressTask.setEnd(TimefulCore.theDate);
        TimefulCore.inprogressTask.saveInBackground();
        TimefulCore.isSaved = true;
        showDialog();
    }


    public void showDialog()
    {
        frag  = getFragmentManager();
        RepeatingTaskDialog dialog = new RepeatingTaskDialog();
        dialog.show(frag, "DIALOG");


    }


}
