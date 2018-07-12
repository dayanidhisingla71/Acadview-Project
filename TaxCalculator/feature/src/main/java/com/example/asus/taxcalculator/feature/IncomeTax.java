package com.example.asus.taxcalculator.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IncomeTax extends AppCompatActivity {
    TextView heading,side,tax_amt,total_amt;
    EditText input;
    String inputValue;
    Button calculate;
    double amount;
    double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_tax);
        heading = (TextView) findViewById(R.id.header);
        side =    (TextView) findViewById(R.id.side);
        tax_amt = (TextView) findViewById(R.id.tax_amount);
        total_amt = (TextView) findViewById(R.id.total_amount);
        input =   (EditText) findViewById(R.id.input);
        calculate = (Button) findViewById(R.id.button);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caculateTax();
            }
        });



    }
    void caculateTax(){
        inputValue = input.getText().toString();
        if(inputValue.isEmpty()){
            Toast.makeText(getApplicationContext(),"Empty Field",Toast.LENGTH_SHORT).show();
        }else{
            /* To convert the input value to integer */
            amount = Double.parseDouble(inputValue);
            if(amount < 250000){
                tax = 0;
                double total = tax+amount;
                tax_amt.setText("Tax on your Income : "+amount+" is : "+tax);
                total_amt.setText("Total Income: "+total);
            }else if(amount >= 250000 && amount <500000){
                tax = amount * 0.05;
                double total = tax+amount;
                tax_amt.setText("Tax on your Income : "+amount+" is : "+tax);
                total_amt.setText("Total Income: "+total);
            }else if( amount >= 500000 && amount < 1000000){
                tax = amount * 0.2;
                double total = tax+amount;
                tax_amt.setText("Tax on your income : "+amount+" is : "+tax);
                total_amt.setText("Total Income would be : "+total);
            }else{
                tax = amount * 0.3;
                double total = tax+amount;
                tax_amt.setText("Tax on your income : "+amount+" is : "+tax);
                total_amt.setText("Total Income would be : "+total);
            }
        }

    }


}
