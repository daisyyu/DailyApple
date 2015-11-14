package com.example.daisy.dailyapple.alarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.example.daisy.dailyapple.notification.NotificationBuilderFactory;

/**
 * Created by Daisy on 10/14/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static int mNotificationId = 001;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Daisy", "alarm received push Notification");
        NotificationBuilderFactory factory = new NotificationBuilderFactory
                (context);
        NotificationCompat.Builder builder = factory.getNotificationBuilder
                (NotificationBuilderFactory
                        .NotificationBuilderType.REPEATING_REMINDER);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context
                .NOTIFICATION_SERVICE);
        manager.notify(mNotificationId, builder.build());
    }

}
