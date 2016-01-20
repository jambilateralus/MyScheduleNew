package com.project.myschedule;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class NotificationEnd extends AppCompatActivity {

    private MediaPlayer mp;
    private SeekBar sb;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_end);

        //Hide Action Bar
        getSupportActionBar().hide();

        sb = (SeekBar) findViewById(R.id.seekBar);
        tv = (TextView) findViewById(R.id.textView);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mp.setLooping(true);
        mp.start();



        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //tv.setText(String.valueOf(progress));
                int val =progress;
                DataBase  db = new DataBase(MainActivity.appContext);
                db.open();
                db.addReport(val);
                db.close();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
        finish();
    }
}
