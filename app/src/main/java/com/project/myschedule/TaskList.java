package com.project.myschedule;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class TaskList extends AppCompatActivity {
    private Bundle bundle;
    private ListView lv;
    private int shId;
    private DataBase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        lv = (ListView) findViewById(R.id.taskListView);
        //Back Button
        final ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        //set title
        bundle = getIntent().getExtras();
        shId = bundle.getInt("index");
        String title = bundle.getString("title");
        setTitle(title);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Display task list
        db = new DataBase(getBaseContext());
        db.open();
        /*String[] taskTitle = new String[db.getScheduleCount()];
        String[] taskStartTime = new String[db.getScheduleCount()];
        String[] taskEndTime = new String[db.getScheduleCount()];
        String[] taskDesp = new String[db.getScheduleCount()];


        //Load contents of database to array
        for (int i = 0; i<db.getScheduleCount(); i++){
            taskTitle[i]= ;
            taskStartTime[i]= ;
            taskEndTime[i] = ;
            taskDesp[i] = ;
        }

        //Set Custom adapter to listview


        */


        //set custom adapter to list view
        TaskListAdapter adpt = new TaskListAdapter(this,db.getTaskTitle(shId),db.getDesp(shId),db.getStartTime(shId),db.getEndTime(shId));
        lv.setAdapter(adpt);
        db.close();
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
            //int scheduleId = bundle.getInt("index");
            Intent addTask = new Intent(getBaseContext(),AddTask.class);
            addTask.putExtra("scheduleId",shId);
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
