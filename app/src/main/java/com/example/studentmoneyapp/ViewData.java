package com.example.studentmoneyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewData extends AppCompatActivity {
    TextView txtHistory;
    AllTransactions allTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);


        allTransactions = new AllTransactions(getApplicationContext());

        txtHistory = (TextView) findViewById(R.id.txtHistory);
        txtHistory.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onBtnView_Clicked(View caller){
        //test();
        setTxtHistory();
    }

    public void test(){
        Log.i("ViewDate", "I was started");
        String str = "";


        Log.i("ViewDate", "size of single transaction list: " + String.valueOf(allTransactions.getSingleTransactionList().size()));
        Log.i("ViewDate", "date of first transaction in list: " + allTransactions.getSingleTransactionList().get(1).getDate().toString());
        str = allTransactions.getSingleTransactionList().get(1).getDate().toString();

        //txtTest.setText(str);
    }

    public void setTxtHistory(){
        for(int i = 0; i<allTransactions.getSingleTransactionList().size(); i++){
            String str = getTxtHistory(i);
            txtHistory.append(str + '\n');
        }

    }

    public String getTxtHistory(int pos){
        String tempStr = "";

        String date = allTransactions.getSingleTransactionList().get(pos).getDate().toString();
        date = date.substring(0,10);
        String amount = String.valueOf(allTransactions.getSingleTransactionList().get(pos).getAmount());
        String store = allTransactions.getSingleTransactionList().get(pos).getStore();
        String euro = "\u20ac";

        tempStr = date + ": " + euro + amount + " " + store;
        return tempStr;
    }
}
