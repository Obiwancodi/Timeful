package com.thinkful.timeful;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseObject;


public class CompleteTaskDialog extends DialogFragment {

   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.complete_task_dialog,null);
    }
    */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        System.out.println("IS THIS CREATING");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.complete_task_dialog)
                .setPositiveButton(R.string.complete_task_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ParseObject task = TimefulCore.userTasks.get(TimefulCore.thing);
                        Tasks realTask = (Tasks) task;
                        realTask.setCompleted(true);
                        realTask.saveInBackground();
                        TimefulCore.userTasks.remove(realTask);
                        TimefulCore.currentUser.put("Exp", 15 + TimefulCore.currentUser.getInt("Exp"));
                        TimefulCore.currentUser.saveInBackground();
                        TimefulCore.isSaved = true;
                        TimefulCore.inprogressTask = realTask;
                        System.out.println(realTask.getCompleted());
                        TimefulCore.staticAdapter.notifyDataSetChanged();
                        TimefulCore.staticProgress.setProgress(TimefulCore.currentUser.getInt("Exp"));




                    }
                })
                .setNegativeButton(R.string.complete_task_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return  builder.create();


    }
}

