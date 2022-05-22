package com.example.studentmoneyapp.activity;

import static java.lang.Math.abs;
import static java.lang.Math.round;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartFormat;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.LinearGauge;
import com.anychart.charts.Pie;
import com.anychart.core.lineargauge.pointers.Marker;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Orientation;
import com.anychart.enums.Position;
import com.anychart.enums.ScaleTypes;
import com.anychart.scales.Base;
import com.anychart.scales.Linear;
import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.model.ChartCurrentGaugeCurrEx;
import com.example.studentmoneyapp.model.SingleTransaction;
import com.example.studentmoneyapp.utils.ChartsDataHandler;
import com.example.studentmoneyapp.utils.TransactionClass;

import java.util.ArrayList;
import java.util.List;

public class ChartsData extends AppCompatActivity {
    ArrayList<SingleTransaction> transactions; // TODO test

    AnyChartView chartLinearGaugeCurrentEx;
    AnyChartView chartColumnsStackedMonthExAbs;
    AnyChartView chartColumnsStackedMonthExPr;
    AnyChartView chartBarStackMonthExAbs;
    AnyChartView chartBarStackMonthExVsIn;
    AnyChartView chartPieMonthExAbs;
    AnyChartView chartPieMonthExPr;
    AnyChartView chartPieYearExAbs;
    AnyChartView chartPieYearExPr;

