package com.example.studentmoneyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//INTERESANT OM NOG TO DOEN
// GEEFT EEN ERRORMESSAGE NAARST DE FIELD EN ZGT DAT DIE NIET LEEG MAG ZIJN
//EditText etUserName = (EditText) findViewById(R.id.txtUsername);
//String strUserName = etUserName.getText().toString();
//
// if(TextUtils.isEmpty(strUserName)) {
//    etUserName.setError("Your message");
//    return;
// }

public class Transaction extends AppCompatActivity {
    
    private Spinner category;
    private Spinner paymentMethode;
    private EditText txtAmount;
    private EditText txtStore;

    private RequestQueue requestQueue;
    private String requestURL;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt114/addTransactions";
    private static final String GET_URL = "https://studev.groept.be/api/a21pt114/getTransactions";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_page);

        category = (Spinner) findViewById(R.id.category);
        paymentMethode = (Spinner) findViewById(R.id.paymentMethode);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtStore = (EditText) findViewById(R.id.txtStore);
    }

    public void onBtnSubmit_Clicked(View caller){
        if(checkReadySubmit()) {
            String date = getCurrentDateAndTime();
            String type = category.getSelectedItem().toString();
            String amount = txtAmount.getText().toString();
            String methode = paymentMethode.getSelectedItem().toString();
            String store = txtStore.getText().toString();

            requestURL = SUBMIT_URL + "/" + date
                    + "/" + type
                    + "/" + amount
                    + "/" + methode
                    + "/" + store;
            Log.i("Database", "requestURL: " + requestURL);

            finish();
            storeToDataBase();
        }
    }

    private boolean checkReadySubmit() {
        return !isEmpty(txtAmount) && !isEmpty(txtStore);
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    public String updatePreviewList(){ //probleem met dit is dat er een nieuw activity gemakt wordt en niet het oude aanpast.
        String type = category.getSelectedItem().toString();
        String methode = paymentMethode.getSelectedItem().toString();
        String amount = txtAmount.getText().toString();
        String euro = "\u20ac";

        StringBuilder string = new StringBuilder();
        string.append(getCurrentDate());
        string.append(": " + type);
        string.append(" , " + euro + amount);
        String tempString = string.toString();
        Log.i("Date", tempString);

        //textViewFore.setText(textViewThree.getText());
        //textViewThree.setText(textViewTwo.getText());
        //textViewTwo.setText(textViewOne.getText());
        //textViewOne.setText(tempString);
        return tempString;
    }

    public void storeToDataBase(){
        requestQueue = Volley.newRequestQueue( this );
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                response -> {
                    CharSequence text = "new payment added";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(Transaction.this, text, duration);
                    toast.show();
                    Log.d("Database", "transaction added");
                },

                error -> {
                    CharSequence text = "Unable to place the order";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(Transaction.this, text, duration);
                    toast.show();
                    Log.d("Database", error.getLocalizedMessage(), error);
                }
        );
        requestQueue.add(submitRequest);
    }

    public String getCurrentDate(){
        String date = LocalDate.now().toString();
        //Log.i("Date", "The date at which this is executed is: " + date);
        return date;
    }

    public String getCurrentDateAndTime(){
        /*String time = LocalTime.now().toString();
        time = time.substring(0, time.length()-4);
        String date = getCurrentDate();
        String spaceForURL = "+";

        String tempString = date + spaceForURL + time;
        Log.i("Data", "The date and time at which this is executed is 1: " + tempString);
        return tempString;*/

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss");
        String dateTime = LocalDateTime.now().format(format);
        Log.i("Data", "The date and time at which this is executed is 2: " + dateTime);
        return dateTime;

    }
}
