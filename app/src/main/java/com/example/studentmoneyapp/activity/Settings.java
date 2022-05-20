package com.example.studentmoneyapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.utils.AllSettingsClass;
import com.example.studentmoneyapp.utils.Setting;

import java.lang.reflect.Field;

public class Settings extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_FOLDER_NAME = "settings";
    private EditText maxWeeklyExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        maxWeeklyExpense = (EditText) findViewById(R.id.eTxtWeeklyExpense);
    }

    public void onBtnSaveChanges_Clicked(View caller) {
        saveSettings();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast("settings saved");
    }

    public void Toast(String message){
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(Settings.this, text, duration);
        toast.show();
    }

    public void saveSettings(){
        Setting setting = new Setting("maxWeeklyExpense", getMaxWeeklyExpense());
        AllSettingsClass.getInstance().updateSettingList(setting);
        saveSettingToSharedPref(setting);
    }

    public int getMaxWeeklyExpense(){
        return Integer.parseInt(maxWeeklyExpense.getText().toString());
    }

    private void saveSettingToSharedPref(Setting setting){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, Context.MODE_PRIVATE);

        //Log.i("Settings", "--------------------------------------------------------------------");
        SharedPreferences.Editor editor = sharedPref.edit();
        //Log.i("Settings", "setting.getValueOfSetting: " + setting.getValueOfSetting());
        editor.putInt(setting.getNameOfSetting(), setting.getValueOfSetting());
        editor.apply();

        Setting setMaxWeeklyExpense = getFromSharedPref("maxWeeklyExpense");
        //Log.i("Settings", "sharedPref after upload: " + setMaxWeeklyExpense);
    }

    public Setting getFromSharedPref(String nameOfSetting){
        int defaultValue = -9999;

        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FOLDER_NAME, Context.MODE_PRIVATE);
        int intVal = sharedPref.getInt(nameOfSetting, defaultValue);

        //Log.i("Settings", "new setting: " + nameOfSetting + ", " + intVal);
        Setting setting = new Setting(nameOfSetting, intVal);
        return setting;
    }
}
