package com.example.studentmoneyapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.studentmoneyapp.R;

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
