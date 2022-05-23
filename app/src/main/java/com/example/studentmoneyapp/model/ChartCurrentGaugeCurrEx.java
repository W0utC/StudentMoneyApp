package com.example.studentmoneyapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.LinearGauge;
import com.anychart.enums.Anchor;
import com.anychart.enums.Orientation;
import com.anychart.enums.Position;
import com.anychart.scales.Base;
import com.anychart.scales.Linear;
import com.example.studentmoneyapp.R;

public class ChartCurrentGaugeCurrEx{
    Context context;
    float weekExpense;
    float monthExpense;
    LayoutInflater inflater;

    public ChartCurrentGaugeCurrEx(Context applicationContext, float weekExpense, float monthExpense) {
        this.context = applicationContext;
        this.weekExpense = weekExpense;
        this.monthExpense = monthExpense;
;
    }

    public void setChart(){

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
                linearGauge.axis(1).scale(linear);

                ViewHolder.gaugeChart.setChart(linearGauge);
            }

    public void setChips(){

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public static AnyChartView gaugeChart;
        //public AnyChartView gaugeChart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gaugeChart = itemView.findViewById(R.id.chartLinearGaugeCurrExWeek);
        }
    }
}