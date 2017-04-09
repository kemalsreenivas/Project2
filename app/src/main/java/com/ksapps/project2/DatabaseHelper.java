package com.ksapps.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sugnani on 22/02/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Coordinates.db";
    public static final String TABLE_NAME = "Coordinates_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Latitudes";
    public static final String COL_3 = "Longitudes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("Create table "+TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, Latitudes TEXT, Longitudes TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
     public boolean insertData (String latitude,String longitude ){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put(COL_2,latitude);
         contentValues.put(COL_3,longitude);
         long result = db.insert(TABLE_NAME,null,contentValues);
         if(result==-1)
             return false;
         else
             return true;
     }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}
