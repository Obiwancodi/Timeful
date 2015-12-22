package com.thinkful.timeful;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import bolts.Task;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>
{


    private Context mContext;
    private RecyclerView mRecyclerView;
  //  private View v;
    private List<ParseObject> userTasks;
    private Tasks task;
    private int thing;

    public TaskListAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        queryTasks();

    }


    public void addTask(Tasks task)
    {
        this.userTasks.add(task);
    }


    public void queryTasks() {

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


    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.tasks_list, viewGroup, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mRecyclerView.getChildLayoutPosition(v));
              int position =  mRecyclerView.getChildLayoutPosition(v);
                ParseObject task = userTasks.get(position);
                Tasks realTask = (Tasks) task;
                realTask.deleteInBackground();
                notifyItemRemoved(position);
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.ViewHolder viewHolder, int i) {
        ParseObject task = userTasks.get(i);
        Tasks realTask = (Tasks) task;
        viewHolder.setText(realTask.getName());
    }

    @Override
    public int getItemCount() {
        if (userTasks != null) {
            return userTasks.size();
        }
        else
        {
            return 0;
        }

    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        public void setText(String text) {
            this.text.setText(text);
        }
    }


}
