package com.project.myschedule;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TaskList extends AppCompatActivity {
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //Back Button
        final ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        //set title
        bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        setTitle(title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //on back button clicked
        if(id == android.R.id.home){
            //startActivity(new Intent(getBaseContext(),MainActivity.class));
            finish();
        }

        //on add button pressed
        else if(id == R.id.action_add_task){
            int scheduleId = bundle.getInt("index");
            Intent addTask = new Intent(getBaseContext(),AddTask.class);
            addTask.putExtra("scheduleId",scheduleId);
            startActivity(new Intent(getBaseContext(),AddTask.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tasklist, menu);
        return true;//
    }
}
