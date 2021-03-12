package com.algo.phantoms.schedulefcm.fcm;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.algo.phantoms.schedulefcm.util.NotificationUtil;

public class ScheduledWorker extends Worker {

    private final String TAG = "ScheduledWorker";
    static final String NOTIFICATION_TITLE = "notification_title";
    static final String NOTIFICATION_MESSAGE = "notification_message";

    public ScheduledWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d(TAG, "Work START");

        // Get Notification Data
        String title = getInputData().getString(NOTIFICATION_TITLE);
        String message = getInputData().getString(NOTIFICATION_MESSAGE);

        // Show Notification
        new NotificationUtil(getApplicationContext()).showNotification(title, message);

        // TODO Do your other Background Processing

        Log.d(TAG, "Work DONE");
        // Return result

        return Result.success();

    }
}