    ChartsDataHandler chartsDataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charts_data_page);

        chartsDataHandler = new ChartsDataHandler();

        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); // TODO test


        setupChartLinearGaugeCurrentEx();

    }


    public void setupChartLinearGaugeCurrentEx(){
        AnyChartView chartLinearGaugeCurrentEx = findViewById(R.id.chartLinearGaugeCurrEx);
        APIlib.getInstance().setActiveAnyChartView(chartLinearGaugeCurrentEx); //REALLY important for good functioning!!!

        LinearGauge linearGauge = AnyChart.linear();



        linearGauge.title("week").padding(30, 0, 10, 0);
        linearGauge.title().orientation(Orientation.BOTTOM);
        linearGauge.title().fontColor("#212121");
        linearGauge.title().fontSize("18dp");

        linearGauge
                .scaleBar(0)
                .width("6dp")
                .fill("#9ccc65", 0.4);

        linearGauge
                .scaleBar(1)
                .width("10dp")
                .fill("#9ccc65", 0.4);

        linearGauge.axis("0.5%").globalOffset("-1%").scale().maximum(100).minimum(0);
        linearGauge.label(16);

        // Set marker point
        Marker marker = linearGauge.marker(0);
        marker.color("#7c868e").offset("8.5%").type("triangle-left");

        // Set label for marker point
        marker
                .labels()
                .enabled(true)
                .position("right-center")
                .offsetX(0)
                .anchor("left-center")
                .fontSize(18)
                .fontColor("#212121");


        String[] categories = new String[] {"week", "month"};
        //float[] chartSumData = new float[] {20, 50};
        double[] chartSumData = new double[] {round(chartsDataHandler.getSumOfCurrentWeekTransactions()), 50};

        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i=0;i<chartSumData.length; ++i){
            dataEntries.add(new ValueDataEntry(categories[i], abs(chartSumData[i])));
        }

        linearGauge.data(dataEntries);

        chartLinearGaugeCurrentEx.setChart(linearGauge);
    }

    public void setChart(){
        AnyChartView chartGauge = findViewById(R.id.chartLinearGaugeCurrEx);
        //ViewHolder.gaugeChart.setProgressBar(findViewById(R.id.progress_bar));

        LinearGauge linearGauge = AnyChart.linear();

        // TODO data
        linearGauge.data(new SingleValueDataSet(new Integer[] { 2 }));

        linearGauge.tooltip()
                .useHtml(true)
                .format(
                        "function () {\n" +
                                "          return this.value + '&deg;' + 'C' +\n" +
                                "            ' (' + (this.value * 1.8 + 32).toFixed(1) +\n" +
                                "            '&deg;' + 'F' + ')'\n" +
                                "    }");

        linearGauge.label(0).useHtml(true);
        linearGauge.label(0)
                .text("C&deg;")
                .position(Position.LEFT_BOTTOM)
                .anchor(Anchor.LEFT_BOTTOM)
                .offsetY("20px")
                .offsetX("38%")
                .fontColor("black")
                .fontSize(17);

        linearGauge.label(1)
                .useHtml(true)
                .text("F&deg;")
                .position(Position.RIGHT_BOTTOM)
                .anchor(Anchor.RIGHT_BOTTOM)
                .offsetY("20px")
                .offsetX("47.5%")
                .fontColor("black")
                .fontSize(17);

        Base scale = linearGauge.scale()
                .minimum(-30)
                .maximum(40);
//                .setTicks

        linearGauge.axis(0).scale(scale);
        linearGauge.axis(0)
                .offset("-1%")
                .width("0.5%");

        linearGauge.axis(0).labels()
                .format("{%Value}&deg;")
                .useHtml(true);

        linearGauge.thermometer(0)
                .name("Thermometer")
                .id(1);

        linearGauge.axis(0).minorTicks(true);
        linearGauge.axis(0).labels()
                .format(
                        "function () {\n" +
                                "    return '<span style=\"color:black;\">' + this.value + '&deg;</span>'\n" +
                                "  }")
                .useHtml(true);

        linearGauge.axis(1).minorTicks(true);
        linearGauge.axis(1).labels()
                .format(
                        "function () {\n" +
                                "    return '<span style=\"color:black;\">' + this.value + '&deg;</span>'\n" +
                                "  }")
                .useHtml(true);
        linearGauge.axis(1)
                .offset("3.5%")
                .orientation(Orientation.RIGHT);

        Linear linear = Linear.instantiate();
        linear.minimum(-20)
                .maximum(100);
//                .setTicks
        //linearGauge.axis(1).scale(linear);

        chartGauge.setChart(linearGauge);
    }

    public void setupPieChart(){ // TODO test
        String[] categories = getResources().getStringArray(R.array.categories);
        //float[] chartSumData = new float[] {}; // int[] a = new int[] {0, 0, 0, 0};

        float[] chartSumData = getChartSumData(getTransactions());
        Log.i("ViewData", "chartSumData: " + chartSumData[0]);

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i=0;i<chartSumData.length; ++i){
            dataEntries.add(new ValueDataEntry(categories[i], abs(chartSumData[i])));
        }

        pie.data(dataEntries); //put the data in the pie
        pie.title("expenses"); //set title of pie
        pie.innerRadius(90); //radius in % of the pie
        pie.labels().fontColor("595959"); //color of labels
        pie.labels().position("inside"); //labels inside the pie
        pie.insideLabelsOffset("-35%"); // set the offset for the labels

        pie.background().fill("Snow"); //set background to a colour

        pie.legend().title("category")
                .fontColor("595959")
                .title().fontColor("595959");
        pie.legend()
                .position("bottom")
                .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                .align(Align.CENTER);

        chartLinearGaugeCurrentEx.setChart(pie); //build and show the pie
    }

    public float[] getChartSumData(ArrayList<SingleTransaction> transactions){ // TODO test
        float sumFood = 0;
        float sumGoingOut = 0;
        float sumSport = 0;
        float sumTech = 0;
        float sumVaria = 0;

        for(SingleTransaction sl : transactions){
            String type = sl.getType();
            float amount = sl.getAmount();
            if (type.equals("food")) {
                sumFood = sumFood + amount;
                //Log.i("ViewData", "sumFood: " + sumFood);
            }
            if (type.equals("goingOut")) sumGoingOut = sumGoingOut + amount;
            if (type.equals("sport")) sumSport = sumSport + amount;
            if (type.equals("tech")) sumTech = sumTech + amount;
            if (type.equals("varia")) sumVaria = sumVaria + amount;
        }
        float[] chartSum = new float[] {sumFood, sumGoingOut, sumSport, sumTech, sumVaria};
        Log.i("ViewData","chartSum: " + chartSum[0] + ", " + chartSum[1] + ", " + chartSum[2] + ", " + chartSum[3] + ", " + chartSum[4]);
        return chartSum;
    }

    public ArrayList<SingleTransaction> getTransactions() {
        return transactions;
    } // TODO test

}
