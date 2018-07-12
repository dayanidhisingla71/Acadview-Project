package com.example.asus.taxcalculator.feature;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GstListActivity extends AppCompatActivity {
    EditText inputAmount;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    HashMap<String, String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_list);
        list = new ArrayList<>();
        itemList = new HashMap<>();
        listView = (ListView) findViewById(R.id.listView);
        Cursor c = null;
        NewDatabaseHelper myDb = new NewDatabaseHelper(getApplicationContext());


        /*Check and create DB*/
        try {
            myDb.createDatabase();
        } catch (IOException e) {
            throw new Error("Unable to create Database");
        }

        /*Open Db*/
        try {
            myDb.openDatabase();
        } catch (SQLException e) {
            throw e;
        }


        /*Fetch all data from the Database*/

        c = myDb.fetchAll("ITEM_LIST", null, null, null, null, null, null);
        if (c != null && c.moveToNext()) {
            do {
                list.add(c.getString(1));
                itemList.put(c.getString(1), c.getString(2));

            } while (c.moveToNext());
        }


        adapter = new ArrayAdapter<>(GstListActivity.this,
                android.R.layout.simple_list_item_1,
                list);
        listView.setAdapter(adapter);


        /*Click listener for List Items*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = listView.getItemAtPosition(position).toString();
                createDialogBox(selected);
            }
        });

    }


    public void createDialogBox(String selected){
        final double tax = findTax(selected);

        /*Code for Dialog Box*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.my_prompt,null);
        builder.setView(dialogView);
        inputAmount = dialogView.findViewById(R.id.edit1);
        builder.setTitle("Calculate GST");
        builder.setMessage("Enter Amount of "+selected);

        builder.setPositiveButton("Calculate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                double enteredAmount = Double.parseDouble(inputAmount.getText().toString());
                double displayAmount = enteredAmount * tax;
                displayTotal(enteredAmount, displayAmount, tax);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                displayTotal(0, 0, 0);
            }
        });

        AlertDialog b = builder.create();
        b.show();

    }



    /*Find tax of that ITEM*/
    public double findTax(String selected){
        for(Map.Entry<String , String>entry : itemList.entrySet()){
            if(selected.equals(entry.getKey())){
                double tax = Double.parseDouble(entry.getValue());
                return tax;
            }
        }
        return 0;
    }


    /*Create new Dialog Box to Display Tax */
    public void displayTotal(double amount, double displayAmount,double gst){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Total Amount");
        if(amount == 0 && displayAmount == 0 && gst == 0){
            builder.setMessage("Total amount: 0");
        }else{
            DecimalFormat df2 = new DecimalFormat(".##");
            builder.setMessage("Gst on : "+amount+" is "+gst+" ( "+df2.format(displayAmount)+")"+
                    "\n"
                    +"Total Amount:"+(displayAmount+amount));
        }

        builder.show();
    }
}

