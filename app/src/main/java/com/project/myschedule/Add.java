package com.project.myschedule;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import java.util.Calendar;

public class Add extends AppCompatActivity {

    static final int FROM_DATE_DIALOG_ID = 0;
    static final int TILL_DATE_DIALOG_ID = 1;

    //Variables to store user selected date.
    public int year,month,day;

    //Variables to store date when it appears first
    private int mYear, mMonth, mDay;
    static String months[] =
            {
                    null , "Jan" , "Feb" , "Mar" , "Apr", "May",
                    "Jun", "Jul", "Aug", "Sep", "Oct",
                    "Nov", "Dec"
            };

    //From and till buttons
    Button fButton;
    Button tButton;



    //Schedule title
    EditText schedule_title;
    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Set title
        setTitle("New Schedule");


        //Back arrow in action bar
        final ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        //Assign current date to variables.
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //Get references of buttons
        fButton= (Button) findViewById(R.id.from_button);
        tButton = (Button) findViewById(R.id.till_button);

        //Set label of buttons to current date
        fButton.setText(mDay + " " + months[mMonth] + " " + mYear);
        tButton.setText(mDay + " " + months[mMonth] + " " + mYear);
        //Set click listener on fButton
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(FROM_DATE_DIALOG_ID);

            }
        });

        //Set click listener on tButton
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(TILL_DATE_DIALOG_ID);

            }
        });
    }

    //Toaster
    private void ToastMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // Register  FromDatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {
                    year = yearSelected;
                    month = monthOfYear+1;
                    day = dayOfMonth;
                    // Set the Selected Date in Select date Button
                    fButton.setText(day + " " + months[month] + " " + year);
                }
            };

    // Register  TillDatePickerDialog listener
    private DatePickerDialog.OnDateSetListener nDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {
                    year = yearSelected;
                    month = monthOfYear+1;
                    day = dayOfMonth;
                    // Set the Selected Date in Select date Button
                    tButton.setText(day + " " + months[month] + " " + year);
                }
            };


    // Method automatically gets Called when you call showDialog()  method
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case FROM_DATE_DIALOG_ID:
                // create a new FromDatePickerDialog with values you want to show
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            // create a new TillDatePickerDialog with values you want to show
            case  TILL_DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        nDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        schedule_title = (EditText) findViewById(R.id.schedule_title);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        if (id == R.id.action_done) {


            //Hide keyboard
            View view = this.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);


            //Check if Schedule title is empty and toast an error message.
            DataBase db = new DataBase(this);
            db.open();
            title = schedule_title.getText().toString();
            if (title.matches("")) {
                ToastMessage("Invalid schedule name");
                // Vibrate for 100 milliseconds
                v.vibrate(100);}



            //if the Schedule title already exists
            else if(db.checkScheduleTitle(title)){
                ToastMessage("Schedule "+title + " already exists");
                schedule_title.setText("");
                // Vibrate for 100 milliseconds
                v.vibrate(100);
            }

            //Finally add contents to database
            else{
                boolean ditItWork =true;
                try {
                    //database action add sechudule
                    String from = fButton.getText().toString();
                    String till = tButton.getText().toString();
                    Boolean status = false;
                    DataBase add = new DataBase(Add.this);
                    add.open();
                    add.addSchedule(title, from, till,status);
                    add.close();
                }catch (Exception e){
                    ditItWork = false;

                }finally {
                    if(!ditItWork){
                        Dialog d = new Dialog(this);
                        d.setTitle("Yo Myan !");
                        TextView tv = new TextView(this);
                        tv.setText("Something Got Wrong!");
                        d.setContentView(tv);
                        d.show();
                    }
                }
                Intent Goto = new Intent(getBaseContext(),MainActivity.class);
                //startActivity(Goto);

                Goto.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(0, 0);
                startActivity(Goto);
            }
        }
        //on back button clicked
        else if(id== android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {

        overridePendingTransition(0, 0);

        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getBaseContext(),MainActivity.class));
    }
}
