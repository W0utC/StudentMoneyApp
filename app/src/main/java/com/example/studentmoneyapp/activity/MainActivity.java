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
    //

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

    //-----------------------------------------------------------------------------------------------------------------

    //public void updatePreviewList() { // TODO aanpassen
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