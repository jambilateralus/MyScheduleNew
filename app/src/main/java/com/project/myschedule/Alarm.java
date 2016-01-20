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


    Intent intent;
    //taskType = 0 for start task
    //1 for task finish
    public  Alarm(int id,int hr, int min,int taskType, String taskName){

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
        //Intent intentAlarm = new Intent(MainActivity.appContext, AlarmReceiver.class);

        //PendingIntent pIntent = PendingIntent.getActivity(MainActivity.appContext,min, intentAlarm , 0);
        // create the object
        AlarmManager alarmManager = (AlarmManager) MainActivity.appContext.getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.appContext, id, new Intent(MainActivity.appContext,Notification.class), PendingIntent.FLAG_UPDATE_CURRENT);


        if(taskType==0) {
            intent = new Intent(MainActivity.appContext, AlarmReceiver.class);
        }else {
            intent = new Intent(MainActivity.appContext, EndAlarmReceiver.class);
        }

        PendingIntent appIntent = PendingIntent.getBroadcast(MainActivity.appContext, id, intent,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), appIntent);
        Toast.makeText(MainActivity.appContext, "Task Scheduled", Toast.LENGTH_LONG).show();
    }


    public void stopAlarm(int alarmId){
        PendingIntent.getBroadcast(MainActivity.appContext, alarmId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT).cancel();
    }

    public void restartAlarm(int alarmId){

    }

}
