package com.thinkful.timeful;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.NumberPicker.OnValueChangeListener;
import java.util.Calendar;
import java.util.Date;
import android.content.Intent;

public class RepeatActivity extends AppCompatActivity {

    private RadioGroup repeatGroup;
    private NumberPicker mNumberPicker;
    private Date date;
    private int endNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        repeatGroup = (RadioGroup) findViewById(R.id.radioGroupRepeat);
        mNumberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        mNumberPicker.setEnabled(false);
        repeatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonNever) {
                    mNumberPicker.setEnabled(true);
                    mNumberPicker.setMaxValue(364);
                    TimefulCore.inprogressTask.setReType("Never");
                    TimefulCore.inprogressTask.saveInBackground();

                } else if (checkedId == R.id.radioButtonDaily) {
                    mNumberPicker.setEnabled(false);
                    TimefulCore.inprogressTask.setReType("day");
                    TimefulCore.inprogressTask.saveInBackground();
                }
                else if (checkedId == R.id.radioButtonWeekly) {

                    mNumberPicker.setEnabled(true);
                    mNumberPicker.setMaxValue(7);
                    TimefulCore.inprogressTask.setReType("week");
                    TimefulCore.inprogressTask.saveInBackground();

                } else if (checkedId == R.id.radioButtonMonthly) {
                    mNumberPicker.setEnabled(true);
                    mNumberPicker.setMaxValue(27);
                    TimefulCore.inprogressTask.setReType("month");
                    TimefulCore.inprogressTask.saveInBackground();
                } else {

                }
            }
        });

        mNumberPicker.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endNumber = newVal;

            }


        });
    }


    public void repeatSubmimit(View view)
    {
        date = TimefulCore.inprogressTask.getStart();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, endNumber);
        date = c.getTime();
        TimefulCore.inprogressTask.setEnd(date);

        Calendar cc = Calendar.getInstance();
        cc.setTime(date);

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
        TimefulCore.inprogressTask.saveInBackground();
        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
    }


}



