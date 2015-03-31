package com.unaiamutxastegi.earthquakes.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cursomovil on 31/03/15.
 */
public class Alarm extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        long timeOrLengthofWait = 10000;
        String ALARM_ACTION = "ALARM_ACTION";

        Intent intentToFire = new Intent(ALARM_ACTION);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intentToFire, 0);

        alarmManager.set(alarmType, timeOrLengthofWait, alarmIntent);

        alarmManager.cancel(alarmIntent);
    }

    private void setInexactRepeatingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        long timeOrLengthofWait = AlarmManager.INTERVAL_HALF_HOUR;

        String ALARM_ACTION = "ALARM_ACTION";

        Intent intentToFire = new Intent(ALARM_ACTION);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intentToFire, 0);


        alarmManager.setInexactRepeating(alarmType, timeOrLengthofWait, timeOrLengthofWait, alarmIntent);
    }
}
