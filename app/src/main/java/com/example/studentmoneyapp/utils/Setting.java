package com.example.studentmoneyapp.utils;

public class Setting {
    String nameOfSetting;
    int valueOfSettingInt;
    //String valueOfSettingString;
    //Boolean valueOfSettingBoolean;

    public Setting (String nameOfSetting, int valueOfSettingInt){
        this.nameOfSetting = nameOfSetting;
        this.valueOfSettingInt = valueOfSettingInt;
    }

    public String getNameOfSetting() {
        return nameOfSetting;
    }

    public void setNameOfSetting(String nameOfSetting) {
        this.nameOfSetting = nameOfSetting;
    }

    public int getValueOfSetting() {
        //if(valueOfSettingInt != -9999) return valueOfSettingInt;
        //else if(valueOfSettingString != null) return valueOfSettingString;
        //else if(valueOfSettingBoolean != null) return valueOfSettingBoolean;
        //return null;
        return valueOfSettingInt;
    }

    public void setValueOfSettingInt(int valueOfSettingInt) {
        this.valueOfSettingInt = valueOfSettingInt;
    }


    /*public void setValueOfSetting(Object valueOfSetting){
        try {
            valueOfSettingInt = (Integer) valueOfSetting;
            return;
        }catch (ClassCastException e){}

        try {
            valueOfSettingString = (String) valueOfSetting;
            return;
        }catch (ClassCastException e){}

        try {
            valueOfSettingBoolean = (Boolean) valueOfSetting;
            return;
        }catch (ClassCastException e){}

    }*/

}
