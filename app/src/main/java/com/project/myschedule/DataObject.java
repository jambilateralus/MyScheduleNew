package com.project.myschedule;

/**
 * Created by sushil on 1/16/16.
 */
public class DataObject {
    private String scheduleTitle;
    private String toDate;
    private  String fromDate;
    private  long scheduleId;
    private Boolean notify;

    DataObject (String scheduleTitle, String toDate, String fromDate, long scheduleId, Boolean notify){
        this.scheduleTitle = scheduleTitle;
        this.toDate = toDate;
        this.fromDate = fromDate;
        this.scheduleId = scheduleId;
        this.notify = notify;
    }

    public String getScheduleTitle() {return scheduleTitle;}

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate(){return fromDate;}

    public Long getScheduleId(){return scheduleId;}

    public void  setFromDate(String fromDate){this.fromDate = fromDate;}

    public void setNotifcationStatus(Boolean satus){this.notify = satus;}

    public Boolean getNotificationStatus(){return notify;}

    //
    public void refresh(){
    }



}

