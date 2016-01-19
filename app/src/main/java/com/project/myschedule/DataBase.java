package com.project.myschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sushil o n 10/25/15.
 */
public class DataBase {
    //Private variables and constant variables for schedule tables
    public static  final String KEY_ROWID = "schedule_id";
    public static  final String KEY_TITTLE = "schedule_title";
    public static  final String KEY_FROM = "date_from";
    public static  final String KEY_TILL = "date_till";
    public static  final String KEY_NOTIFICATION = "notification";
    public static final String KEY_ID = "ID";

    //private variables and constant  vriables for task table
    public static final String TASK_ID = "task_id";
    public static final String TASK_COL1= "schedule_id";
    public static final String TASK_COL2 = "task_name";
    public static final String TASK_COL3 = "task_srt_time";
    public static final String TASK_COL4 = "task_end_time";
    public static final String TASK_COL5= "task_icon";
    public static final String TASK_COL6= "task_priority";
    public static final String TASK_COL7= "task_desp";



    //variables for DATABASE
    private static final String DATABASE_NAME ="MySchedule";
    private static final int DATABASE_VERSION =1;

    //variables for TABLES
    private static final String TABLE_SCHEDULE ="schedule";
    private static final String TABLE_TASK ="task";


    //instances of DbHelper class and variables
    private DbHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase ;


    //private class for sqlite opertion ---like a mediator
    private static class DbHelper extends SQLiteOpenHelper{

        //DbHelper constructor for database setting
        public DbHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        //method to create database for first time
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_SCHEDULE + "(" +
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            KEY_TITTLE +" TEXT NOT NULL, "+
                            KEY_FROM +" TEXT NOT NULL, "+
                            KEY_TILL +" TEXT NOT NULL ," +
                            KEY_NOTIFICATION +" TEXT " +");"
            );

            sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_TASK + "(" +
                            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            TASK_COL1 +" INTEGER NOT NULL, "+
                            TASK_COL2 +" TEXT NOT NULL, "+
                            TASK_COL3 +" TEXT NOT NULL, "+
                            TASK_COL4 +" TEXT NOT NULL, "+
                            TASK_COL5 +" TEXT NOT NULL, "+
                            TASK_COL6 +" INTEGER NOT NULL, " +
                            TASK_COL7 +" TEXT NOT NULL, " +
                            " FOREIGN KEY(" +TASK_COL2 +")" +
                            " REFERENCES "+TABLE_SCHEDULE +"(" +KEY_ROWID +") "+
                            ");"
            );

        }

        //if already have DAtabase in system
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_SCHEDULE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_TASK);
            onCreate(sqLiteDatabase);

        }
    } //end DbHelper

    //constructor
    public DataBase(Context c){
        this.ourContext =c;

    }

    //open conection for database
    public DataBase open(){
        ourHelper = new DbHelper(ourContext);
        ourDatabase =ourHelper.getWritableDatabase();
        return this;
    }

    //close conection
    public void close(){
        ourHelper.close();
    }


    //insert query forschedule table
    public long addSchedule(String title, String from, String till,Boolean notify){
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITTLE,title);
        cv.put(KEY_FROM, String.valueOf(from));
        cv.put(KEY_TILL, String.valueOf(till));
        cv.put(KEY_NOTIFICATION, Boolean.valueOf(notify));
        return ourDatabase.insert(TABLE_SCHEDULE, null, cv);

    }


    //Get total number of schedule
    public int getScheduleCount(){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE, columns, null, null, null, null, null);
        return c.getCount();
    }

    //Get schedule title
    public String getScheduleTitle(int position){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE, columns, null, null, null, null, null);
        int iTitle =c.getColumnIndex(KEY_TITTLE);
        c.moveToPosition(position);
        return c.getString(iTitle);
    }




    //Get schedule from date
    public String getScheduleFromDate(int index){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL,KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE, columns, null, null, null, null, null);
        c.moveToPosition(index);
        int iFrom =c.getColumnIndex(KEY_FROM);
        return c.getString(iFrom);
    }


    //Get schedule to date
    public String getScheduleToDate(int index){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE,columns,null,null,null,null,null);
        c.moveToPosition(index);
        int iTill =c.getColumnIndex(KEY_TILL);
        return c.getString(iTill);
    }

    //get schedule id
    public long getScheduleId(int index){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE,columns,null,null,null,null,null);
        c.moveToPosition(index);
        int iId = c.getColumnIndex(KEY_ROWID);
        return c.getLong(iId);

    }

    //get notification
    public Boolean getNotification(int index){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE,columns,null,null,null,null,null);
        c.moveToPosition(index);
        int iId = c.getColumnIndex(KEY_NOTIFICATION);
        if(c.getInt(iId)==1){
            return true;
        }
        else {
            return false;
        }

    }

    //set notification
    public void setNotification(int index,boolean status){
        int val;
        if(status){val = 1;}
        else {val = 0;}


        ourDatabase.execSQL("UPDATE "+TABLE_SCHEDULE +" SET "+KEY_NOTIFICATION +" = "+val+" WHERE " +KEY_ROWID +" = "+getId(index));

    }


    //check if schedule title already exist... returns true if entry already exists on database
    public boolean checkScheduleTitle(String title){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE, columns, null, null, null, null, null);

        boolean result = false;

        for (int i=0; i<c.getCount(); i++){
            c.moveToPosition(i);
            int iTitle =c.getColumnIndex(KEY_TITTLE);
            if(title.equals(c.getString(iTitle))){
                result = true;

            }
        }
        return result;

    }


    //delete schedule from schedule table
    public void deleteSchedule(long id){

        ourDatabase.delete(TABLE_SCHEDULE, KEY_ROWID + "=" + id, null);
        //ourDatabase.execSQL("UPDATE "+TABLE_SCHEDULE +" SET "+KEY_ID +"= "+KEY_ID +"-1 " +"WHERE " +KEY_ROWID +" = "+"> "+id);
       /* long index,newValue;
        long front = id+1;
        for(long x = front; x<=getScheduleCount();x++){
            index = x;
            newValue = x-1;
            ourDatabase.execSQL("UPDATE "+TABLE_SCHEDULE+ " SET "+KEY_ID+" = '"+newValue+"' WHERE " +KEY_ID +" = "+index);
        }*/

    }



    //methods for task table

    //add task in task table
    public long addTask(String name, int sch_id, String srtTime, String endTime, String icon, boolean priority, String desp){
        ContentValues cv = new ContentValues();
        cv.put(TASK_COL1,sch_id);
        cv.put(TASK_COL2, String.valueOf(name));
        cv.put(TASK_COL3, String.valueOf(srtTime));
        cv.put(TASK_COL4, String.valueOf(endTime));
        cv.put(TASK_COL5, String.valueOf(icon));
        cv.put(TASK_COL6, priority);
        cv.put(TASK_COL7,desp );
        return ourDatabase.insert(TABLE_TASK,null,cv);
    }

    //get KEYUD
    /*public int getKeyId(int id){
        String[] columns = new String[]{KEY_ROWID,KEY_ID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE, columns, null, null, null, null, null);
        int iKey =c.getColumnIndex(KEY_ID);
        c.moveToPosition(id);
        return c.getInt(iKey);

    }*/


    //Get schedule title from
    public String getTitle(int position){
        //int pos = position +1;
        int pos = getScheduleCount() - position;
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE,columns,null,null,null,null,null);
        c.moveToFirst();
        for(int i = 1; i<= pos-1; i++){
            c.moveToNext();
        }
        int iTitle =c.getColumnIndex(KEY_TITTLE);
        return c.getString(iTitle);

    }


    //Get row id from cardview position
    public int getId(int position){
        //int pos = position +1;
        int pos = getScheduleCount() - position;
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_SCHEDULE,columns,null,null,null,null,null);
        c.moveToFirst();
        for(int i = 1; i<= pos-1; i++){
            c.moveToNext();
        }
        int iTitle =c.getColumnIndex(KEY_ROWID);
        return c.getInt(iTitle);

    }













}//end DataBAse
