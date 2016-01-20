package com.project.myschedule;

/**
 * Created by sushil on 1/16/16.
 */
public class DataObject {
    private String taskTitle;
    private String startTime;
    private  String endTime;
    private  long taskId;
    private Boolean notify;

    DataObject (String taskTitle, String startTime, String endTime, long taskId, Boolean notify){
        this.taskTitle = taskTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.taskId = taskId;
        this.notify = notify;
    }

    public String getTaskTitle() {return taskTitle;}

    //public void setScheduleTitle(String scheduleTitle) {this.scheduleTitle = scheduleTitle;
    //}

    public String getStartTime() {
        return startTime;
    }

    //public void setToDate(String toDate) {
      //  this.toDate = toDate;
    //}

    public String getEndTime(){return endTime;}

    public Long getTaskId(){return taskId;}

    //public void  setFromDate(String fromDate){this.fromDate = fromDate;}

    //public void setNotifcationStatus(Boolean satus){this.notify = satus;}

    public Boolean getNotificationStatus(){return notify;}

    //
    public void refresh(){
    }



}

