package com.viovie.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

public class AlarmHelper {
    public static void create(@NonNull Context context, int position, AlarmItem item) {
        Intent intent = buildReceiverIntent(context, position, item);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, item.datetime.getTimeInMillis(), pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, item.datetime.getTimeInMillis(), pendingIntent);
        }
    }

    public static void update(@NonNull Context context, int position, AlarmItem item) {
        Intent intent = buildReceiverIntent(context, position, item);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, item.datetime.getTimeInMillis(), pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, item.datetime.getTimeInMillis(), pendingIntent);
        }
    }

    public static void delete(@NonNull Context context, int position) {
        Intent intent = buildReceiverIntent(context, position, null);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    protected static Intent buildReceiverIntent(@NonNull Context context, int position, AlarmItem item) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("alarm" + position); // set same intent
        intent.putExtra(AlarmReceiver.PARAM_POSITION, position);
        if (item != null) {
            intent.putExtra(AlarmReceiver.PARAM_ALARM_ITEM, item);
        }

        return intent;
    }
}
