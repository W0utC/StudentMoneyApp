package com.example.studentmoneyapp.activity;

import static java.lang.Math.abs;
import static java.lang.Math.round;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.CircularGauge;
import com.anychart.charts.LinearGauge;
import com.anychart.charts.Pie;
import com.anychart.core.lineargauge.pointers.Marker;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Orientation;
import com.anychart.graphics.vector.GradientKey;
import com.anychart.scales.Linear;
import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.model.SingleTransaction;
import com.example.studentmoneyapp.model.TransactionsRecViewAdapter;
import com.example.studentmoneyapp.utils.ChartsDataHandler;
import com.example.studentmoneyapp.utils.TransactionClass;
import com.example.studentmoneyapp.utils.TransactionRecView;
import com.google.common.collect.Lists;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewData extends AppCompatActivity {
    TextView txtHistory;
    ArrayList<SingleTransaction> transactions;
    AnyChartView anyChartView;

    private RecyclerView transactionsRecView;
    private RelativeLayout relativeLayout;
    ChartsDataHandler chartsDataHandler;

    ArrayList<TransactionRecView> transactionsRecViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);

        chartsDataHandler = new ChartsDataHandler(this);

        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); //declare array list and put in the transactions

        txtHistory = (TextView) findViewById(R.id.txtHistory);
        txtHistory.setMovementMethod(new ScrollingMovementMethod());

        anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        //setupPieChart();
        setupCircularGauge();

        setTxtHistory();
        txtHistory.setVisibility(View.INVISIBLE);

        transactionsRecView = findViewById(R.id.transactionRecViewAll);
        transactionsRecViewList = new ArrayList<>();
        populateTransactionRecView();
        TransactionsRecViewAdapter transactionAdapter = new TransactionsRecViewAdapter(this);
        transactionAdapter.setTransactions(transactionsRecViewList);
        transactionsRecView.setAdapter(transactionAdapter);
        transactionsRecView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onBtnChartPage_Clicked(View caller) {
        Intent intent = new Intent(this, ChartsData.class);
        startActivity(intent);
    }

    public void setupPieChart(){ //hadden achteraf mss beter google chart gebruikt of fusion charts
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

        anyChartView.setChart(pie); //build and show the pie
    }

    public void setupCircularGauge(){
        AnyChartView chatCircularGauge = findViewById(R.id.any_chart_view);
        APIlib.getInstance().setActiveAnyChartView(chatCircularGauge); //REALLY important for good functioning!!!

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.animation(true);

        circularGauge
                .fill("White")
                .stroke(null)
                .padding("0dp")
                .margin("10dp")
                .startAngle(270)
                .sweepAngle(180);


        circularGauge.axis(0)
                .enabled(true)
                .labels()
                .fontSize(14)
                .position("outside")
                .format("{%Value}");

        circularGauge
                .axis(0)
                .enabled(true)
                .scale()
                .minimum(0)
                .maximum(150)
                .ticks("{ interval: 10 }")
                .minorTicks("{ interval: 5 }");

        circularGauge.axis(0)
                .fill("#545f69")
                .width(1)
                .ticks("{ type: 'line', fill: 'white', length: 2 }");

        circularGauge.title("this weeks spending"); //can't put ' in the text anychart won't work then

        circularGauge
                .title()
                .useHtml(true)
                .padding(0)
                .fontColor("#212121")
                .hAlign("center")
                .margin(0, 0, 10, 0);

        circularGauge
                .needle(0)
                .stroke("2 #545f69")
                .enabled(true)
                .startRadius("5%")
                .endRadius("90%")
                .startWidth("0.1%")
                .endWidth("0.1%")
                .middleWidth("0.1%");

        circularGauge.cap().radius("3%").enabled(true).fill("#545f69");

        circularGauge.range(0)
                .from(0)
                .to(50)
                .position("inside")
                .fill("#dd2c00 0.4")
                .startSize(50)
                .endSize(50)
                .radius(98);

        circularGauge.range(1)
                .from(50)
                .to(100)
                .position("inside")
                .fill("#ffa000 0.4")
                .startSize(50)
                .endSize(50)
                .radius(98);

        circularGauge.range(2)
                .from(100)
                .to(150)
                .position("inside")
                .fill("#009900 0.4")
                .startSize(50)
                .endSize(50)
                .radius(98);

        circularGauge
                .label(1)
                .text("good")
                .fontColor("#212121")
                .fontSize(14)
                .offsetY("68%")
                .offsetX(25)
                .anchor("center");

        circularGauge
                .label(2)
                .text("be careful")
                .fontColor("#212121")
                .fontSize(14)
                .offsetY("68%")
                .offsetX(90)
                .anchor("center");

        circularGauge
                .label(3)
                .text("stop spending!")
                .fontColor("#212121")
                .fontSize(14)
                .offsetY("68%")
                .offsetX(155)
                .anchor("center");

        List<DataEntry> dataEntry = new ArrayList<>();
        dataEntry.add(new ValueDataEntry("week", abs(round(chartsDataHandler.getSumOfCurrentWeekTransactions()))));
        circularGauge.data(dataEntry);

        chatCircularGauge.setChart(circularGauge);
    }

    public float[] getChartSumData(ArrayList<SingleTransaction> transactions){ // TODO better to work with ExpenseSumPair
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

    public void setTxtHistory() {
        for (int i = 0; i < getTransactions().size(); i++) { //original: for(int i = 0; i<allTransactions.getSingleTransactionList().size(); i++){
            String str = getTxtHistory(i);
            txtHistory.append(str + '\n');
        }

    }

    public String getTxtHistory(int pos) {
        String tempStr = "";

        String date = transactions.get(pos).getDate().toString(); //original: String date = allTransactions.getSingleTransactionList().get(pos).getDate().toString();
        date = date.substring(0, 10);
        String amount = String.valueOf(transactions.get(pos).getAmount()); //original: String amount = String.valueOf(allTransactions.getSingleTransactionList().get(pos).getAmount());
        String store = transactions.get(pos).getStore(); //original: String store = allTransactions.getSingleTransactionList().get(pos).getStore();
        String euro = "\u20ac";

        tempStr = date + ": " + euro + amount + " " + store;
        return tempStr;
    }

    public void populateTransactionRecView(){

        for(SingleTransaction singleTransaction : Lists.reverse(getTransactions())){
            transactionsRecViewList.add(new TransactionRecView(singleTransaction.getDate(),
                    singleTransaction.getAmount(),
                    singleTransaction.getStore()));
        }
    }

    public ArrayList<SingleTransaction> getTransactions() {
        return transactions;
    }



    //public void test() {
        /*Log.i("ViewData", "------------------------");

        //Log.i("ViewData", "first element in TransactionClass: " + TransactionClass.getInstance().getList().get(0).toString());
        Log.i("ViewData", "first element in TransactionClass: " + transactions.toString());
        //Log.i("ViewData", "first element in AllTransactions: " + allTransactions.getSingleTransactionList().get(0));

        for (SingleTransaction s : transactions) {
            String date = s.getDate().toString();
            date = date.substring(0, 10);
            String amount = String.valueOf(s.getAmount());
            String store = s.getStore();
            String euro = "\u20ac";

            String tempStr = date + ": " + euro + amount + " " + store;
            Log.i("ViewData", tempStr);
        }

        Log.i("ViewData", "------------------------");
    }*/
}
