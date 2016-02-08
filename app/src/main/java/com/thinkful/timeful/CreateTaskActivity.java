package com.thinkful.timeful;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.ParseUser;
import android.widget.SeekBar;

import android.widget.SeekBar.OnSeekBarChangeListener;

import android.widget.TextView;

import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTaskActivity extends AppCompatActivity {


    private GoogleApiClient client;
    private Switch noteSwitch;
    private Context mContext;
    private RadioGroup mRadioGroup;
    private String taskSkill;
    private SeekBar diffucltySeekBar;
    private TextView diffExp;
    private TextView expText;
    private TextView skillText;
    private int exp;
    private int skillExp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        diffucltySeekBar = (SeekBar) findViewById(R.id.seekBarDiff);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.fitnessRadio) {
                    taskSkill = "fit";
                } else if (checkedId == R.id.socialRadio) {
                    taskSkill = "social";
                    System.out.println("Social WORKS");
                } else if (checkedId == R.id.careerRadio) {
                    taskSkill = "career";
                } else if (checkedId == R.id.hobbiesRadio) {
                    taskSkill = "hobbies";
                } else {
                    taskSkill = "n/a";
                }
            }
        });

        diffExp = (TextView) findViewById(R.id.diffSeekText);
        diffExp.setText(R.string.trivial);
        expText = (TextView) findViewById(R.id.expTextView);
        skillText = (TextView) findViewById(R.id.skillsText);
        diffucltySeekBar.setMax(50);
        diffucltySeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;


            @Override

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                progress = progresValue;
                diffExp.setText(diffSetting(progress));
                expText.setText(progress + "");
                skillText.setText(progress/4 + "");


            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                diffExp.setText(diffSetting(progress));
                expText.setText(progress + "");
                skillText.setText(progress/4 + "");


            }


            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {

                diffExp.setText(diffSetting(progress));
                expText.setText(progress + "");
                skillText.setText(progress/4 + "");
                exp = progress;
                skillExp = progress/4;

            }

        });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        this.noteSwitch = (Switch) this.findViewById(R.id.noteSwitch);
    }





    public void onCreateTaskButtonPushed(View v)
    {
        TextView taskNameTV = (TextView) this.findViewById(R.id.TaskName);
        TextView taskDesTV = (TextView) this.findViewById(R.id.TaskDescription);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null)
        {
            Tasks tasks = new Tasks();
            tasks.setUser(ParseUser.getCurrentUser());
            tasks.setName(taskNameTV.getText().toString());
            tasks.setDesript(taskDesTV.getText().toString());
            tasks.setCompleted(false);
            tasks.setSkill(taskSkill);
            tasks.setExp(exp);
            tasks.setSkillPoints(skillExp);
            tasks.setExpired(false);
            tasks.setCanceled(false);
             if (this.noteSwitch.isChecked())
             {
                 tasks.setNote("yes");

             }
            else
             {
                 tasks.setNote("no");
             }





            TimefulCore.inprogressTask = tasks;
            Log.i("TASK", tasks.getName());
            Intent aIntent = new Intent(this, StartDateCalActivity.class);
            aIntent.putExtra("Task", tasks);
            this.startActivityForResult(aIntent, 1);

        }
        else
        {
            // show the signup or login screen
        }




    }


    public String diffSetting(int exp)
    {
        if (exp <= 5)
        {
            return "Trivial";
        }
        else if (exp <= 20)
        {
            return "Easy";
        }
        else if (exp<= 35)
        {
            return "Normal";
        }
        else if (exp<= 45)
        {
            return "Hard";
        }
        else
        {
            return "Very Hard";
        }

    }





    public void backToMain() {
        super.onBackPressed();
    }



}
