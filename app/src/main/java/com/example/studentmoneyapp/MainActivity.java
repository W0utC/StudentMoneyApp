package com.example.studentmoneyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtMain1;
    private TextView txtMain2;
    private TextView txtMain3;
    private TextView txtMain4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMain1 = (TextView) findViewById(R.id.txtMain1);
        txtMain2 = (TextView) findViewById(R.id.txtMain2);
        txtMain3 = (TextView) findViewById(R.id.txtMain3);
        txtMain4 = (TextView) findViewById(R.id.txtMain4);

        Log.i("Main-onCreate", "-------------------");
        Log.i("Main-onCreate", "txt1: " + txtMain1.getText().toString());
        Log.i("Main-onCreate", "txt2: " + txtMain2.getText().toString());
        Log.i("Main-onCreate", "txt3: " + txtMain3.getText().toString());
        Log.i("Main-onCreate", "txt4: " + txtMain4.getText().toString());

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            txtMain4.setText(txtMain3.getText().toString());
            txtMain3.setText(txtMain2.getText().toString());
            txtMain2.setText(txtMain1.getText().toString());

            String newTxtMain1 = extras.getString("txtData");
            txtMain1.setText(newTxtMain1);
        } else{txtMain1.setText("LOL");}


    }

    public void onBtnAddNew_Clicked(View caller){
        Intent intent = new Intent(this, Transaction.class);
        startActivity(intent);
    }

    public void onBtnViewData_Clicked(View caller){
        Intent intent = new Intent(this, ViewData.class);
        startActivity(intent);
    }

    public void updatePreviewList(Bundle savedInstanceState){

    }
}