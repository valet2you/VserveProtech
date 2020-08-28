package com.viralops.touchlessfoodordering.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Dashboard1;
import com.viralops.touchlessfoodordering.Model.Spa_dashboard;

import java.util.ArrayList;

/**
 * Created by Dominic on 07/04/2015.
 */
public class DbHelper1 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sparemind";
    public static final String TABLE_NAME = "SPAalarm";
    public static final String C_ID = "_id";
    public static final String O_ID = "o_id";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String DETAIL = "description";
    public static final String TIME = "time";
    public static final String DATE = "date";
    public static final int VERSION = 4;

    private final String createDB = "create table if not exists " + TABLE_NAME + " ( "
    + C_ID + " integer primary key autoincrement, "
    + TITLE + " text, "
    + O_ID + " text, "
    + DETAIL + " text, "
    + TYPE + " text, "
    + TIME + " text, "
    + DATE + " text)";

    public DbHelper1(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
    }
    public ArrayList<Spa_dashboard.Data> getAllCotacts() {
        ArrayList<Spa_dashboard.Data> array_list = new ArrayList<Spa_dashboard.Data>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            Spa_dashboard.Data queue_data=new Spa_dashboard.Data();
            queue_data.setId(res.getString(res.getColumnIndex(C_ID)));
            queue_data.setTypenotify(res.getString(res.getColumnIndex(TYPE)));
            queue_data.setRoomno(res.getString(res.getColumnIndex(TITLE)));
            queue_data.setDatenew(res.getString(res.getColumnIndex(DATE)));
            queue_data.setTimestring(res.getString(res.getColumnIndex(TIME)));
            queue_data.setO_id(res.getString(res.getColumnIndex(O_ID)));
            queue_data.setDetail(res.getString(res.getColumnIndex(DETAIL)));
            array_list.add(queue_data);
            res.moveToNext();
        }
        return array_list;
    }

}
