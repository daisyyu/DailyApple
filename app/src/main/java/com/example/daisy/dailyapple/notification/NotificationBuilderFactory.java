package com.example.daisy.dailyapple.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import com.example.daisy.dailyapple.MainActivity;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.welcome.WelcomeActivity;

/**
 * Created by Daisy on 10/18/15.
 */
public class NotificationBuilderFactory {
    private static NotificationBuilderFactory factory;
    private static Context context;

    public NotificationBuilderFactory(Context context) {
        this.context = context;
    }

//    public static NotificationBuilderFactory getNotificationBuilderFactory
//            (Context
//                                                                            context) {
//        if (factory == null) {
//            factory = new NotificationBuilderFactory(context);
//        }
//        return factory;
//    }

    public enum NotificationBuilderType {
        REPEATING_REMINDER,
    }

    @Nullable
    public NotificationCompat.Builder getNotificationBuilder
            (NotificationBuilderType
                     type) {
        switch (type) {

            case REPEATING_REMINDER:
                return createRepeatingReminderNotificationBuilder();
            default:
                return null;
        }

    }

    private NotificationCompat.Builder
    createRepeatingReminderNotificationBuilder() {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R
                .drawable.apple);
        largeIcon = Bitmap.createScaledBitmap(largeIcon, 200, 200, false);
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context).setSmallIcon(R.drawable.apple).setLargeIcon
                (largeIcon)
                .setContentTitle
                        ("Ready to learn more words?").setAutoCancel(true)
                .setContentText("Tab to start!");
        Intent resultIntent = new Intent(context, WelcomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        return builder;
    }
}
