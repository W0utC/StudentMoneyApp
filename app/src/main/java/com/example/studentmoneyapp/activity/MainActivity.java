package com.example.studentmoneyapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmoneyapp.network.AllTransactions;
import com.example.studentmoneyapp.R;

public class MainActivity extends AppCompatActivity {
    private TextView txtMain1;
    private TextView txtMain2;
    private TextView txtMain3;
    private TextView txtMain4;
    private TextView txtMain5;

    private AllTransactions allTransactions;
    MediaPlayer mp;

    private static final String SHARED_PREFERENCES_FOLDER_NAME = "settings";
    private static final String AUDIO_SETTING = "audioSetting";

    private static final String TAG = MainActivity.class.getSimpleName();

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
        playAudio();
    }

    @Override
    protected void onResume() {
        super.onResume();

        allTransactions.resetTransactions();
        updatePreviewList();

        playAudio();
    }

    public void onBtnAddNew_Clicked(View caller) {
        pauseAudio();

        Intent intent = new Intent(this, Transaction.class);
        startActivity(intent);
    }

    public void onBtnViewData_Clicked(View caller) {
        Log.i(TAG, "get check on click: " + allTransactions.getCheck());

        if(allTransactions.getCheck()) {
            Log.i(TAG, "onBtnView activated");
            pauseAudio();

            Intent intent = new Intent(this, ViewData.class);
            startActivity(intent);
        }else{
            toast("can't reach the sever at the moment");
        }
    }

    public void onBtnResetData_Clicked(View caller) {
        updatePreviewList();
        if(!allTransactions.getCheck() && isEmpty(txtMain2)){ //&& isEmpty(txtMain2)
            txtMain1.setText("failed to connect to the server");
        }
    }

    public void onBtnSettingsMain_Clicked(View caller) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void updatePreviewList() {
        int length;
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
            String amount = "";
            String store = "";
            String euro = "\u20ac";

            try {
                date = allTransactions.getSingleTransactionList().get(i).getDate().toString();
                date = date.substring(0, 10);
                amount = String.valueOf(allTransactions.getSingleTransactionList().get(i).getAmount());
                store = allTransactions.getSingleTransactionList().get(i).getStore();

                String tempString = date + ": " + euro + amount + " " + store;
                Log.i("MainActivity", "temp string equals to: " + tempString);
                if (i == sizeSingleTransactionList - 1) txtMain1.setText(tempString);
                if (i == sizeSingleTransactionList - 2) txtMain2.setText(tempString);
                if (i == sizeSingleTransactionList - 3) txtMain3.setText(tempString);
                if (i == sizeSingleTransactionList - 4) txtMain4.setText(tempString);
                if (i == sizeSingleTransactionList - 5) txtMain5.setText(tempString);

            } catch (Exception e) {
                Log.e("MainActivity", e.toString());
                e.printStackTrace();
            }
        }
    }

    private boolean isEmpty(TextView txt) {
        return txt.getText().toString().trim().length() <= 0;
    }

    public void playAudio(){
        if(checkAudioChecked()){
            mp = MediaPlayer.create(this, R.raw.one_hour_angrybirds_theme_song);
            mp.start();
        }
        else if(mp != null) {
            mp.stop();
        }
    }

    public void pauseAudio(){
        try {
            if(mp != null){
                mp.pause();
            }
        }catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    public boolean checkAudioChecked(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, MODE_PRIVATE);
        Log.i(TAG, "audioSetting retrieved: " + sharedPreferences.getBoolean(AUDIO_SETTING, false));
        boolean check = sharedPreferences.getBoolean(AUDIO_SETTING, false);
        return check;
    }

    public void toast(String message){
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
}