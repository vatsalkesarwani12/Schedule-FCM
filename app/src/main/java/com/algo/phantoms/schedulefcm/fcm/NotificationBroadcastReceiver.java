package com.algo.phantoms.schedulefcm.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static com.algo.phantoms.schedulefcm.fcm.ScheduledWorker.NOTIFICATION_MESSAGE;
import static com.algo.phantoms.schedulefcm.fcm.ScheduledWorker.NOTIFICATION_TITLE;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "NotificationBroadcastRe";

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(NOTIFICATION_TITLE);
        String message = intent.getStringExtra(NOTIFICATION_MESSAGE);

        // Create Notification Data

         Data notificationData = new Data.Builder()
                .putString(NOTIFICATION_TITLE, title)
                .putString(NOTIFICATION_MESSAGE, message)
                .build();

         // Init Worker
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(ScheduledWorker.class)
                .setInputData(notificationData)
                .build();

        WorkManager.getInstance().beginWith(work).enqueue();

        Log.d(TAG, "WorkManager is enqueued");

    }
}
