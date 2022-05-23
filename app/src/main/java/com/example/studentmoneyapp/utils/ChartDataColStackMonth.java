package com.example.studentmoneyapp.utils;

import com.anychart.chart.common.dataentry.ValueDataEntry;

public class ChartDataColStackMonth extends ValueDataEntry {

    public ChartDataColStackMonth(String x, Number foodSum, Number horecaSum, Number goingOutSum, Number sportSum, Number techSum, Number variaSum) {
        super(x, foodSum);
        setValue("value2", horecaSum);
        setValue("value3", goingOutSum);
        setValue("value4", sportSum);
        setValue("value5", techSum);
        setValue("value6", variaSum);
    }
}