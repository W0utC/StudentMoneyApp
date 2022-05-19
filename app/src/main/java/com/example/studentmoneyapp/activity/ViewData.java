package com.example.studentmoneyapp.activity;

import static java.lang.Math.abs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.model.SingleTransaction;
import com.example.studentmoneyapp.utils.TransactionClass;

import java.util.ArrayList;
import java.util.List;

public class ViewData extends AppCompatActivity {
    TextView txtHistory;
    ArrayList<SingleTransaction> transactions;
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);


        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); //declare array list and put in the transactions

        txtHistory = (TextView) findViewById(R.id.txtHistory);
        txtHistory.setMovementMethod(new ScrollingMovementMethod());

        anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        setupPieChart();

        setTxtHistory();
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
        pie.insideLabelsOffset("-30%"); // set the offset for the labels

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

    public ArrayList<SingleTransaction> getTransactions() {
        return transactions;
    }

    public void test() {
        Log.i("ViewData", "------------------------");

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
    }

}
