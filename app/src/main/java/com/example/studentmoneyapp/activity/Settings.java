package com.example.studentmoneyapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmoneyapp.R;

public class Settings extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_FOLDER_NAME = "settings";
    private static final String SHARED_PREFERENCES_AUDIO = "audioSetting";
    private static final String SHARED_PREFERENCES_MAX_EXPENSE = "maxWeeklyExpense";
    private EditText maxWeeklyAllowance;
    private CheckBox audio;

    private static final String TAG = Settings.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        maxWeeklyAllowance = (EditText) findViewById(R.id.eTxtWeeklyExpense);
        audio = (CheckBox) findViewById(R.id.checkboxAudio);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillAllFields();
    }

    public void fillAllFields(){
        fillWeeklyAllowance();
        fillAudioCheck();
    }

    public void fillWeeklyAllowance(){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, Context.MODE_PRIVATE);
        int intVal = sharedPref.getInt(SHARED_PREFERENCES_MAX_EXPENSE, -1);

        maxWeeklyAllowance.setText(String.valueOf(intVal));
    }

    public void fillAudioCheck(){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, Context.MODE_PRIVATE);
        boolean BooleanVal = sharedPref.getBoolean(SHARED_PREFERENCES_AUDIO, false);

        if(BooleanVal){
            audio.setChecked(true);
        }
    }

    public void onBtnSaveChanges_Clicked(View caller) {
        if(checkReadySave()) {
            saveSettings();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            finish();
            toast("settings saved");
        }
    }

    public void saveSettings(){
        saveWeeklyExpense();
        saveAudio();
    }

    public void saveAudio(){
        if(audio.isChecked()){
            saveSharedSettings(SHARED_PREFERENCES_AUDIO, true);
        }else{
            saveSharedSettings(SHARED_PREFERENCES_AUDIO, false);
        }

        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, Context.MODE_PRIVATE);
    }

    public void saveWeeklyExpense(){
        saveSharedSettings(SHARED_PREFERENCES_MAX_EXPENSE, getMaxWeeklyAllowance());
    }

    public void saveSharedSettings(String settingName, Object obj){
        if(obj instanceof String){
            saveSharedSettingsStr(settingName, String.valueOf(obj));
        }else if(obj instanceof Boolean){
            saveSharedSettingsBoolean(settingName, (Boolean) obj);
        }else if(obj instanceof Integer){
            saveSharedSettingsInt(settingName, (Integer) obj);
        }else if(obj instanceof Float){
            //saveSharedSettingsFloat(settingName, Float.valueOf(obj));
        }
    }

    public void saveSharedSettingsStr(String settingName, String value){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(settingName, value);
        editor.apply();
        editor.clear();
    }

    public void saveSharedSettingsBoolean(String settingName, Boolean value){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean(settingName, value);
        editor.apply();
        editor.clear();
    }

    public void saveSharedSettingsInt(String settingName, int value){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(settingName, value);
        editor.apply();
        editor.clear();
    }

    public void toast(String message){
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(Settings.this, text, duration);
        toast.show();
    }

    private boolean checkReadySave() {
        if (isEmpty(maxWeeklyAllowance)) {
            maxWeeklyAllowance.setError("fill in!");
        }
        return !isEmpty(maxWeeklyAllowance);
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    public int getMaxWeeklyAllowance(){
        return Integer.parseInt(maxWeeklyAllowance.getText().toString());
    }
}
