package com.thinkful.timeful;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import java.util.Date;
import java.util.Calendar;

public class DailyWeeklyMonthyDialog extends DialogFragment{

    private Date date;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        System.out.println("IS THIS CREATING");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.repeat_task_dialog)
                .setPositiveButton(R.string.daily, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       date = TimefulCore.inprogressTask.getEnd();
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.DATE, 1);
                        System.out.println(TimefulCore.inprogressTask.getEnd());
                        date = c.getTime();
                        TimefulCore.inprogressTask.setRepeat(date);
                        TimefulCore.inprogressTask.setReType("day");
                        TimefulCore.inprogressTask.saveInBackground();
                        Intent intent = new Intent(TimefulCore.mContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);




                    }
                })

                .setNeutralButton(R.string.weekly, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        date = TimefulCore.inprogressTask.getEnd();
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.DATE, 7);
                        System.out.println(TimefulCore.inprogressTask.getEnd());
                        date = c.getTime();
                        TimefulCore.inprogressTask.setRepeat(date);
                        TimefulCore.inprogressTask.setReType("week");
                        TimefulCore.inprogressTask.saveInBackground();
                        Intent intent = new Intent(TimefulCore.mContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                })


                .setNegativeButton(R.string.monthly, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        date = TimefulCore.inprogressTask.getEnd();
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.MONTH, 1);
                        date = c.getTime();
                        TimefulCore.inprogressTask.setRepeat(date);
                        TimefulCore.inprogressTask.setReType("month");
                        TimefulCore.inprogressTask.saveInBackground();
                        Intent intent = new Intent(TimefulCore.mContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                });
        // Create the AlertDialog object and return it
        return  builder.create();


    }

}
