package com.thinkful.timeful;

import android.app.Dialog;
import android.app.DialogFragment;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bolts.Task;

public class TimefulCalActivity extends AppCompatActivity
{
    private Tasks tasks;
    private TimePicker timePicker;
    private int y;
    private int mon;
    private int d;
    private int hour;
    private int min;
    private View v;
    private List userTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeful_cal);
       // queryTasks(v);
        queryUserTasks(v);
        this.initCal();
        timePicker = (TimePicker) findViewById(R.id.timePicker);

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
/*
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    }
*/
    public void finishButtonClicked(View v)
    {
        hour = timePicker.getCurrentHour();
        min = timePicker.getCurrentMinute();
        Calendar c = Calendar.getInstance();
        c.set(y,mon,d,hour,min);
        Date theDate = c.getTime();
        tasks.setEnd(theDate);
        tasks.saveInBackground();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

  /*  public void queryTasks(View v) {

        ParseQuery<ParseObject> taskQuery = ParseQuery.getQuery("Tasks");
        taskQuery.whereEqualTo("createdBy", ParseUser.getCurrentUser());

        taskQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> taskList, ParseException e) {
                if (e == null) {
                    Log.i("tasks", "Retrieved " + taskList.size() + " tasks");
                    userTasks = taskList;

                } else {
                    Log.i("tasks", "Error: " + e.getMessage());
                }
            }
        });


    }
    */

    public void queryUserTasks(View v)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
        query.orderByDescending("updatedAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.i("ERROR", "IT DID NOT WORK");
                } else {
                    tasks = (Tasks)object;
                }
            }
        });


    }
}
