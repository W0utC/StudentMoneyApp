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

import java.time.LocalDate;

public class Transaction extends AppCompatActivity {

    private TextView textViewOne;
    private TextView textViewTwo;
    private TextView textViewThree;
    private TextView textViewFore;
    private Spinner category;
    private Spinner paymentMethode;
    private EditText txtAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_page);

        textViewOne = (TextView) findViewById(R.id.txtMain1);
        textViewTwo = (TextView) findViewById(R.id.txtMain2);
        textViewThree = (TextView) findViewById(R.id.txtMain3);
        textViewFore = (TextView) findViewById(R.id.txtMain4);
        category = (Spinner) findViewById(R.id.category);
        paymentMethode = (Spinner) findViewById(R.id.paymentMethode);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
    }

    public void onBtnSubmit_Clicked(View caller){
        updatePreviewList();

        /*Intent intent = new Intent(this, MainActivity.updatePreviewList);
        intent.putExtra("txtData", updatePreviewList());
        startActivity(intent);*/

        CharSequence text = "new payment added";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
    public String updatePreviewList(){
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

    public String getCurrentDate(){
        String date = LocalDate.now().toString();
        Log.i("Date", date);
        return date;
    }
}
