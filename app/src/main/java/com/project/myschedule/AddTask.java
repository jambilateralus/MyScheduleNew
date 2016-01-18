package com.project.myschedule;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddTask extends AppCompatActivity {

    private Button startTime;
    private Button endTime;
    private EditText taskTitle;
    private EditText taskDesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //
        startTime = (Button) findViewById(R.id.startTimeButton);
        endTime =(Button) findViewById(R.id.endTimeButton);
        taskTitle = (EditText) findViewById(R.id.taskTitle);
        taskDesp = (EditText) findViewById(R.id.taskDescp);

        //Set title
        setTitle("New Task");

        //Back arrow in action bar
        final ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        ///////////////////////////////////////////////////////////////////////////////////////////
        //Start Time Picker Dialog
        ///////////////////////////////////////////////////////////////////////////////////////////
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //time set stuff
            }
        };
        final TimePickerDialog startDialog = new TimePickerDialog(this,mTimeSetListener,0,0,false);
        startDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                // Cancel code here
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////





        ///////////////////////////////////////////////////////////////////////////////////////////////
        //End Time Picker Dialog
        ///////////////////////////////////////////////////////////////////////////////////////////////
        TimePickerDialog.OnTimeSetListener nTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //time set stuff
            }
        };
        final TimePickerDialog endDialog = new TimePickerDialog(this,nTimeSetListener,0,0,false);
        startDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
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