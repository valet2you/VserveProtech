package com.viralops.touchlessfoodordering.database;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Dashboard1;
import com.viralops.touchlessfoodordering.Model.Laundry_Dashboard;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dominic on 13/04/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    SQLiteDatabase db;
    DbHelper mDbHelper;
    ArrayList<Laundry_Dashboard1.Data> localarray=new ArrayList<>();
    Calendar calendar;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        mDbHelper = new DbHelper(context);
        db= mDbHelper.getWritableDatabase();
        localarray=mDbHelper.getAllCotacts();
        calendar=Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String format = s.format(new Date());
     //   Toast.makeText(context, "notify 1" + intent.getStringExtra("time") , Toast.LENGTH_LONG).show();
        String Title = intent.getStringExtra("title");
        String time = intent.getStringExtra("time");
        String id = intent.getStringExtra("id");
        Intent x = new Intent(context, Alert.class);
        x.putExtra("title",Title);
        x.putExtra("time",time);
        x.putExtra("alramid",id);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(x);
/*
        for(int i=0;i<localarray.size();i++){
            String date=localarray.get(i).getDatenew()+" "+localarray.get(i).getTimestring();
            if(date.equals(format)){
                updateMyActivity(context,"alaram");

             //   Toast.makeText(context, "notify " + intent.getStringExtra("time") , Toast.LENGTH_LONG).show();
                String Title = intent.getStringExtra("title");
                String time = intent.getStringExtra("time");
                Intent x = new Intent(context, Alert.class);
                x.putExtra("title",localarray.get(i).getRoomno());
                x.putExtra("time",localarray.get(i).getTimestring());
                x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(x);
            }



        }
*/

    }
    static void updateMyActivity(Context context, String message) {

        Intent intent = new Intent("com.viralops.touchlessfoodordering.alaram2");
        context.sendBroadcast(intent);
    }
}

