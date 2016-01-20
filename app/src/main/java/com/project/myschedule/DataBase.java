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
    public static  final String KEY_ROWID = "task_id";
    public static  final String KEY_TITTLE = "task_title";
    public static  final String KEY_FROM = "task_srt_time";
    public static  final String KEY_TILL = "task_end_time";
    public static  final String KEY_NOTIFICATION = "notification";
    public static final String KEY_DESP = "task_desp";

    //private variables and constant for report table
    public static final String REPORT_ID ="report_id";
    public static final String REPORT_COL1="task_id";
    public static final String REPORT_PERCENTAGE ="completeness";



    //variables for DATABASE
    private static final String DATABASE_NAME ="MySchedule";
    private static final int DATABASE_VERSION =1;

    //variables for TABLES
    private static final String TABLE_TASK ="task";
    private static final String TABLE_REPORT ="report";


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
            sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_TASK + "(" +
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            KEY_TITTLE +" TEXT NOT NULL, "+
                            KEY_FROM +" TEXT NOT NULL, "+
                            KEY_TILL +" TEXT NOT NULL ," +
                            KEY_NOTIFICATION +" TEXT "+");"
            );


/*            sqLiteDatabase.execSQL("CRETE TABLE "+TABLE_REPORT+"(" +
                        REPORT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            REPORT_COL1 +" INTEGER NOT NULL, "+
                            REPORT_PERCENTAGE +" INTEGER NOT NULL, "+
                            " FOREIGN KEY(" +REPORT_COL1 +")" +
                            " REFERENCES "+TABLE_TASK +"(" +TASK_ID +") "+
                            ");"
            );*/

        }

        //if already have DAtabase in system
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_TASK);
            //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_REPORT);
            onCreate(sqLiteDatabase);

        }
    } //end DbHelper

    //constructor
    public DataBase(Context c){
        this.ourContext =c;
        open();

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
    public long addTask(String title, String from, String till,Boolean notify){
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITTLE,title);
        cv.put(KEY_FROM, String.valueOf(from));
        cv.put(KEY_TILL, String.valueOf(till));
        cv.put(KEY_NOTIFICATION, Boolean.valueOf(notify));
        return ourDatabase.insert(TABLE_TASK, null, cv);

    }


    //Get total number of schedule
    public int getTaskCount(){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null);
        return c.getCount();
    }

    //Get schedule title
    public String getTaskTitle(int position){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null);
        int iTitle =c.getColumnIndex(KEY_TITTLE);
        c.moveToPosition(position);
        return c.getString(iTitle);
    }




    //Get schedule from date
    public String getTaskStartTime(int index){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL,KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null);
        c.moveToPosition(index);
        int iFrom =c.getColumnIndex(KEY_FROM);
        return c.getString(iFrom);
    }


    //Get schedule to date
    public String getTaskEndTime(int index){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK,columns,null,null,null,null,null);
        c.moveToPosition(index);
        int iTill =c.getColumnIndex(KEY_TILL);
        return c.getString(iTill);
    }

    //get schedule id
    public long getTaskId(int index){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK,columns,null,null,null,null,null);
        c.moveToPosition(index);
        int iId = c.getColumnIndex(KEY_ROWID);
        return c.getLong(iId);
    }

    //get last schedule id
    //get schedule id
    public int getLastTaskId() {
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null);
        c.moveToLast();
        int iId = c.getColumnIndex(KEY_ROWID);
        return c.getInt(iId);
    }

    //get notification
    public Boolean getNotification(int index){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK,columns,null,null,null,null,null);
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


        ourDatabase.execSQL("UPDATE "+TABLE_TASK +" SET "+KEY_NOTIFICATION +" = "+val+" WHERE " +KEY_ROWID +" = "+getId(index));

    }


    //check if schedule title already exist... returns true if entry already exists on database
    public boolean checkTaskTitle(String title){
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null);

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

        ourDatabase.delete(TABLE_TASK, KEY_ROWID + "=" + id, null);
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

    //TODO add task not working
    //add task in task table

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
        int pos = getTaskCount() - position;
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK,columns,null,null,null,null,null);
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
        int pos = getTaskCount() - position;
        String[] columns = new String[]{KEY_ROWID, KEY_TITTLE, KEY_FROM, KEY_TILL, KEY_NOTIFICATION};
        Cursor c = ourDatabase.query(TABLE_TASK,columns,null,null,null,null,null);
        c.moveToFirst();
        for(int i = 1; i<= pos-1; i++){
            c.moveToNext();
        }
        int iTitle =c.getColumnIndex(KEY_ROWID);
        return c.getInt(iTitle);

    }

    //methods for report
    //add report
    public long addReport(int task_id, int percentage){
        ContentValues cv = new ContentValues();
        cv.put(REPORT_COL1, Integer.valueOf(task_id));
        cv.put(REPORT_PERCENTAGE, percentage);
        return ourDatabase.insert(TABLE_REPORT, null, cv);
    }


    //Get taskTitle
    /*
    public String[] getTaskTitle(int scheduleId){
        int iTitle;
        int count = 0;
        int iId;
        String[] title = new String[getTaskCount(scheduleId)];
        String[] columns = new String[]{TASK_ID,TASK_COL1,TASK_COL2,TASK_COL3,TASK_COL4,TASK_COL5};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null,null);
        c.moveToFirst();
        iTitle =c.getColumnIndex(TASK_COL1);
        //while (c.getInt(iTitle)==scheduleId)
        if(!c.isLast()){
            c.moveToNext();
            iId = c.getColumnIndex(TASK_COL1);
            iTitle =c.getColumnIndex(TASK_COL2);
            if(c.getInt(iId)==scheduleId) {
                title[count] = c.getString(iTitle);
            }
        }
        return title;
    }

    //Get startTime
    public String[] getStartTime(int scheduleId){
        int iTitle;
        int count = 0;
        int iId;
        String[] title = new String[getTaskCount(scheduleId)];
        String[] columns = new String[]{TASK_ID,TASK_COL1,TASK_COL2,TASK_COL3,TASK_COL4,TASK_COL5};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null,null);
        c.moveToFirst();
        iTitle =c.getColumnIndex(TASK_COL3);
        //while (c.getInt(iTitle)==scheduleId)
        if(!c.isLast()){
            c.moveToNext();
            iId = c.getColumnIndex(TASK_COL1);
            iTitle =c.getColumnIndex(TASK_COL3);
            if(c.getInt(iId)==scheduleId) {
                title[count] = c.getString(iTitle);
            }
        }
        return title;
    }

    //Get endTime
    public String[] getEndTime(int scheduleId){
        int iTitle;
        int count = 0;
        int iId;
        String[] title = new String[getTaskCount(scheduleId)];
        String[] columns = new String[]{TASK_ID,TASK_COL1,TASK_COL2,TASK_COL3,TASK_COL4,TASK_COL5};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null,null);
        c.moveToFirst();
        iTitle =c.getColumnIndex(TASK_COL4);
        //while (c.getInt(iTitle)==scheduleId)
        if(!c.isLast()){
            c.moveToNext();
            iId = c.getColumnIndex(TASK_COL1);
            iTitle =c.getColumnIndex(TASK_COL4);
            if(c.getInt(iId)==scheduleId) {
                title[count] = c.getString(iTitle);
            }
        }
        return title;
    }
*/
  /*  //Get startdesp
    public String[] getDesp(int scheduleId){
        int iTitle;
        int count = 0;
        int iId;
        String[] title = new String[getTaskCount(scheduleId)];
        String[] columns = new String[]{TASK_ID,TASK_COL1,TASK_COL2,TASK_COL3,TASK_COL4,TASK_COL5};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null,null);
        c.moveToFirst();
        iTitle =c.getColumnIndex(TASK_COL5);
        //while (c.getInt(iTitle)==scheduleId)
        if(!c.isLast()){
            c.moveToNext();
            iId = c.getColumnIndex(TASK_COL1);
            iTitle =c.getColumnIndex(TASK_COL5);
            if(c.getInt(iId)==scheduleId) {
                title[count] = c.getString(iTitle);
            }
        }
        return title;
    }


   /* public int getTaskCount(int scheduleID){
        //addDummyValue();
        int iTitle;
        int val = 0;
        String[] columns = new String[]{TASK_ID,TASK_COL1,TASK_COL2,TASK_COL3,TASK_COL4,TASK_COL5};
        Cursor c = ourDatabase.query(TABLE_TASK, columns, null, null, null, null, null,null);
        iTitle = c.getColumnIndex(TASK_COL1);
        int count = c.getCount();
        if(count==0){
            return 0;
        }else{
           // while (!c.isLast()){
             //   if(scheduleID==c.getInt(iTitle))
               // val++;
            }

            return getId(iTitle);
            //}
    }*/



   /* public String[] getTskTitle(int schId){
        int val;
        String q = "select "+TASK_COL3+" from "+TABLE_TASK+" where "+TASK_COL1+" = "+schId;
        Cursor c = ourDatabase.rawQuery(q,null);
        String[] title = new String[c.getCount()];

        for (int i = 0; i<= c.getCount(); i++){
            val = c.getColumnIndex(TASK_COL3);
            title[i] = c.getString(val);

        }
        return  title;
    }*/












}//end DataBAse
