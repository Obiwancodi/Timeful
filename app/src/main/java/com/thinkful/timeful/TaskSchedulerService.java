package com.thinkful.timeful;

import android.util.Log;


import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;


/**
 * Service used for receiving calls when tasks scheduled through
 * {@link com.google.android.gms.gcm.GcmNetworkManager} are executed.
 */
public class TaskSchedulerService extends GcmTaskService {

    private LoggingService.Logger mLogger;

    public TaskSchedulerService() {
        mLogger = new LoggingService.Logger(this);
    }

    @Override
    public int onRunTask(TaskParams params) {
        String tag = params.getTag();
        TaskCollection tasks = TaskCollection.getInstance(this);
        TaskTracker task = tasks.getTask(tag);
        if (task != null) {
            task.execute(mLogger);
            tasks.updateTask(task);
        } else {
            mLogger.log(Log.ERROR, "Could not find task with tag " + tag);
            task = TaskTracker.emptyTaskWithTag(tag);
            task.execute(mLogger);
            tasks.updateTask(task);
        }
        return GcmNetworkManager.RESULT_SUCCESS;

    }

}
