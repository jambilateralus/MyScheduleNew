package com.project.myschedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {


    private Button startTime;
    private Button endTime;
    private EditText taskTitle;
    private EditText taskDesp;
    private int[] sTime = new int[2];
    private int[] eTime=new int[2];
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        db = new DataBase(getBaseContext());
        db.open();


        //
        startTime = (Button) findViewById(R.id.startTimeButton);
        endTime =(Button) findViewById(R.id.endTimeButton);
        taskTitle = (EditText) findViewById(R.id.taskTitle);
        taskDesp = (EditText) findViewById(R.id.taskDescp);

        //Set title
        //setTitle("New Task"+db.getLastTaskId());

        //Back arrow in action bar
        final ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        ///////////////////////////////////////////////////////////////////////////////////////////
        //START Time Picker Dialog
        ///////////////////////////////////////////////////////////////////////////////////////////
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //time set stuff
                sTime[0] = hourOfDay;
                sTime[1] = minute;
                startTime.setText("Start: " + sTime[0] + ":" + sTime[1]);

            }
        };
        final TimePickerDialog startDialog = new TimePickerDialog(this,mTimeSetListener,0,0,false);
        startDialog.setTitle("Start Time");
        startDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                // Cancel code here
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////





        ///////////////////////////////////////////////////////////////////////////////////////////////
        //END Time Picker Dialog
        ///////////////////////////////////////////////////////////////////////////////////////////////
        TimePickerDialog.OnTimeSetListener nTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                eTime[0] = hourOfDay;
                eTime[1] = minute;
                endTime.setText("End: "+eTime[0]+":"+eTime[1]);

                //time set stuff
            }
        };
        final TimePickerDialog endDialog = new TimePickerDialog(this,nTimeSetListener,0,0,false);
        endDialog.setTitle("End Time");
        endDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                // Cancel code here
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////





        //---------------------------------------------------------------------------------------------
        /////////////////////////////////////////////////////////////////////////////////////////////
        //SET START TIME
        ////////////////////////////////////////////////////////////////////////////////////////////
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog.show();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        //------------------------------------------------------------------------------------------



        ////////////////////////////////////////////////////////////////////////////////////////////
        //Set End Time
        ////////////////////////////////////////////////////////////////////////////////////////////
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDialog.show();
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //on back button clicked
        if (id == android.R.id.home) {
            //startActivity(new Intent(getBaseContext(),MainActivity.class));
            finish();
        }else if(id == R.id.action_done){
            //Hide keyboard
            View view = this.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);


            String startTime = ""+sTime[0]+" "+sTime[1];
            String endTime = ""+eTime[0]+" "+eTime[1];
            String title = taskTitle.getText().toString();
            String desp = taskDesp.toString();


            //int scheduleId = 1;

            //Check input data
            if(title.matches("") || (sTime[0]==eTime[0]&&sTime[1]==eTime[1])){
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                Toast.makeText(getBaseContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                v.vibrate(100);
            }

            else {
                //Call database function from here
                //Bundle bundl = getIntent().getBundleExtra("a");
                //int a = bundl.getInt("a");
                db.addTask(title, startTime, endTime, true);
                this.finish();
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
                //Start Alarm
                //startAlarm(sTime[0],sTime[1]);
                int sId =db.getLastTaskId()*200;
                int eId = db.getLastTaskId()*40;
                //new Alarm().addAlarm(sId,sTime[0], sTime[1],0,title);
                //new Alarm().addAlarm(eId,eTime[0],eTime[1],1,title);
                new  Alarm(sId,sTime[0], sTime[1],0,title);
                new Alarm(eId,eTime[0],eTime[1],1,title);
                db.close();

            }


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addtask, menu);
        return true;
    }




}