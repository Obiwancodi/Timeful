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
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTaskActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Switch noteSwitch;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
             if (this.noteSwitch.isChecked())
             {
                 tasks.setNote("yes");
                 /*
                 final ViewGroup rootFrameLayout = (ViewGroup) this.getWindow().peekDecorView();
                 LayoutInflater li = LayoutInflater.from(this);
                 View noteview = li.inflate(R.layout.switchonform, null);
                 LinearLayout placeHolder = (LinearLayout)this.findViewById(R.id.placeHolderLayout);
                 placeHolder.addView(noteview);
                 */
             }
            else
             {
                 tasks.setNote("no");
             }





            TimefulCore.inprogressTask = tasks;
            Log.i("TASK", tasks.getName());
            Intent aIntent = new Intent(this, TimefulCalActivity.class);
            aIntent.putExtra("Task", tasks);
            this.startActivityForResult(aIntent, 1);

        }
        else
        {
            // show the signup or login screen
        }




    }





    public void backToMain() {
        super.onBackPressed();
    }



}
