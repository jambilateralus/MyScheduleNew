package com.project.myschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //Set title
        setTitle("New Task");


    }
}
