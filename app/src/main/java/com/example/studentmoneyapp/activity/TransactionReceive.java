package com.example.studentmoneyapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmoneyapp.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionReceive extends AppCompatActivity {

    private Spinner category;
    private Spinner paymentMethode;
    private EditText txtAmount;

    private RequestQueue requestQueue;
    private String requestURL;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt114/addTransactions";
    private static final String GET_URL = "https://studev.groept.be/api/a21pt114/getTransactions";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recieve_transaction_page);

        category = (Spinner) findViewById(R.id.categoryReceive);
        paymentMethode = (Spinner) findViewById(R.id.ReceiveMethode);
        txtAmount = (EditText) findViewById(R.id.txtAmountReceive);
    }

    public void onbtnSubmitReceive_Clicked(View caller) {
        if (checkReadySubmit()) {
            String date = getCurrentDateAndTime();
            String type = category.getSelectedItem().toString();
            String amount = txtAmount.getText().toString();
            String methode = paymentMethode.getSelectedItem().toString();
            String store = "-1";

            requestURL = SUBMIT_URL + "/" + date
                    + "/" + type
                    + "/" + amount
                    + "/" + methode
                    + "/" + store;
            //Log.i("Database", "requestURL: " + requestURL);

            finish();
            storeToDataBase();
        }
    }

    private boolean checkReadySubmit() {
        if (isEmpty(txtAmount)) {
            txtAmount.setError("fill in!");
        }
        return !isEmpty(txtAmount);
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    public void storeToDataBase() {
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                response -> {
                    CharSequence text = "new payment added";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(TransactionReceive.this, text, duration);
                    toast.show();
                    Log.d("Database", "transaction added");
                },

                error -> {
                    CharSequence text = "Unable to place the order";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(TransactionReceive.this, text, duration);
                    toast.show();
                    Log.d("Database", error.getLocalizedMessage(), error);
                }
        );
        requestQueue.add(submitRequest);
    }

    public String getCurrentDate() {
        String date = LocalDate.now().toString();
        //Log.i("Date", "The date at which this is executed is: " + date);
        return date;
    }

    public String getCurrentDateAndTime() {
        /*String time = LocalTime.now().toString();
        time = time.substring(0, time.length()-4);
        String date = getCurrentDate();
        String spaceForURL = "+";

        String tempString = date + spaceForURL + time;
        Log.i("Data", "The date and time at which this is executed is 1: " + tempString);
        return tempString;*/

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss");
        String dateTime = LocalDateTime.now().format(format);
        //Log.i("Data", "The date and time at which this is executed is 2: " + dateTime);
        return dateTime;

    }
}
