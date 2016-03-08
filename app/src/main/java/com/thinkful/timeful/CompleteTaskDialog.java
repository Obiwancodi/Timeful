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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;


public class CompleteTaskDialog extends DialogFragment {

    Date date;
    private String skillType;
    private int nextNumber;
    private int newExp;
    private int userExp;


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
                        System.out.println(realTask.getName());
                        realTask.setCompleted(true);
                        realTask.saveInBackground();
                        realTask.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    TimefulCore.updateData(TimefulCore.staticAdapter);
                                }
                            }
                        });
                        System.out.println(TimefulCore.userTasks.size());
                        TimefulCore.removeData(realTask);
                        System.out.println(TimefulCore.userTasks.size());
                        TimefulCore.updateData(TimefulCore.staticAdapter);
                        TimefulCore.currentUser.put("Exp", realTask.getExp() + TimefulCore.currentUser.getInt("Exp"));
                       skillType =  realTask.getSkill();

                        if (skillType.equals("fit"))
                        {
                            TimefulCore.currentUser.put("Fit", realTask.getSkillPoints() + TimefulCore.currentUser.getInt("Fit"));
                        }

                        else if (skillType.equals("social"))
                        {
                            TimefulCore.currentUser.put("Social", realTask.getSkillPoints() + TimefulCore.currentUser.getInt("Social"));
                        }

                        else if (skillType.equals("career"))
                        {
                            TimefulCore.currentUser.put("Career", realTask.getSkillPoints() + TimefulCore.currentUser.getInt("Career"));
                        }

                        else if (skillType.equals("hobbies"))
                        {
                            TimefulCore.currentUser.put("hobbies", realTask.getSkillPoints() + TimefulCore.currentUser.getInt("hobbies"));
                        }

                        TimefulCore.currentUser.saveInBackground();
                        TimefulCore.isSaved = true;
                       // TimefulCore.inprogressTask = realTask;
                        System.out.println(realTask.getCompleted());
                       // TimefulCore.staticProgress.setProgress(TimefulCore.currentUser.getInt("Exp"));

                        if (realTask.getRepeat() != null)
                        {

                            Tasks tasks = new Tasks();
                            tasks.setUser(ParseUser.getCurrentUser());
                            tasks.setName(realTask.getName());
                            tasks.setDesript(realTask.getDesript());
                            // tasks.setNote(realTask.getNote());
                            tasks.setCompleted(false);
                            tasks.setSkill(realTask.getSkill());
                            tasks.setEnd(realTask.getRepeat());
                            tasks.setSkillPoints(realTask.getSkillPoints());
                            tasks.setExp(realTask.getExp());
                            String type = realTask.getReType();
                            tasks.setCanceled(false);
                            tasks.setExpired(false);
                            tasks.setEdited(false);

                            if (type.equals("day"))
                            {
                                date = realTask.getRepeat();
                                Calendar c = Calendar.getInstance();
                                c.setTime(date);
                                c.add(Calendar.DATE, 1);
                                date = c.getTime();
                                tasks.setRepeat(date);
                                tasks.setReType("day");
                            }

                            else if (type.equals("week"))
                            {
                                date =realTask.getRepeat();
                                Calendar c = Calendar.getInstance();
                                c.setTime(date);
                                c.add(Calendar.DATE, 7);
                                date = c.getTime();
                                tasks.setRepeat(date);
                                tasks.setReType("week");
                            }

                            else if (type.equals("month"))
                            {
                                date =realTask.getRepeat();
                                Calendar c = Calendar.getInstance();
                                c.setTime(date);
                                c.add(Calendar.MONTH, 1);
                                date = c.getTime();
                                tasks.setRepeat(date);
                                tasks.setReType("month");

                            }

                            tasks.saveInBackground();

                            TimefulCore.updateData(TimefulCore.staticAdapter);

                            TimefulCore.staticAdapter.addTask(tasks);
                           // TimefulCore.userTasks.add(tasks);

                            TimefulCore.levelUp();
                        }

                        else
                        {
                            TimefulCore.updateData(TimefulCore.staticAdapter);
                            TimefulCore.levelUp();
                        }
                        userExp = (int) TimefulCore.currentUser.get("Exp");
                        nextNumber = (int) TimefulCore.currentUser.get("level");
                        if (userExp < 100)

                        {
                            TimefulCore.staticProgress.setProgress(userExp);
                            TimefulCore.nextLevel.setText("" + 1);
                        }

                        else
                        {

                            newExp =  userExp - TimefulCore.levelList[nextNumber - 1];
                            nextNumber = (int) TimefulCore.currentUser.get("level") + 1;
                            TimefulCore.staticProgress.setProgress(newExp);
                            TimefulCore.nextLevel.setText(nextNumber + "");
                        }


                        TimefulCore.levelUp();
                        TimefulCore.currentLevel.setText(TimefulCore.currentUser.get("level") + "");


                        int exp = (int) TimefulCore.currentUser.get("Exp");
                        TimefulCore.currentExp.setText(exp + "");

                        int hobExp = (int) TimefulCore.currentUser.get("hobbies");
                        TimefulCore.currentHob.setText(hobExp + "");

                        int carExp = (int) TimefulCore.currentUser.get("Career");
                        TimefulCore.currentCar.setText(carExp + "");

                        int fitExp = (int) TimefulCore.currentUser.get("Fit");
                        TimefulCore.currentFit.setText(fitExp + "");

                        int socialExp = (int) TimefulCore.currentUser.get("Social");
                        TimefulCore.currentSocial.setText(socialExp + "");

                    }
                });

             /*   builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                */


        builder.setNeutralButton(R.string.edit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

               builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ParseObject task = TimefulCore.userTasks.get(TimefulCore.thing);
                Tasks realTask = (Tasks) task;
                realTask.setCanceled(true);
                int uExp = (int) TimefulCore.currentUser.get("Exp");
                int tExp = (realTask.getExp())/4;
                uExp = uExp - tExp;
                TimefulCore.currentUser.put("Exp", uExp);
                TimefulCore.currentUser.saveInBackground();
                realTask.saveInBackground();
                TimefulCore.userTasks.remove(realTask);
                TimefulCore.levelUp();
                userExp = (int) TimefulCore.currentUser.get("Exp");

                if (userExp < 100)
                {
                    TimefulCore.staticProgress.setProgress(userExp);

                }
                else
                {
                    newExp = userExp - TimefulCore.levelList[(int) TimefulCore.currentUser.get("level") - 1];
                    System.out.println(newExp);
                    TimefulCore.staticProgress.setProgress(newExp);
                }
                nextNumber =  (int) TimefulCore.currentUser.get("level") + 1;
                TimefulCore.currentLevel.setText(TimefulCore.currentUser.get("level") + "");
                TimefulCore.nextLevel.setText(nextNumber + "");
                TimefulCore.currentExp.setText(userExp + "");
                TimefulCore.updateData(TimefulCore.staticAdapter);

            }
        });


        // Create the AlertDialog object and return it
        return  builder.create();


    }
}

