package com.example.asus.taxcalculator.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button income,gst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        income = (Button) findViewById(R.id.income);
         gst   = (Button) findViewById(R.id.gst);
         applyIncome();
         applyGst();
    }
    void applyIncome(){
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,IncomeTax.class);
                startActivity(intent);
            }
        });
    }
    void applyGst(){
        gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GstListActivity.class);
                startActivity(intent);
            }
        });
    }

}
