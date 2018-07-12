package com.example.asus.taxcalculator.feature;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String db_name = "GST.db";
    final static String tablename = "gst_data";
    final static String col1 = "ID";
    final static String col2 = "NAME";
    final static String col3 = "PRICE";
    public DatabaseHelper(Context context){

        super(context,db_name,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*Check every time if DB exists*/
        db.execSQL("CREATE TABLE IF NOT EXISTS "+tablename+"(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARHCAR, price INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tablename);
        onCreate(db);
    }


    /*INSERT DATA*/
    public boolean insert(TreeMap<String,String> item_data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try{
            for(Map.Entry<String , String> entry : item_data.entrySet()){
                contentValues.put(col2,entry.getKey());
                contentValues.put(col3,entry.getValue());
                db.insert(tablename,null,contentValues);
            }

        }catch (Exception e){
            Log.d("Error Occured","While Inserting Dataaa");
        }


        return true;
    }
    /*Return All data*/
    public ArrayList<String> fetchAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+tablename,null);
        if(cursor != null && cursor.moveToNext()){
            do{
                list.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public Cursor checkDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+tablename,null);
        return cursor;
    }

    public int deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tablename, "1", null);
    }

    public Cursor search(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+tablename+" WHERE NAME = "+"'"+item+"'",null);
        return cursor;
    }


}
