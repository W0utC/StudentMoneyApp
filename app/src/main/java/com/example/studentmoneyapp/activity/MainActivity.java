package com.example.studentmoneyapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.studentmoneyapp.network.AllTransactions;
import com.example.studentmoneyapp.R;

public class MainActivity extends AppCompatActivity {
    private TextView txtMain1;
    private TextView txtMain2;
    private TextView txtMain3;
    private TextView txtMain4;
    private TextView txtMain5;

    private RequestQueue requestQueue;
    private TextView txtResponse;
    private String requestURL;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt114/addTransactions";
    private static final String GET_URL = "https://studev.groept.be/api/a21pt114/getTransactions";

    private AllTransactions allTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMain1 = findViewById(R.id.txtMain1);
        txtMain2 = findViewById(R.id.txtMain2);
        txtMain3 = findViewById(R.id.txtMain3);
        txtMain4 = findViewById(R.id.txtMain4);
        txtMain5 = findViewById(R.id.txtMain5);

        allTransactions = new AllTransactions(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        allTransactions.resetTransactions();
        updatePreviewList();
    }

    public void onBtnAddNew_Clicked(View caller) {
        Intent intent = new Intent(this, Transaction.class);
        startActivity(intent);
    }

    public void onBtnViewData_Clicked(View caller) {
        Intent intent = new Intent(this, ViewData.class);
        startActivity(intent);
    }

    public void onBtnResetData_Clicked(View caller) {
        updatePreviewList();
    }

    public void onBtnTest_Clicked(View caller) {
        Intent intent = new Intent(this, ScrollableTest.class);
        startActivity(intent);
    }

    public void onBtnSettingsMain_Clicked(View caller) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    /*public void runAsyncUpdatePreviewList(){
        Log.i("MainActivity", "runAsyncUpdatePreviewList started");

        new TaskRunner(MainActivity.this) {
            @Override
            public void doInBackground() {
                Log.i("MainActivity", "runAsyncUpdatePreviewList started in background");
                allTransactions = new AllTransactions(getApplicationContext());
                //put you background code
                //same like doingBackground
                //Background Thread
            }

            @Override
            public void onPostExecute() {
                Log.i("MainActivity", "runAsyncUpdatePreviewList started on post execute");
                Log.i("MainActivity", "runAsyncUpdatePreviewList on execute size: " + allTransactions.getSingleTransactionList().size());
                updatePreviewList();
                //hear is result part same
                //same like post execute
                //UI Thread(update your UI widget)
            }
        }.execute();
    }*/

    public void updatePreviewList() {
        int length;
        //List<SingleTransaction> transactions = new ArrayList<>(allTransactions);

        int sizeSingleTransactionList = allTransactions.getSingleTransactionList().size();
        Log.i("MainActivity", "size of SingleTransactionList: " + sizeSingleTransactionList);


        if (sizeSingleTransactionList < 1) length = 0;
        else if (sizeSingleTransactionList < 2) length = 1;
        else if (sizeSingleTransactionList < 3) length = 2;
        else if (sizeSingleTransactionList < 4) length = 3;
        else if (sizeSingleTransactionList < 5) length = 4;
        else length = sizeSingleTransactionList - 5;

        for (int i = length; i < sizeSingleTransactionList; i++) {
            Log.i("MainActivity", "value of i : " + i);
            String date = "";
            //String type = "";
            String amount = "";
            //String methode = "";
            String store = "";
            String euro = "\u20ac";

            try {
                //date = LocalDateTime.parse(o.get("date").toString());
                date = allTransactions.getSingleTransactionList().get(i).getDate().toString();
                date = date.substring(0, 10);
                //type = transactions.get(i).getType();
                amount = String.valueOf(allTransactions.getSingleTransactionList().get(i).getAmount());
                //methode = transactions.get(i).getMethode();
                store = allTransactions.getSingleTransactionList().get(i).getStore();

                String tempString = date + ": " + euro + amount + " " + store;
                Log.i("MainActivity", "temp string equals to: " + tempString);
                if (i == sizeSingleTransactionList - 1) txtMain1.setText(tempString);
                if (i == sizeSingleTransactionList - 2) txtMain2.setText(tempString);
                if (i == sizeSingleTransactionList - 3) txtMain3.setText(tempString);
                if (i == sizeSingleTransactionList - 4) txtMain4.setText(tempString);
                if (i == sizeSingleTransactionList - 5) txtMain5.setText(tempString);

                //Log.i("printer", singleTransaction.toString());
                //transactions.add(singleTransaction);
            } catch (Exception e) {
                Log.e("MainActivity", e.toString());
                e.printStackTrace();

            }
        }
    }


