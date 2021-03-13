package com.algo.phantoms.schedulefcm.fcm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.algo.phantoms.schedulefcm.util.NotificationUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

import static com.algo.phantoms.schedulefcm.fcm.ScheduledWorker.NOTIFICATION_MESSAGE;
import static com.algo.phantoms.schedulefcm.fcm.ScheduledWorker.NOTIFICATION_TITLE;
import static com.algo.phantoms.schedulefcm.util.SettingUtil.isTimeAutomatic;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}");

            // Get Message details
            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("message");

            // Check that 'Automatic Date and Time' settings are turned ON.
            // If it's not turned on, Return
            if (!isTimeAutomatic(getApplicationContext())) {
                Log.d(TAG, "`Automatic Date and Time` is not enabled");
            }

            // Check whether notification is scheduled or not
            boolean isScheduled = Boolean.parseBoolean(remoteMessage.getData().get("isScheduled"));
            if (isScheduled) {
                // This is Scheduled Notification, Schedule it
                String scheduledTime = remoteMessage.getData().get("scheduledTime");
                try {
                    scheduleAlarm(scheduledTime, title, message);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                // This is not scheduled notification, show it now
                showNotification(title, message);
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void scheduleAlarm(
            String scheduledTimeString,
            String title,
            String message
    ) throws ParseException {
        AlarmManager alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
        alarmIntent.putExtra(NOTIFICATION_TITLE, title);
        alarmIntent.putExtra(NOTIFICATION_MESSAGE, message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);

        // Parse Schedule time
        Date scheduledTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .parse(scheduledTimeString);

        // With set(), it'll set non repeating one time alarm.
        assert scheduledTime != null;
        alarmMgr.set(
                    AlarmManager.RTC_WAKEUP,
                    scheduledTime.getTime(),
                    pendingIntent
            );
    }

    private void showNotification(String title,String message) {
        new NotificationUtil(getApplicationContext()).showNotification(title, message);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: $token");
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }

}
