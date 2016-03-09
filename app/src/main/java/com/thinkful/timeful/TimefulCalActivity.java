package com.thinkful.timeful;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import butterknife.Bind;
import butterknife.ButterKnife;
public class TimefulCalActivity extends AppCompatActivity
{
    private int y = 0;
    private int mon;
    private int d;
    private int hour;
    private int min;
    private RadioGroup repeatGroup;
    private Date date;
    private long mili;
    private boolean repeat = false;



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
                    repeat = true;

                } else if (checkedId == R.id.repeatDay) {
                    TimefulCore.inprogressTask.setReType("day");
                    TimefulCore.inprogressTask.saveInBackground();
                    repeat = true;
                }
                else if (checkedId == R.id.repeatWeek) {
                    TimefulCore.inprogressTask.setReType("week");
                    TimefulCore.inprogressTask.saveInBackground();
                    repeat = true;

                } else if (checkedId == R.id.repeatMonth) {

                    TimefulCore.inprogressTask.setReType("month");
                    TimefulCore.inprogressTask.saveInBackground();
                    repeat = true;
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

    public void finishButtonClicked(View v)
    {
        if (!repeat)
        {
            Toast notRepeat = Toast.makeText(getApplicationContext(),"Please Select Repeat Option",Toast.LENGTH_SHORT);
            notRepeat.show();
        }
        else {
            Calendar c = Calendar.getInstance();
            hour = 23;
            min = 59;

            c.set(y, mon, d, hour, min);

            TimefulCore.theDate = c.getTime();
            TimefulCore.inprogressTask.setEnd(TimefulCore.theDate);
            date = TimefulCore.theDate;

            Calendar cc = Calendar.getInstance();
            cc.setTime(date);
            System.out.println(TimefulCore.inprogressTask.getReType());

            if (TimefulCore.inprogressTask.getReType().equals("day")) {
                cc.add(Calendar.DATE, 1);
                date = cc.getTime();
                TimefulCore.inprogressTask.setRepeat(date);
            } else if (TimefulCore.inprogressTask.getReType().equals("week")) {
                cc.add(Calendar.DATE, 7);
                date = cc.getTime();
                TimefulCore.inprogressTask.setRepeat(date);
            } else if (TimefulCore.inprogressTask.getReType().equals("month")) {
                cc.add(Calendar.MONTH, 1);
                date = cc.getTime();
                TimefulCore.inprogressTask.setRepeat(date);
            }

            TimefulCore.inprogressTask.saveInBackground();
            TimefulCore.isSaved = true;
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }


}
