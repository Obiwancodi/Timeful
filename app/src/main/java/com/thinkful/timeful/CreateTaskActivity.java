package com.thinkful.timeful;

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

public class CreateTaskActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Switch noteSwitch;

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

    public void onCreateTaskButtonPushed(View v) {

        TextView taskNameTV = (TextView) this.findViewById(R.id.TaskName);
        TextView taskDesTV = (TextView) this.findViewById(R.id.TaskDescription);
        TextView taskDateTV = (TextView) this.findViewById(R.id.TaskDate);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null)
        {
            Tasks tasks = new Tasks();
            tasks.setUser(ParseUser.getCurrentUser());
            tasks.setName(taskNameTV.getText().toString());
            tasks.setDesript(taskDesTV.getText().toString());
             if (this.noteSwitch.isChecked())
             {
                 tasks.setNote("yes");
                 final ViewGroup rootFrameLayout = (ViewGroup) this.getWindow().peekDecorView();
                 LayoutInflater li = LayoutInflater.from(this);
                 View noteview = li.inflate(R.layout.switchonform, null);
                 LinearLayout placeHolder = (LinearLayout)this.findViewById(R.id.placeHolderLayout);
                 placeHolder.addView(noteview);
             }
            else
             {
                 tasks.setNote("no");
             }



            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            String myDate = taskDateTV.getText().toString();
            Log.i("DATE STRING", myDate);

            // Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(System.currentTimeMillis());

            // long milliseconds = calendar.getTimeInMillis();

            tasks.saveInBackground();
           this.backToMain();
        } else {
            // show the signup or login screen
        }

    }



    public void backToMain() {
        super.onBackPressed();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateTask Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.thinkful.timeful/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateTask Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.thinkful.timeful/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
