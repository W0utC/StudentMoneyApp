package com.example.studentmoneyapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.activity.Settings;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AllSettingsClass extends Activity{
    Context context;
    //View view;
    private static AllSettingsClass instance;
    private ArrayList<Setting> list;

    private AllSettingsClass() {
        //this.context = context;
        //this.view = view;
    }

    public ArrayList<Setting> getList() {
        return list;
    }

    public void setList(ArrayList<Setting> list) {
        this.list = list;
    }

    public static AllSettingsClass getInstance() {
        if (instance == null) {
            instance = new AllSettingsClass();
        }
        return instance;
    }

    public Boolean updateSettingList(Setting setting){
        boolean checkIfNameChanged =! list.stream()
                .anyMatch(setting1 -> setting1.
                        getNameOfSetting().
                        equals(setting.nameOfSetting));

        boolean checkIfValueChanged =! list.stream()
                .filter(setting1 -> setting1.getNameOfSetting()
                        .equals(setting.nameOfSetting))
                .anyMatch(setting1 ->
                        setting1.getValueOfSetting() == setting.getValueOfSetting());

        //Log.i("Settings", "checkIfNameChanged:" + checkIfNameChanged);
        //Log.i("Settings", "checkIfValueChanged:" + checkIfValueChanged);
        if(checkIfNameChanged) {
            getList().add(setting);
            //saveSettingToSharedPref(setting);
            return true;
        }else if(checkIfValueChanged){
            for(int i = 0; i<getList().size(); i++){
                if(getList().get(i).getNameOfSetting().equals(setting.getNameOfSetting())){
                    getList().get(i).setValueOfSettingInt(setting.getValueOfSetting());
                }
            }
        }
        return false;
    }

    private void saveSettingToSharedPref(Setting setting){
        SharedPreferences sharedPref = getSharedPreferences(setting.getNameOfSetting(), Context.MODE_PRIVATE); //original: SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Log.i("Settings", "sharedPref: " + sharedPref);
        SharedPreferences.Editor editor = sharedPref.edit();
        Log.i("Settings", "setting.getValueOfSetting: " + setting.getValueOfSetting());
        editor.putInt(getString(R.string.maxWeeklyExpense), setting.getValueOfSetting());
        editor.apply();
    }

    private Activity getActivity() {
        return AllSettingsClass.this;
    }
}
