package com.example.studentmoneyapp.activity;

import static java.lang.Math.abs;
import static java.lang.Math.round;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.CircularGauge;
import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.model.SingleTransaction;
import com.example.studentmoneyapp.model.TransactionsRecViewAdapter;
import com.example.studentmoneyapp.utils.ChartsDataHandler;
import com.example.studentmoneyapp.utils.TransactionClass;
import com.example.studentmoneyapp.utils.TransactionRecView;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ViewData extends AppCompatActivity {
    ArrayList<SingleTransaction> transactions;

    private RecyclerView transactionsRecView;
    private AnyChartView chartCircularGauge;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    ChartsDataHandler chartsDataHandler;

    ArrayList<TransactionRecView> transactionsRecViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);

        chartsDataHandler = new ChartsDataHandler(this); //declare class where all data is handled and puled out of
        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); //declare array list and put in the transactions

        progressBar = (ProgressBar) findViewById(R.id.pbCircularGaugeWeek);
        chartCircularGauge = (AnyChartView) findViewById(R.id.any_chart_view);

        transactionsRecViewList = new ArrayList<>();
        transactionsRecView = findViewById(R.id.transactionRecViewAll);
    }

    @Override
    protected void onResume() {
        super.onResume();

        chartsDataHandler.updateList();
        transactions = chartsDataHandler.getList();

        setupCircularGauge();

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

    public void setupCircularGauge(){
        APIlib.getInstance().setActiveAnyChartView(chartCircularGauge); //REALLY important for good functioning!!!
        chartCircularGauge.setProgressBar(findViewById(R.id.pbCircularGaugeWeek));

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
                .fill("#009900 0.4")
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
                .fill("#dd2c00 0.4")
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

        chartCircularGauge.setChart(circularGauge);
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
}
