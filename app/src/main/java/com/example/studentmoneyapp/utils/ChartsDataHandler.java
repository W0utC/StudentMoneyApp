package com.example.studentmoneyapp.utils;

import static java.lang.Math.abs;

import android.content.Context;
import android.util.Log;

import com.example.studentmoneyapp.R;
import com.example.studentmoneyapp.model.SingleTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChartsDataHandler {

    ArrayList<SingleTransaction> transactions;
    Context context;

    private static final String TAG = ChartsDataHandler.class.getSimpleName();

    public ChartsDataHandler(Context context) {
        transactions = new ArrayList<>(TransactionClass.getInstance().getList()); //declare array list and put in the transactions
        this.context = context;
    }

    public double getSumOfCurrentMonthTransactionsEx(){ //sum of all expenses and income from the current month
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getMonthValue() == (getCurrentMonthValue()))
                .filter(singleTransaction -> singleTransaction.getAmount() < 0)
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfCurrentWeekTransactions(){ //sum of all expenses and income from the current week
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getMonthValue() == (getCurrentMonthValue()))
                .filter(singleTransaction -> singleTransaction.getDate().getDayOfMonth() >= getCurrentDayOfMonthMin())
                .filter(singleTransaction -> singleTransaction.getDate().getDayOfMonth() <= getCurrentDayOfMonthMax())
                .filter(singleTransaction -> singleTransaction.getAmount() < 0)
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfCurrentYearTransactionsEx(){ //sum of all expenses and income from the current year
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getYear() == getCurrentYear())
                .filter(singleTransaction -> singleTransaction.getAmount() < 0)
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfSpecificMonthTransactionsEx(int month){ // int 1-12  //sum of all expenses and income from a specific month
        if(month > 0 && month < 13){
            Log.e(TAG,getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
            return -1;
        }
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getMonthValue() == month)
                .filter(singleTransaction -> singleTransaction.getAmount() < 0)
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfSpecificWeekTransactionsEx(int week){ //sum of all expenses and income from a specific week
        if(week > 1 && week < 53){
            Log.e(TAG,getClass().getCanonicalName() + ": Input out of bounds! max range: 1-52");
            return -1;
        }
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getDayOfYear() >= getFirstDayOfSpecificWeek(week))
                .filter(singleTransaction -> singleTransaction.getDate().getDayOfYear() <= getSpecificDayOfYearMax(week))
                .filter(singleTransaction -> singleTransaction.getAmount() < 0)
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfLastYearTransactionsEx(){ //sum of all expenses and income from the past year
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getDate().getYear() == getLastYear())
                .filter(singleTransaction -> singleTransaction.getAmount() < 0)
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    public double getSumOfAlTimeTransactionsEx(){ //sum of all expenses and income from all time
        return transactions
                .stream()
                .filter(singleTransaction -> singleTransaction.getAmount() < 0)
                .mapToDouble(SingleTransaction::getAmount)
                .sum();
    }

    /** returns a list of sums of the category's of a month in a year
     * [sumFood, sumHoreca, sumGoingOut, sumSport, sumTech, sumVaria]**/
    public ArrayList<Double> getSumListOfMothCat(int year, int month){
        String[] categories = context.getResources().getStringArray(R.array.categories);
        ArrayList<Double> sumList= new ArrayList<>();

        for(int i = 0; i<categories.length; i++){
            String cat = categories[i];
            Log.i(TAG, "category: " + cat);
            sumList.add(getSumOfMonthCatExpense(cat, year, month));
            Log.i(TAG, "sumList item added: " + sumList.get(i));
        }

        return sumList;
    }

    public double getSumOfMonthCatExpense(String cat, int year, int month){
        if(month < 0 || month > 12){
            Log.e(TAG,getClass().getCanonicalName() + ": Input out of bounds! max range: 1-12");
            return -1;
        }

        double temp = abs(
                transactions
                        .stream()
                        .filter(singleTransaction -> singleTransaction.getDate().getYear() == year)
                        .filter(singleTransaction -> singleTransaction.getDate().getMonthValue() == month)
                        .filter(singleTransaction -> singleTransaction.getType().equals(cat))
                        .filter(singleTransaction -> singleTransaction.getAmount() < 0)
                        .mapToDouble(SingleTransaction::getAmount)
                        .sum()
        );

        double tempRounded = Math.round(temp*100.0)/100.0;

        return tempRounded;
    }


    public int getCurrentYear() { //get the current year as int
        return LocalDateTime.now().getYear();
    }

    public int getLastYear() { //get the current year as int
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

    public void updateList(){
        //transactions.clear();
        transactions = TransactionClass.getInstance().getList();
    }

    public ArrayList<SingleTransaction> getList(){
        if (transactions!= null){
        return transactions;
        }
        return null;
    }
}
