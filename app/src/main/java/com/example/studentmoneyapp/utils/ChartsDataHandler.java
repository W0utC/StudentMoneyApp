package com.example.studentmoneyapp.utils;

import android.util.Log;

import com.example.studentmoneyapp.model.SingleTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChartsDataHandler {

    ArrayList<SingleTransaction> transactions;

    private static final String TAG = ChartsDataHandler.class.getSimpleName();

    public ChartsDataHandler() {
        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); //declare array list and put in the transactions
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
            Log.e(TAG,getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
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
            Log.e(TAG,getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
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
            Log.e(TAG,getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
            return -1;
        }
        return (week * 7) - 6;
    }

    public int getSpecificDayOfYearMax(int week){ //get last day of specific week of year int 7, 14, 21...
        if(week > 0 && week < 53){
            Log.e(TAG,getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
            return -1;
        }
        int dayOfYear = getFirstDayOfSpecificWeek(week);
        int difference = 6;
        int max = dayOfYear + difference;
        return max;
    }
}
