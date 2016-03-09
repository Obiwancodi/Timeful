package com.thinkful.timeful;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private int nextNumber;
    private int newExp;




    private static class UpdateLay extends LinearLayoutManager {

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public UpdateLay(Context context)
        {
            super(context);
        }

        public UpdateLay(Context context, int orientation, boolean reverseLayout)
        {
            super(context,orientation,reverseLayout);
        }

        public UpdateLay(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
        {
            super(context,attrs,defStyleAttr,defStyleRes);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFrag();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        mLayoutManager = new UpdateLay(this);
       mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TimefulCore.staticAdapter = new TaskListAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(TimefulCore.staticAdapter);
        TimefulCore.currentLevel = (TextView) findViewById(R.id.currentText);
        TimefulCore.nextLevel = (TextView) findViewById(R.id.nextText);

        nextNumber = (int) TimefulCore.currentUser.get("level") + 1;
        TimefulCore.currentLevel.setText(TimefulCore.currentUser.get("level") + "");
        TimefulCore.nextLevel.setText(nextNumber + "");
        TimefulCore.dialogViewGroup = (ViewGroup) findViewById(android.R.id.content);
        TimefulCore.mLayoutInflater = getLayoutInflater();
        System.out.println(TimefulCore.dialogViewGroup);



      int userExp = (int) TimefulCore.currentUser.get("Exp");
        int userLevel = (int) TimefulCore.currentUser.get("level");

       TimefulCore.staticProgress = (ProgressBar) findViewById(R.id.expBar);

        if (userLevel == 0)
        {
            TimefulCore.staticProgress.setProgress(userExp);
        }
        else
        {
            System.out.println(userExp);
            newExp =  userExp - TimefulCore.levelList[userLevel - 1];
            System.out.println(newExp);
            TimefulCore.staticProgress.setProgress(newExp);
            System.out.println(TimefulCore.staticProgress.getProgress());
            TimefulCore.staticProgress.setMax(TimefulCore.expList[userLevel]);
        }
        TimefulCore.levelUp();
        TimefulCore.isExpired();

        TimefulCore.currentExp = (TextView) findViewById(R.id.expMainText);
        int exp = (int) TimefulCore.currentUser.get("Exp");
        TimefulCore.currentExp.setText(exp + "");

        TimefulCore.currentHob = (TextView) findViewById(R.id.hobTextView);
        int hobExp = (int) TimefulCore.currentUser.get("hobbies");
        TimefulCore.currentHob.setText(hobExp + "");

        TimefulCore.currentCar = (TextView) findViewById(R.id.carrerText);
        int carExp = (int) TimefulCore.currentUser.get("Career");
        TimefulCore.currentCar.setText(carExp + "");

        TimefulCore.currentFit = (TextView) findViewById(R.id.fitText);
        int fitExp = (int) TimefulCore.currentUser.get("Fit");
        TimefulCore.currentFit.setText(fitExp + "");

        TimefulCore.currentSocial = (TextView) findViewById(R.id.socialText);
        int socialExp = (int) TimefulCore.currentUser.get("Social");
        TimefulCore.currentSocial.setText(socialExp + "");




        System.out.println("ON CREATE!!!!!");



        Log.i("CurrentUserMain", "" + ParseUser.getCurrentUser().getObjectId());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTaskPush();


            }
        });







    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.mRecyclerView.requestLayout();
        TimefulCore.isExpired();
        int userExp = (int) TimefulCore.currentUser.get("Exp");
        int userLevel = (int) TimefulCore.currentUser.get("level");


            if (userLevel > 0) {

                newExp = userExp - TimefulCore.levelList[userLevel - 1];
                nextNumber = (int) TimefulCore.currentUser.get("level") + 1;
                TimefulCore.staticProgress.setProgress(newExp);
            }

        TimefulCore.levelUp();
        TimefulCore.currentLevel.setText(TimefulCore.currentUser.get("level") + "");
        TimefulCore.nextLevel.setText(nextNumber + "");

        if (TimefulCore.inprogressTask != null && TimefulCore.isSaved)
        {
            TimefulCore.staticAdapter.addTask(TimefulCore.inprogressTask);
            this.mRecyclerView.requestLayout();
            TimefulCore.staticAdapter.notifyDataSetChanged();
            if (userLevel == 0)

            {
                TimefulCore.staticProgress.setProgress(userExp);
                System.out.println(userExp);
            }

            else {

                TimefulCore.staticProgress.setProgress(newExp);
                TimefulCore.staticProgress.setMax(TimefulCore.expList[userLevel]);
            }
            TimefulCore.inprogressTask = null;
            TimefulCore.isSaved = false;
            System.out.println("ON RESUME!!!!!");




        }

        System.out.println("ON RESUME!!!!!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createTaskPush()
    {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        this.startActivity(intent);

    }






    public void getFrag()
    {
        TimefulCore.frag = getFragmentManager();


    }







}