    //public void updatePreviewList() { // TODO aanpassen zodat van transactions (arrayList) de data haalt en niet nog is van dataBase
    /*    //transactions.stream().limit(5).forEach();


        //txtMain4.setText(txtMain3.getText().toString());
        //txtMain3.setText(txtMain2.getText().toString());
        //txtMain2.setText(txtMain1.getText().toString());
        try{
            txtMain1.setText(getTransactions().get(getTransactions().size()).toString());
        }catch (IndexOutOfBoundsException e){
            Log.e("exeption", e.getLocalizedMessage(), e);
        }

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, GET_URL, null,

                response -> {
                    String tempString = "";
                    int length;
                    //for (int i = response.length()-1; i > response.length()-4; --i) {
                    //Log.i("txtupdate", "Length of response: " + String.valueOf(response.length()));
                    if(response.length()<1) length = 0;
                    else if(response.length()<2) length = 1;
                    else if(response.length()<3) length = 2;
                    else if(response.length()<4) length = 3;
                    else if(response.length()<5) length = 4;
                    else length = response.length()-5;

                    for (int i = length; i < response.length(); i++) {
                        //Log.i("txtupdate", "value of i : " + String.valueOf(i));
                        String date = "";
                        String type = "";
                        String amount = "";
                        String methode = "";
                        String store = "";
                        String euro = "\u20ac";

                        //JSONObject o = null;
                        JSONObject o;
                        try {
                            o = response.getJSONObject(i);

                            //date = LocalDateTime.parse(o.get("date").toString());
                            date = o.get("date").toString();
                            date = date.substring(0,10);
                            type = o.get("type").toString();
                            amount = o.get("amount").toString();
                            methode = o.get("methode").toString();
                            store = o.get("store").toString();

                            tempString = date + ": " + euro + amount + " " + store;
                            //Log.i("txtUpdate", "temp string equals to: " + tempString);
                            if (i==response.length()-1) txtMain1.setText(tempString);
                            if (i==response.length()-2) txtMain2.setText(tempString);
                            if (i==response.length()-3) txtMain3.setText(tempString);
                            if (i==response.length()-4) txtMain4.setText(tempString);
                            if (i==response.length()-5) txtMain5.setText(tempString);

                            //Log.i("printer", singleTransaction.toString());
                            //transactions.add(singleTransaction);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },

                error -> {
                    txtResponse.setText(error.getLocalizedMessage());
                    Log.d("Database", error.getLocalizedMessage(), error);
                }
        );

        requestQueue.add(getRequest);
    }*/

    //public void getAllFromDataBase() {
        /*requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, GET_URL, null,

                response -> {
                    String info = "";
                    for (int i = 0; i < response.length(); ++i) {
                        LocalDateTime date = null;
                        String type = "";
                        float amount = 0;
                        String methode = "";
                        String store = "";

                        //JSONObject o = null;
                        JSONObject o;
                        try {
                            o = response.getJSONObject(i);

                            //date = LocalDateTime.parse(o.get("date").toString());
                            date = setDateTime(o.get("date").toString());
                            type = o.get("type").toString();
                            amount = Float.parseFloat(o.get("amount").toString());
                            methode = o.get("methode").toString();
                            store = o.get("store").toString();

                            SingleTransaction singleTransaction = new SingleTransaction(date, type, amount, methode, store);
                            //Log.i("printer", singleTransaction.toString());
                            transactions.add(singleTransaction);
                            //Log.i("printer", "length of list: " + transactions.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },

                error -> {
                    txtResponse.setText(error.getLocalizedMessage());
                    Log.d("Database", error.getLocalizedMessage(), error);
                }
        );

        requestQueue.add(getRequest);
    }*/

    //public LocalDateTime setDateTime(String stDate){
        /*
        String dateString = stDate.substring(0, 9);
        String timeString = stDate.substring(11, 18);
        String completeDateString = dateString + "T" + timeString;
        LocalDateTime date = LocalDateTime.parse(completeDateString);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(stDate, formatter);


        //of(int year, int month, int dayOfMonth, int hour, int minute, int second)
        int year = Integer.parseInt(stDate.substring(0, 3));
        int month = Integer.parseInt(stDate.substring(5, 6));;
        int dayOfMonth = Integer.parseInt(stDate.substring(8, 9));;
        int hour = Integer.parseInt(stDate.substring(11, 12));;
        int minute = Integer.parseInt(stDate.substring(14, 15));;
        int second = Integer.parseInt(stDate.substring(17, 18));;
        //LocalDateTime date = toLocalDate(stDate);
        return dateTime;
    }*/

    //public List<SingleTransaction> getTransactions() {
    /*    return transactions;
    }*/

    //public void printer(){
        /*Log.i("printer", "Is started the methode printer");
        Log.i("printer", "length of list: " + transactions.size());
        //Log.i("printer", "first transaction: " + transactions.get(1).getDate().toString() + transactions.get(1).getStore() + transactions.get(1).getAmount());
        for (SingleTransaction object : getTransactions()) {
            Log.i("Printer", object.getDate().toString() + object.getStore() + String.valueOf(object.getAmount()));

        }
        try{
            Log.i("printer", "first transaction: " + transactions.get(1).getDate().toString() + transactions.get(1).getStore() + transactions.get(1).getAmount());
        }catch(IndexOutOfBoundsException e){

        }
    }*/
}