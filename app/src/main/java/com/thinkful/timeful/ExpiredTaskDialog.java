package com.thinkful.timeful;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseObject;


public class ExpiredTaskDialog extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String taskName = TimefulCore.expiredTask.getName();
        builder.setMessage(taskName + "has expired");


        return  builder.create();


    }
}
