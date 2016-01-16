package com.thinkful.timeful;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseObject;

public class RepeatingTaskDialog extends DialogFragment {

    FragmentManager frag;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        System.out.println("IS THIS CREATING");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.repeat_task_dialog)
                .setPositiveButton(R.string.complete_task_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*TimefulCore.inprogressTask.setEnd(TimefulCore.theDate);
                        TimefulCore.inprogressTask.saveInBackground();
                        TimefulCore.isSaved = true;
                        */
                        showDialog();




                    }
                })
                .setNegativeButton(R.string.complete_task_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(TimefulCore.mContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                });
        // Create the AlertDialog object and return it
        return  builder.create();


    }


    public void showDialog()
    {
        frag  = getFragmentManager();
        DailyWeeklyMonthyDialog dialog = new DailyWeeklyMonthyDialog();
        dialog.show(frag, "DIALOG");


    }


}
