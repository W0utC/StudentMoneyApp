package com.example.studentmoneyapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewData extends AppCompatActivity {
    TextView txtHistory;
    AllTransactions allTransactions;
    ArrayList<SingleTransaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);


        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); //declare array list and put in the transactions
        //allTransactions = new AllTransactions(getApplicationContext());

        txtHistory = (TextView) findViewById(R.id.txtHistory);
        txtHistory.setMovementMethod(new ScrollingMovementMethod());

        setTxtHistory();
        //test();
    }

    public void onBtnView_Clicked(View caller){
        setTxtHistory();
    }

    public void setTxtHistory(){
        for(int i = 0; i<transactions.size(); i++){ //original: for(int i = 0; i<allTransactions.getSingleTransactionList().size(); i++){
            String str = getTxtHistory(i);
            txtHistory.append(str + '\n');
        }

    }

    public String getTxtHistory(int pos){
        String tempStr = "";

        String date = transactions.get(pos).getDate().toString(); //original: String date = allTransactions.getSingleTransactionList().get(pos).getDate().toString();
        date = date.substring(0,10);
        String amount = String.valueOf(transactions.get(pos).getAmount()); //original: String amount = String.valueOf(allTransactions.getSingleTransactionList().get(pos).getAmount());
        String store = transactions.get(pos).getStore(); //original: String store = allTransactions.getSingleTransactionList().get(pos).getStore();
        String euro = "\u20ac";

        tempStr = date + ": " + euro + amount + " " + store;
        return tempStr;
    }

    public void test(){
        Log.i("ViewData", "------------------------");

        //Log.i("ViewData", "first element in TransactionClass: " + TransactionClass.getInstance().getList().get(0).toString());
        Log.i("ViewData", "first element in TransactionClass: " + transactions.toString());
        //Log.i("ViewData", "first element in AllTransactions: " + allTransactions.getSingleTransactionList().get(0));

        for(SingleTransaction s : transactions){
            String date = s.getDate().toString();
            date = date.substring(0,10);
            String amount = String.valueOf(s.getAmount());
            String store = s.getStore();
            String euro = "\u20ac";

            String tempStr = date + ": " + euro + amount + " " + store;
            Log.i("ViewData", tempStr);
        }

        Log.i("ViewData", "------------------------");
    }

}
