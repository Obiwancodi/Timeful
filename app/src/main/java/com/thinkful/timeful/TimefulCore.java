package com.thinkful.timeful;


import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimefulCore
{
    static Tasks inprogressTask = null;
    static boolean isSaved = false;

    static ParseUser currentUser = null;

    static int thing = 0;

    static List<ParseObject> userTasks;

    static FragmentManager frag;

    static  TaskListAdapter staticAdapter;

    static ProgressBar staticProgress;

    static Context mContext; // for TimefulCalActivity context

    static Date theDate;

    static Date theStartDate;

    static TextView currentLevel;

    static TextView nextLevel;

    static int[] levelList = {100, 216, 351, 508, 691, 904, 1152, 1441, 1778, 2171, 2629, 3163, 3786,
            4512, 5359, 6347, 7499, 8843, 10411, 12240, 14373, 16861, 19763, 23148, 27097, 31704, 37078,
            43347, 50660, 59191, 69143, 80753, 94298, 110100, 128535, 150042, 175133, 204405, 238555, 278396,
            324877, 379104, 442368, 516176, 602285, 702745, 819948, 956684, 1116209, 1302321};

    static int[] expList = {100, 116, 135, 157, 183, 213, 248, 289, 337, 393, 458, 534, 623, 726, 847, 988,
            1152, 1344, 1568, 1829, 2133, 2488, 2902, 3385, 3949, 4607, 5374, 6269, 7313, 8531, 9952, 11610,
            13545, 15802, 18435, 21507, 25091, 29272, 34150, 39841, 46481, 54227, 63264, 73808, 86109, 100460,
            117203, 136736, 159525, 186112};

   public FragmentManager fragForExpired;

    static public void levelUp()
    {
        int userLevel = (int) TimefulCore.currentUser.get("level");
        int userExp = (int) TimefulCore.currentUser.get("Exp");


        if (TimefulCore.levelList[userLevel] <= userExp)
        {
            TimefulCore.staticProgress.setProgress(userExp - TimefulCore.levelList[userLevel]);
            TimefulCore.currentUser.put("level", userLevel + 1);
            TimefulCore.currentUser.saveInBackground();
        }

        else if (TimefulCore.levelList[userLevel -1] > userExp)
        {
            TimefulCore.currentUser.put("level", userLevel - 1);
            TimefulCore.currentUser.saveInBackground();
            TimefulCore.staticProgress.setProgress(userExp - TimefulCore.levelList[userLevel]);
            TimefulCore.staticProgress.setMax(TimefulCore.expList[userLevel - 1]);
        }

        //TimefulCore.staticProgress.setMax(TimefulCore.expList[userLevel]);

    }


    static public void isExpired()

    {
        Calendar expriedDated = Calendar.getInstance();
        Calendar now = Calendar.getInstance();


        for (int i = 0; i < TimefulCore.userTasks.size(); i++)
        {
            Tasks aTask = (Tasks) TimefulCore.userTasks.get(i);
            expriedDated.setTime(aTask.getEnd());

            if (now.after(expriedDated))
            {
                aTask.setExpired(true);
               int exp = aTask.getExp();
                exp = (exp)/(4);
               ParseUser user =   aTask.getUser();
               int userExp =  (int) user.get("Exp");
                userExp = userExp - exp;
                user.put("Exp", userExp);
                aTask.saveInBackground();
                user.saveInBackground();
                TimefulCore.userTasks.remove(aTask);
                TimefulCore.staticAdapter.notifyDataSetChanged();
               TimefulCore.showDialog();


            }


        }


    }


    static public void showDialog()
    {

        ExpiredTaskDialog dialog = new ExpiredTaskDialog();
        dialog.show(TimefulCore.frag, "DIALOG");


    }


    @UiThread
    static void updateData(TaskListAdapter taskListAdapter)
    {
        taskListAdapter.notifyDataSetChanged();
    }

    static void removeData(Tasks tasks)
    {
        TimefulCore.userTasks.remove(tasks);
    }




}
