package com.unaiamutxastegi.earthquakes.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.unaiamutxastegi.earthquakes.service.DownloadEarthQuakeService;

/**
 * Created by cursomovil on 31/03/15.
 */
public class Alarm  {

    public static void setAlarm(Context context, long timeOrLengthofWait) {
        int alarmType = AlarmManager.RTC;

        AlarmManager alarmManager =  (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent intentToFire = new Intent(context, DownloadEarthQuakeService.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intentToFire, 0);

        alarmManager.setInexactRepeating(alarmType, timeOrLengthofWait, timeOrLengthofWait, alarmIntent);
    }

    public static void stopAlarm(Context context){
        AlarmManager alarmManager =  (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intentToFire = new Intent(context, DownloadEarthQuakeService.class);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intentToFire, 0);

        alarmManager.cancel(alarmIntent);
    }

    public static void updateAlarm(Context context, long timeOrLengthofWait){
        setAlarm(context,timeOrLengthofWait);
    }

}
