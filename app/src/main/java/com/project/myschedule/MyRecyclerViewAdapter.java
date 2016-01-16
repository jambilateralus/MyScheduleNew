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
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView scheduleTitle;
        TextView toDate;
        TextView fromDate;
        ImageButton menu;

        public DataObjectHolder(View itemView) {
            super(itemView);
            scheduleTitle = (TextView) itemView.findViewById(R.id.schedule_title);
            toDate = (TextView) itemView.findViewById(R.id.schedule_to_date);
            fromDate = (TextView) itemView.findViewById(R.id.schedule_from_date);
            menu = (ImageButton) itemView.findViewById(R.id.menu_button);
            DataBase delete = new DataBase(MainActivity.appContext);

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
            intent.putExtra("index",""+getPosition());
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
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.scheduleTitle.setText(mDataset.get(position).getScheduleTitle());
        holder.toDate.setText(mDataset.get(position).getToDate());
        holder.fromDate.setText(mDataset.get(position).getFromDate());



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
                                        "Schedule "+mDataset.get(position).getScheduleTitle()+" deleted",
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


