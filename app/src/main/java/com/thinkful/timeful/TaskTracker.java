package com.thinkful.timeful;

import android.os.SystemClock;
import android.util.Log;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.thinkful.timeful.Constants.CANCELLED;
import static com.thinkful.timeful.Constants.CREATED_AT_ELAPSED_SECS;
import static com.thinkful.timeful.Constants.EXECUTED;
import static com.thinkful.timeful.Constants.FLEX;
import static com.thinkful.timeful.Constants.PERIOD;
import static com.thinkful.timeful.Constants.TAG;
import static com.thinkful.timeful.Constants.WINDOW_START_ELAPSED_SECS;
import static com.thinkful.timeful.Constants.WINDOW_STOP_ELAPSED_SECS;


/**
 * Keep track of task so when it is sent back we can ensure correct execution criteria.
 */
public class TaskTracker {

    // TaskTracker parameters.
    public final String tag;
    public final long windowStartElapsedSecs;
    public final long windowStopElapsedSecs;
    public final long period;
    public final long flex;
    public final long createdAtElapsedSecs;
    // TaskTracker state.
    private boolean cancelled;
    private boolean executed;

    /**
     * Each time this task's {@link #execute} is called we save the information here
     */
    public final List<Long> executionTimes;

    public static TaskTracker createPeriodic(String tag, long periodSecs, long flexSecs) {
        return new TaskTracker(tag, periodSecs, flexSecs, 0L, 0L,
                SystemClock.elapsedRealtime() / 1000);
    }

    public static TaskTracker createOneoff(String tag, long windowStartSecs, long windowEndSecs) {
        return new TaskTracker(tag, 0L, 0L, windowStartSecs, windowEndSecs,
                SystemClock.elapsedRealtime() / 1000);
    }

    static public TaskTracker fromJson(JSONObject jsonObject) throws JSONException {
        final String tag = jsonObject.getString(TAG);
        final long windowStartElapsedSecs = jsonObject.optLong(WINDOW_START_ELAPSED_SECS);
        final long windowStopElapsedSecs = jsonObject.optLong(WINDOW_STOP_ELAPSED_SECS);
        final long period = jsonObject.optLong(PERIOD);
        final long flex = jsonObject.optLong(FLEX);
        final long createdAtElapsedSecs = jsonObject.optLong(CREATED_AT_ELAPSED_SECS);
        boolean cancelled = jsonObject.optBoolean(CANCELLED);
        boolean executed = jsonObject.optBoolean(EXECUTED);
        TaskTracker task = new TaskTracker(tag, period, windowStartElapsedSecs,
                windowStopElapsedSecs, flex, createdAtElapsedSecs);
        task.cancelled = cancelled;
        task.executed = executed;
        return task;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TAG, tag);
        jsonObject.put(WINDOW_START_ELAPSED_SECS, windowStartElapsedSecs);
        jsonObject.put(WINDOW_STOP_ELAPSED_SECS, windowStopElapsedSecs);
        jsonObject.put(PERIOD, period);
        jsonObject.put(FLEX, flex);
        jsonObject.put(CREATED_AT_ELAPSED_SECS, createdAtElapsedSecs);
        jsonObject.put(CANCELLED, cancelled);
        jsonObject.put(EXECUTED, executed);
        return jsonObject;
    }

    /**
     * If we see a task that we don't have data for, we still want to log it, based off of the tag
     * that came in the broadcast. This will be initialised with a {@link #createdAtElapsedSecs} of
     * now, which may or may not be meaningful.
     */
    public static TaskTracker emptyTaskWithTag(String tag) {
        return new TaskTracker(tag, 0L, 0L, 0L, 0L, SystemClock.elapsedRealtime());
    }

    private TaskTracker(String tag, long period, long flex, long windowStartElapsedSecs,
                        long windowEndElapsedSecs, long createdAtElapsedSecs) {
        executionTimes = new ArrayList<Long>();
        this.period = period;
        this.flex = flex;
        this.windowStartElapsedSecs = windowStartElapsedSecs;
        this.windowStopElapsedSecs = windowEndElapsedSecs;
        this.tag = tag;
        this.createdAtElapsedSecs = createdAtElapsedSecs;
    }

    /**
     * Copy properties from another class, notably the runtimes.
     *
     * @param other TaskTracker to copy from.
     */
    public TaskTracker(TaskTracker other) {
        this(other.tag, other.period, other.flex, other.windowStartElapsedSecs,
                other.windowStopElapsedSecs, other.createdAtElapsedSecs);
        this.cancelled = other.isCancelled();
        this.executed = other.isExecuted();
        for (long l : other.executionTimes) {
            this.executionTimes.add(l);
        }
    }

    /**
     * Once we cancel a task it can't be uncancelled.
     */
    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isExecuted() {
        return this.executed;
    }

    /**
     * Execute this task, reporting any errors if this was an invalid execution.
     */
    public void execute(LoggingService.Logger logger) {
        final long elapsedNowSecs = SystemClock.elapsedRealtime() / 1000;


        if (!cancelled) {
            if (executed && period == 0) {
                logger.log(Log.ERROR, "Attempt to execute one off task  " + tag + " multiple " +
                        "times");
                return;
            } else {
                this.executed = true;
                this.executionTimes.add(elapsedNowSecs);
            }
        } else {
            logger.log(Log.ERROR, "Attempt to execute task  " + tag + " after it was cancelled");
            return;
        }

        // Handle periodic errors and one-offs differently.
        // We ignore drift outside this window. This could be a delay due to the JobScheduler/
        // AlarmManager, or we just don't care.
        final int driftAllowed = 10;
        if (period == 0) { // one-off task
            if (elapsedNowSecs > windowStopElapsedSecs + driftAllowed ||
                    elapsedNowSecs < windowStartElapsedSecs - driftAllowed) {
                logger.log(Log.ERROR, "Mistimed execution for task " + tag);
            } else {
                logger.log(Log.INFO, "Successfully executed one-off task " + tag);
            }
        } else { // periodic
            final int n = executionTimes.size(); // This is the nth execution
            if (elapsedNowSecs + driftAllowed
                    < (createdAtElapsedSecs) + (n - 1) * this.period) {
                // Run too early.
                logger.log(Log.ERROR, "Mistimed execution for task " + tag + ": run too early");
            } else if (elapsedNowSecs - driftAllowed > (createdAtElapsedSecs) + n * period) {
                // Run too late.
                logger.log(Log.ERROR, "Mistimed execution for task " + tag + ": run too late");
            } else {
                logger.log(Log.INFO, "Successfully executed periodic task " + tag);
            }
        }
    }
}
