package com.example.studentmoneyapp.activity;

import static java.lang.Math.abs;

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

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.LinearGauge;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.scales.Linear;
import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.model.SingleTransaction;
import com.example.studentmoneyapp.model.TransactionsRecViewAdapter;
import com.example.studentmoneyapp.utils.TransactionClass;
import com.example.studentmoneyapp.utils.TransactionRecView;

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

    ArrayList<TransactionRecView> transactionsRecViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);

        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); //declare array list and put in the transactions

        txtHistory = (TextView) findViewById(R.id.txtHistory);
        txtHistory.setMovementMethod(new ScrollingMovementMethod());

        anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        //setupPieChart();

        setTxtHistory();
        txtHistory.setVisibility(View.INVISIBLE);

        transactionsRecView = findViewById(R.id.transactionRecViewAll);
        transactionsRecViewList = new ArrayList<>();
        populateTransactionRecView();
        TransactionsRecViewAdapter transactionAdapter = new TransactionsRecViewAdapter(this);
        transactionAdapter.setTransactions(transactionsRecViewList);
        transactionsRecView.setAdapter(transactionAdapter);
        transactionsRecView.setLayoutManager(new LinearLayoutManager(this));

        setupChartLinearGaugeCurrentEx();
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

    public void setupChartLinearGaugeCurrentEx(){ //TODO just to test
        //AnyChartView chartGauge = findViewById(R.id.chartLinearGaugeCurrEx);
        LinearGauge linearGauge = AnyChart.linear();

        linearGauge.data(new SingleValueDataSet(new Integer[] { 2 }));

        Linear linear = Linear.instantiate();
        linear.minimum(-20)
                .maximum(100);
        linearGauge.axis(1).scale(linear);

        anyChartView.setChart(linearGauge);
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
        for(SingleTransaction singleTransaction : getTransactions()){
            transactionsRecViewList.add(new TransactionRecView(singleTransaction.getDate(),
                    singleTransaction.getAmount(),
                    singleTransaction.getStore()));
        }
    }

    public ArrayList<SingleTransaction> getTransactions() {
        return transactions;
    }

    public double getSumOfCurrentMonthTransactions(){ //sum of all expenses and income from the current month
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getMonthValue() == (getCurrentMonthValue()))
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfCurrentWeekTransactions(){ //sum of all expenses and income from the current week
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getMonthValue() == (getCurrentMonthValue()))
                .filter(singleTransaction -> singleTransaction.getDate().getDayOfMonth() >= getCurrentDayOfMonthMin())
                .filter(singleTransaction -> singleTransaction.getDate().getDayOfMonth() <= getCurrentDayOfMonthMax())
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfCurrentYearTransactions(){ //sum of all expenses and income from the current year
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getYear() == getCurrentYear())
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfSpecificMonthTransactions(int month){ // int 1-12  //sum of all expenses and income from a specific month
        if(month > 0 && month < 13){
            Log.e(getLocalClassName(),getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
            return -1;
        }
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getMonthValue() == month)
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfSpecificWeekTransactions(int week){ //sum of all expenses and income from a specific week
        if(week > 1 && week < 53){
            Log.e(getLocalClassName(),getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
            return -1;
        }
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getDayOfYear() >= getFirstDayOfSpecificWeek(week))
                .filter(singleTransaction -> singleTransaction.getDate().getDayOfYear() <= getSpecificDayOfYearMax(week))
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfLastYearTransactions(){ //sum of all expenses and income from the past year
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getYear() == getLastYear())
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfAlTimeTransactions(){ //sum of all expenses and income from all time
        return transactions
                .stream()
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }


    private int getCurrentYear() { //get the current year as int
        return LocalDateTime.now().getYear();
    }

    private int getLastYear() { //get the current year as int
        return LocalDateTime.now().getYear()-1;
    }

    public int getCurrentMonthValue(){ //get the current month as an int from 1-12
        return LocalDateTime.now().getMonthValue();
    }

    public int getCurrentDayOfMonthMin(){ //get first day of week in relation with the month as int from 1-31
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        int difference = dayOfMonth % 7;
        int min = (dayOfMonth - difference) - 1;
        return min;
    }

    public int getCurrentDayOfMonthMax(){ //get last day of week in relation with the month as int
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        int difference = dayOfMonth % 7;
        int max = dayOfMonth + (7 - difference);
        return max;
    }

    public int getFirstDayOfSpecificWeek(int week){ //int 1-52
        if(week > 1 && week < 53){
            Log.e(getLocalClassName(),getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
            return -1;
        }
        return (week * 7) - 6;
    }

    public int getSpecificDayOfYearMax(int week){ //get last day of specific week of year int 7, 14, 21...
        if(week > 0 && week < 53){
            Log.e(getLocalClassName(),getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
            return -1;
        }
        int dayOfYear = getFirstDayOfSpecificWeek(week);
        int difference = 6;
        int max = dayOfYear + difference;
        return max;
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
