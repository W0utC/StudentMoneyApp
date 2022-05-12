package com.example.studentmoneyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class ScrollableTest extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollable_test);
    }

    public void onBtnClickMe_Clicked(View caller) {
        finish();

        String text = "Test page closed";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(ScrollableTest.this, text, duration);
        toast.show();
    }

}
