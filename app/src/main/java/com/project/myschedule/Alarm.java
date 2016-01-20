package com.project.myschedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by sushil on 1/20/16.
 */
public class Alarm {
    public void addAlarm(int hr, int min){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        if(Calendar.getInstance().after(calendar)){
            // Move to tomorrow
            calendar.add(Calendar.DATE, 1);
        }
        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(MainActivity.appContext, AlarmReceiver.class);

        //PendingIntent pIntent = PendingIntent.getActivity(this,min, intentAlarm , 0);
        // create the object
        AlarmManager alarmManager = (AlarmManager) MainActivity.appContext.getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), PendingIntent.getBroadcast(MainActivity.appContext, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.appContext, id, new Intent(MainActivity.appContext,Notification.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //pendingIntent.
       // alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);//' , PendingIntent.FLAG_UPDATE_CURRENT;
        //alarmManager.cancel(pendingIntent);
        Toast.makeText(MainActivity.appContext, "Alarm Scheduled", Toast.LENGTH_LONG).show();
    }


    public void stopAlarm(int alarmId){

    }

    public void restartAlarm(int alarmId){

    }


    public void deleteAlarm(int alarmId){

    }

}
