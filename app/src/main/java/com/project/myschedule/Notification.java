package com.project.myschedule;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Notification extends AppCompatActivity {
    private Button now;
    private Button later;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //Hide Action Bar
        getSupportActionBar().hide();

        mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mp.setLooping(true);
        mp.start();

        now = (Button) findViewById(R.id.buttonNow);
        later = (Button) findViewById(R.id.buttonLater);

        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                finish();
            }
        });

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                finish();
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
