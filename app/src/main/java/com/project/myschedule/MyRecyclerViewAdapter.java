package com.project.myschedule;

/**
 * Created by sushil on 1/16/16.
 */
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
    private  ToggleButton notification;




    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView scheduleTitle;
        TextView toDate;
        TextView fromDate;
        ImageButton menu;
        ToggleButton notification;

        public DataObjectHolder(View itemView) {
            super(itemView);
            scheduleTitle = (TextView) itemView.findViewById(R.id.schedule_title);
            toDate = (TextView) itemView.findViewById(R.id.schedule_to_date);
            fromDate = (TextView) itemView.findViewById(R.id.schedule_from_date);
            menu = (ImageButton) itemView.findViewById(R.id.menu_button);
            notification = (ToggleButton) itemView.findViewById(R.id.toggleButton);
            //DataBase delete = new DataBase(MainActivity.appContext);

            //action to button
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
            Log.i(LOG_TAG, "hmmmmmmm " + getPosition());
            Context context = itemView.getContext();
            Intent intent = new Intent(context, TaskList.class);
            DataBase db = new DataBase(MainActivity.appContext);
            db.open();
            long indexRaw = db.getScheduleId(getPosition());
            int  index = new BigDecimal(indexRaw).intValueExact();
            intent.putExtra("index",db.getId(getAdapterPosition()));
            intent.putExtra("title",""+db.getTitle(getAdapterPosition()));

            db.close();
            context.startActivity(intent);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.scheduleTitle.setText(mDataset.get(position).getScheduleTitle());
        holder.toDate.setText("Till:     "+mDataset.get(position).getToDate());
        holder.fromDate.setText("From: " + mDataset.get(position).getFromDate());
        holder.notification.setChecked(mDataset.get(position).getNotificationStatus());



        holder.notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DataBase db = new DataBase(MainActivity.appContext);
                db.open();
                db.setNotification(position,isChecked);
                //db.close();
                /*if (isChecked) {
                    Toast.makeText(MainActivity.appContext,
                            "on",
                            Toast.LENGTH_SHORT).show();
                    db.setNotification(position, true);
                } else {
                    Toast.makeText(MainActivity.appContext,
                            "off",
                            Toast.LENGTH_SHORT).show();
                    db.setNotification(position, false);

                }*/

            }
        });
        //menu button on click
        holder.menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.appContext, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.actions, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()) {

                            case R.id.edit_schedule:
                                ///
                                return true;
                            //Delete menu
                            case R.id.delete_schedule:
                                DataBase delete = new DataBase(MainActivity.appContext);
                                delete.open();
                                Toast.makeText(MainActivity.appContext,
                                        "Schedule " + mDataset.get(position).getScheduleTitle() + " deleted",
                                        Toast.LENGTH_SHORT).show();
                                delete.deleteSchedule(mDataset.get(position).getScheduleId());
                                delete.close();
                                deleteItem(position);
                                updateResults(mDataset);
                                return true;

                        }
                        return true;
                    }

                });
            }


        });



        /*holder.notification.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //toggle.toggle();
                //boolean notify = notification.isActivated();

            }
        });*/





    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }


    public void updateResults(ArrayList<DataObject> results) {
        mDataset = results;
        //Triggers the list update
        notifyDataSetChanged();
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(MainActivity.appContext,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.show();
    }

}


