package com.thinkful.timeful;


import android.app.FragmentManager;

import com.parse.ParseObject;
import com.parse.ParseUser;

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





}
