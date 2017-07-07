package com.viovie.alarmclock;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String PARAM_ALARM_ITEM = "AlarmReceiver.alarm.item";
    public static final String PARAM_POSITION = "AlarmReceiver.position";

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmItem item = intent.getParcelableExtra(PARAM_ALARM_ITEM);
        int position = intent.getIntExtra(PARAM_POSITION, 0);

        // update data and alarm
        if (item.isRepeat) {
            item.useNextTime();
            DataStorage.update(context, position, item);
            AlarmHelper.update(context, position, item);
        }

        Intent newIntent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, newIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(item.title)
                .setContentText(item.content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
