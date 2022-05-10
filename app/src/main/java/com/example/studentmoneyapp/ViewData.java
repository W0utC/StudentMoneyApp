package com.example.studentmoneyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewData extends AppCompatActivity {
    TextView txtTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);

        txtTest = (TextView) findViewById(R.id.txtTest);
    }

    public void onBtnTest_Clicked(View caller){
        test();
    }

    public void test(){
        Log.i("ViewDate", "I was started");
        String str = "";

        AllTransactions allTransactions = new AllTransactions(getApplicationContext());
        Log.i("ViewDate", String.valueOf(allTransactions.getSingleTransactionList().size()));
        Log.i("ViewDate", allTransactions.getSingleTransactionList().get(1).toString());
        str = allTransactions.getSingleTransactionList().get(1).toString();

        txtTest.setText(str);
    }

}
