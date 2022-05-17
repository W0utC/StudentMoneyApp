package com.example.studentmoneyapp;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AllTransactions{

    private RequestQueue requestQueue;
    private TextView txtResponse;
    private String requestURL;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt114/addTransactions";
    private static final String GET_URL = "https://studev.groept.be/api/a21pt114/getTransactions";
    private final Context context;

    private final ArrayList<SingleTransaction> singleTransactionList; //arrayList of al the transactions

    public AllTransactions(Context context){
        this.context = context;
        singleTransactionList = new ArrayList<>();
        Log.i("AllTransactionsClass", "I started running");
        setTransactions(context);
    }

    public void setTransactions(Context context){
        requestQueue = Volley.newRequestQueue(context);


        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, GET_URL, null,

                response -> {
                    this.singleTransactionList.clear();
                    for (int i = 0; i < response.length(); ++i) {
                        LocalDateTime date = null;
                        String type = "";
                        float amount = 0;
                        String methode = "";
                        String store = "";

                        JSONObject o;
                        try {
                            o = response.getJSONObject(i);

                            date = setDateTime(o.get("date").toString());
                            type = o.get("type").toString();
                            amount = Float.parseFloat(o.get("amount").toString());
                            methode = o.get("methode").toString();
                            store = o.get("store").toString();

                            SingleTransaction singleTransaction = new SingleTransaction(date, type, amount, methode, store);
                            this.singleTransactionList.add(singleTransaction);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    TransactionClass.getInstance().setList(singleTransactionList); //declare singleTransactionList in TransactionClass so it can be accessed from anywhere
                },

                error -> {
                    txtResponse.setText(error.getLocalizedMessage());
                    Log.d("Database", error.getLocalizedMessage(), error);
                }
        );
        requestQueue.add(getRequest);
        Log.i("AllTransactionsClass", "I stopped running");
    }

    public void resetTransactions(){
        clearAllTransactions();
        setTransactions(context);
    }

    public void clearAllTransactions(){
        singleTransactionList.clear();
    }

    public LocalDateTime setDateTime(String stDate){
        /*String dateString = stDate.substring(0, 9);
        String timeString = stDate.substring(11, 18);
        String completeDateString = dateString + "T" + timeString;
        LocalDateTime date = LocalDateTime.parse(completeDateString);*/

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(stDate, formatter);

        return dateTime;
    }

    public List<SingleTransaction> getSingleTransactionList() {
        return singleTransactionList;
    }
}
