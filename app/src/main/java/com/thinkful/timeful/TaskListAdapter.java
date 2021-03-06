package com.thinkful.timeful;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bolts.Task;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>
{


    private Context mContext;
    private RecyclerView mRecyclerView;
    private Tasks task;
    private int thing;



    public TaskListAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
       queryTasks();

    }


    public void addTask(Tasks task)
    {
        TimefulCore.userTasks.add(task);
    }




    public void queryTasks() {

        ParseQuery<ParseObject> taskQuery = ParseQuery.getQuery("Tasks");
        taskQuery.whereEqualTo("createdBy", ParseUser.getCurrentUser());
        taskQuery.whereEqualTo("Completed", false);
        taskQuery.whereEqualTo("expired", false);
        taskQuery.whereEqualTo("canceled", false);
        taskQuery.whereEqualTo("edited", false);

        try
        {
          TimefulCore.userTasks = taskQuery.find();
        }
        catch (ParseException e)
        {
            TimefulCore.userTasks = new LinkedList<ParseObject>();
        }

    }


    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.task_list, viewGroup, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mRecyclerView.getChildLayoutPosition(v));
                TimefulCore.thing = mRecyclerView.getChildLayoutPosition(v);
                ParseObject task = TimefulCore.userTasks.get(TimefulCore.thing);
                TimefulCore.inprogressTask = (Tasks) task;
                showDialog();


            }

        });


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.ViewHolder viewHolder, int i) {
       //queryTasks();
        System.out.println(TimefulCore.userTasks.size());
        System.out.println(i);
        ParseObject task = TimefulCore.userTasks.get(i);
        System.out.println(TimefulCore.userTasks.size());
        Tasks realTask = (Tasks) task;
        viewHolder.setText(realTask.getName(),realTask.getEnd(),realTask.getDesript(), realTask.getExp(), realTask.getSkill(), realTask.getSkillPoints());
    }

    @Override
    public int getItemCount() {
        if (TimefulCore.userTasks != null) {
            return TimefulCore.userTasks.size();
        }
        else
        {
            return 0;
        }

    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView dueText;
        private TextView desText;
        private TextView expText;
        private TextView skillText;
        private TextView skillExp;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.nameText);
            dueText = (TextView) itemView.findViewById(R.id.dueDateText);
            desText = (TextView) itemView.findViewById(R.id.itemDes);
            expText = (TextView) itemView.findViewById(R.id.taskExp);
            skillText = (TextView) itemView.findViewById(R.id.taskSkill);
            skillExp = (TextView) itemView.findViewById(R.id.taskSkillPoints);
        }

        public void setText(String taskName,Date dueDate,String taskDes,int exp, String skill, int skexp) {
            this.nameText.setText(taskName);
            DateFormat[] formats = new DateFormat[] {
                    DateFormat.getDateInstance(),
                    DateFormat.getDateTimeInstance(),
                    DateFormat.getTimeInstance(),
            };
            this.dueText.setText(formats[1].format(dueDate));
            this.desText.setText(taskDes);
            this.expText.setText(exp + "");
            this.skillText.setText(skill);
            this.skillExp.setText(skexp + "");

        }
    }


    public void showDialog()
    {
        CompleteTaskDialog dialog = new CompleteTaskDialog();
        TimefulCore.mDialog = dialog;
        dialog.show(TimefulCore.frag, "DIALOG");


    }










    }



