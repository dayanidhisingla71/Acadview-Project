package com.example.asus.taxcalculator.feature;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NewDatabaseHelper extends SQLiteOpenHelper {

    String db_path = "";
    public static String db_name = "taxdata.db";
    SQLiteDatabase myDatabase;
    Context context;

    public  NewDatabaseHelper(Context context){
        super(context, db_name, null, 10);
        this.context = context;
        this.db_path = context.getDatabasePath(db_name).toString();
        Log.e("Path of Database",db_path);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            try{
                copyData();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    public Cursor fetchAll(String table, String [] columns, String selection, String [] selectionArgs, String groupBy, String having, String orderBy){
        return myDatabase.query("ITEM_LIST",null,null, null, null, null, null);
    }


    public void createDatabase() throws IOException{
        boolean dbExists = checkDataBase();
        if(dbExists){

        }else{
            this.getReadableDatabase();

            try{
                copyData();
            }catch (IOException e){
                throw new Error("Error Copying Database");
            }
        }
    }

    public boolean checkDataBase(){
        SQLiteDatabase db = null;
        try{
            String myPath = db_path;
            Log.e("Databse path :",db_path);
            db = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLException e){
            Log.e("Error at Check databse",e.toString());
        }

        return db != null? true : false;

    }


    public void  copyData() throws IOException{
        InputStream myInput = context.getAssets().open(db_name);
        Log.e("File opened","true");
        String outFileName = db_path + db_name;
        Log.e("File Name is ",outFileName);
        OutputStream outFile = new FileOutputStream(outFileName);

        byte[] buffer = new byte[10];
        int length;

        while((length = myInput.read(buffer)) > 0){
            outFile.write(buffer, 0, length);
        }
        outFile.flush();
        outFile.close();
        myInput.close();

    }


    public void openDatabase() throws SQLException{
        String myPath = db_path + db_name;
        Log.e("openDatabase(),Db path",myPath);
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    public void close(){
        if(myDatabase != null)
            myDatabase.close();
        super.close();
    }



}
