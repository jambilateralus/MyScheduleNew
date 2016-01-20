package com.project.myschedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sushil on 1/21/16.
 */
public class EndAlarmReceiver extends BroadcastReceiver {

    private Intent i;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub


        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring
        // the phone



        i = new Intent(context, NotificationEnd.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_LONG).show();
    }
}
