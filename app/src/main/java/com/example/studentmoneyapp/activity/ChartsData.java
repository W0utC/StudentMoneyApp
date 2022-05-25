package com.example.studentmoneyapp.activity;

import static java.lang.Math.abs;
import static java.lang.Math.round;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.LinearGauge;
import com.anychart.core.cartesian.series.Line;
import com.anychart.core.lineargauge.pointers.Marker;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Orientation;
import com.anychart.enums.ScaleStackMode;
import com.anychart.graphics.vector.GradientKey;
import com.anychart.scales.Linear;
import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.model.SingleTransaction;
import com.example.studentmoneyapp.utils.ChartDataColStackMonth;
import com.example.studentmoneyapp.utils.ChartsDataHandler;
import com.example.studentmoneyapp.utils.TransactionClass;

import java.util.ArrayList;
import java.util.List;

public class ChartsData extends AppCompatActivity {
    ArrayList<SingleTransaction> transactions; // TODO test

    ChartsDataHandler chartsDataHandler;
    private static final String TAG = ChartsData.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charts_data_page);

        chartsDataHandler = new ChartsDataHandler(this);
        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); // TODO test

        setupChartLinearGaugeCurrentEx();
        setupChartColumnStackedMonthEx();
    }


    public void setupChartLinearGaugeCurrentEx(){
        setupChartLinearGaugeCurrentExWeek();
        setupChartLinearGaugeCurrentExMonth();
    }

    public void setupChartLinearGaugeCurrentExWeek(){
        AnyChartView chatLinearGauge = findViewById(R.id.chartLinearGaugeCurrExWeek);
        APIlib.getInstance().setActiveAnyChartView(chatLinearGauge); //REALLY important for good functioning!!!

        LinearGauge linearGauge = AnyChart.linear();
        linearGauge.animation(true);
        linearGauge.addPointer(0);

        linearGauge.title("this week").padding(30, 0, 10, 0);
        linearGauge.title()
                .orientation(Orientation.BOTTOM)
                .fontColor("#212121")
                .fontSize("18dp")
                .margin("5dp");

        GradientKey gradientKey = new GradientKey("#9ccc65", 1 ,1);
        GradientKey normal = new GradientKey("#9ccc65", 1 ,1);
        GradientKey warning = new GradientKey("#FF8C00", 1 ,1);
        GradientKey danger = new GradientKey("#FF6347", 1 ,1);

        double val = abs(round(chartsDataHandler.getSumOfCurrentWeekTransactions()));
        if(val <= 50) gradientKey = normal;
        else if(val <= 100) gradientKey = warning;
        else if(val > 100) gradientKey = danger;

        linearGauge
                .scaleBar(0)
                .width("10.5")
                .offset("0%")
                .fill(gradientKey);

        linearGauge.axis("0.5%").globalOffset("-1%").scale().maximum(150).minimum(0);

        linearGauge.axis(0).offset("-10.5%").orientation(Orientation.LEFT);

        // Set marker point
        Marker marker = linearGauge.getPointer(0).getGauge().marker(0);
        marker.color("#7c868e").offset("11.5%").type("triangle-left");

        // Set label for marker point
        marker
                .labels()
                .enabled(true)
                .position("right-center")
                .offsetX(0)
                .anchor("left-center")
                .fontSize(18)
                .fontColor("#212121");

        List<DataEntry> dataEntry = new ArrayList<>();
        dataEntry.add(new ValueDataEntry("week", abs(round(chartsDataHandler.getSumOfCurrentWeekTransactions()))));
        linearGauge.data(dataEntry);

        chatLinearGauge.setChart(linearGauge);
    }

    public void setupChartLinearGaugeCurrentExMonth(){
        AnyChartView chartLinearGauge = findViewById(R.id.chartLinearGaugeCurrExMonth);
        APIlib.getInstance().setActiveAnyChartView(chartLinearGauge); //REALLY important for good functioning!!!

        LinearGauge linearGauge = AnyChart.linear();
        linearGauge.animation(true);

        linearGauge.title("this month").padding(30, 0, 10, 0);
        linearGauge.title()
                .orientation(Orientation.BOTTOM)
                .fontColor("#212121")
                .fontSize("18dp")
                .margin("5dp");

        linearGauge.addPointer(0);

        GradientKey gradientKey = new GradientKey("#9ccc65", 1 ,1);
        GradientKey normal = new GradientKey("#9ccc65", 1 ,1);
        GradientKey warning = new GradientKey("#FF8C00", 1 ,1);
        GradientKey danger = new GradientKey("#FF6347", 1 ,1);

        double val = abs(round(chartsDataHandler.getSumOfCurrentWeekTransactions()));
        if(val <= 200) gradientKey = normal;
        else if(val <= 350) gradientKey = warning;
        else if(val > 350) gradientKey = danger;


        linearGauge
                .scaleBar(0)
                .width("10.5")
                .offset("0%")
                .fill(gradientKey);

        linearGauge.axis("0.5%").globalOffset("-1%").scale().maximum(400).minimum(0);
        linearGauge.axis(0).offset("-10.5%").orientation(Orientation.LEFT);

        // Set marker point
        Marker marker = linearGauge.getPointer(0).getGauge().marker(0);
        marker.color("#7c868e").offset("11.5%").type("triangle-left");

        // Set label for marker point
        marker
                .labels()
                .enabled(true)
                .position("right-center")
                .offsetX(0)
                .anchor("left-center")
                .fontSize(18)
                .fontColor("#212121");

        List<DataEntry> dataEntry = new ArrayList<>();
        dataEntry.add(new ValueDataEntry("month", abs(round(chartsDataHandler.getSumOfCurrentMonthTransactionsEx()))));
        linearGauge.data(dataEntry);

        chartLinearGauge.setChart(linearGauge);
    }


    public void setupChartColumnStackedMonthEx(){
        setupChartColumnStackedMonthExAbs();
    }

    public void setupChartColumnStackedMonthExAbs(){
        AnyChartView columnStacked = findViewById(R.id.chartColumnStackedMonthExAbs);//original: AnyChartView columnStacked = findViewById(R.id.chartColumnStackedMonthExAbs);

        APIlib.getInstance().setActiveAnyChartView(columnStacked); //REALLY important for good functioning!!!

        Cartesian cartesian = AnyChart.cartesian();

        cartesian.animation(true);

        cartesian.title("Monthly expenses in absolute values");

        cartesian.yScale().stackMode(ScaleStackMode.VALUE);

        Linear scalesLinear = Linear.instantiate();
        scalesLinear.minimum(0);
        scalesLinear.maximum(700);
        scalesLinear.ticks("{ interval: 100 }");

        com.anychart.core.axes.Linear extraYAxis = cartesian.yAxis(1d);
        extraYAxis.orientation(Orientation.RIGHT)
                .scale(scalesLinear);
        extraYAxis.labels()
                .padding(0d, 0d, 0d, 5d)
                .format("{%Value}");

        List<DataEntry> data = new ArrayList<>();
        int year = chartsDataHandler.getCurrentYear();

        for(int i = 1; i<13; i++){
            ArrayList<Double> sumList =
                    new ArrayList<>(chartsDataHandler.getSumListOfMothCat(year, i));

            data.add(new ChartDataColStackMonth(java.time.Month.of(i).toString(), //original: new ChartDataColStackMonth(java.time.Month.of(i).toString(),
                    (Number) sumList.get(0),
                    (Number) sumList.get(1),
                    (Number) sumList.get(2),
                    (Number) sumList.get(3),
                    (Number) sumList.get(4),
                    (Number) sumList.get(5)));
        }

        Set set = Set.instantiate();
        set.data(data);
        Mapping column1Data = set.mapAs("{ x: 'food', value: 'value1' }");
        Mapping column2Data = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping column3Data = set.mapAs("{ x: 'goingOut', value: 'value3' }");
        Mapping column4Data = set.mapAs("{ x: 'x', value: 'value4' }");
        Mapping column5Data = set.mapAs("{ x: 'x', value: 'value5' }");
        Mapping column6Data = set.mapAs("{ x: 'x', value: 'value6' }");

        cartesian.column(column1Data).name("food");
        cartesian.crosshair(true);
        cartesian.column(column2Data).name("horeca");
        cartesian.column(column3Data).name("goingOut");
        cartesian.column(column4Data).name("sport");
        cartesian.column(column5Data).name("tech");
        cartesian.column(column6Data).name("varia");

        cartesian.legend()
                .enabled(true)
                .fontSize(13)
                .padding(0, 0, 20, 0)
                .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE);

        // set titles for axes
        cartesian.xAxis("1dp").title("months");

        cartesian
                .tooltip()
                .positionMode("point")
                .position("center-top")
                .anchor("center-bottom")
                .offsetX(0)
                .offsetY(5)
                .titleFormat("{%X}")
                .format("{%SeriesName} : ${%Value}{groupsSeparator: }");

        columnStacked.setChart(cartesian);
    }

    public void testChartColumnStacked(){ //TODO test
        AnyChartView anyChartView = findViewById(R.id.chartColumnStackedMonthExAbs);
        APIlib.getInstance().setActiveAnyChartView(anyChartView); //REALLY important for good functioning!!!
        //anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.cartesian();

        cartesian.animation(true);

        cartesian.title("Combination of Stacked Column and Line Chart (Dual Y-Axis)");

        cartesian.yScale().stackMode(ScaleStackMode.VALUE);

        Linear scalesLinear = Linear.instantiate();
        scalesLinear.minimum(0d);
        scalesLinear.maximum(100d);
        scalesLinear.ticks("{ interval: 20 }");

        com.anychart.core.axes.Linear extraYAxis = cartesian.yAxis(1d);
        extraYAxis.orientation(Orientation.RIGHT)
                .scale(scalesLinear);
        extraYAxis.labels()
                .padding(0d, 0d, 0d, 5d)
                .format("{%Value}%");

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomDataEntry("P1", 96.5, 2040, 1200, 1600));
        data.add(new CustomDataEntry("P2", 77.1, 1794, 1124, 1724));
        data.add(new CustomDataEntry("P3", 73.2, 2026, 1006, 1806));
        data.add(new CustomDataEntry("P4", 61.1, 2341, 921, 1621));
        data.add(new CustomDataEntry("P5", 70.0, 1800, 1500, 1700));
        data.add(new CustomDataEntry("P6", 60.7, 1507, 1007, 1907));
        data.add(new CustomDataEntry("P7", 62.1, 2701, 921, 1821));
        data.add(new CustomDataEntry("P8", 75.1, 1671, 971, 1671));
        data.add(new CustomDataEntry("P9", 80.0, 1980, 1080, 1880));
        data.add(new CustomDataEntry("P10", 54.1, 1041, 1041, 1641));
        data.add(new CustomDataEntry("P11", 51.3, 813, 1113, 1913));
        data.add(new CustomDataEntry("P12", 59.1, 691, 1091, 1691));

        Set set = Set.instantiate();
        set.data(data);
        Mapping lineData = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping column1Data = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping column2Data = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping column3Data = set.mapAs("{ x: 'x', value: 'value4' }");

        cartesian.column(column1Data);
        cartesian.crosshair(true);

        Line line = cartesian.line(lineData);
        line.yScale(scalesLinear);

        cartesian.column(column2Data);

        cartesian.column(column3Data);

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
        }
    }
}
