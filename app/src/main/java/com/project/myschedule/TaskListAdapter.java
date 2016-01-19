package com.project.myschedule;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by sushil on 1/19/16.
 */
public class TaskListAdapter extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] taskTitle;
    private final String[] taskDesp;
    private final String[] taskStartTime;
    private final String[] taskEndTime;

    public TaskListAdapter(Activity context, String[] taskTitle, String[] taskDesp, String[] taskStartTime, String[] taskEndTime){
        super(context,R.layout.task_list_item,taskTitle);
        this.context = context;
        this.taskTitle = taskTitle;
        this.taskDesp = taskDesp;
        this.taskStartTime = taskStartTime;
        this.taskEndTime = taskEndTime;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.task_list_item, null, true);

        TextView title = (TextView) rowView.findViewById(R.id.taskTitle);
        TextView startTime = (TextView) rowView.findViewById(R.id.startTime);
        TextView endTime = (TextView) rowView.findViewById(R.id.endTime);
        TextView desp = (TextView) rowView.findViewById(R.id.taskDescp);

        //Customize the content of each row based on position
        title.setText(taskTitle[position]);
        startTime.setText(taskStartTime[position]);
        endTime.setText(taskEndTime[position]);
        desp.setText(taskDesp[position]);


        return rowView;
    }
}
