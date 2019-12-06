package com.debug.messagereceiver.History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Room;
import android.os.Bundle;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;

import com.anychart.core.gauge.pointers.Bar;
import com.debug.messagereceiver.MainActivity;
import com.debug.messagereceiver.database.MyAppDatabase;
import com.debug.messagereceiver.R;
import com.debug.messagereceiver.Transaction;
import com.debug.messagereceiver.TransactionData;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PieChart extends AppCompatActivity implements Serializable {

    AnyChartView anyChartView;
    public static MyAppDatabase myAppDatabase;
    List<Transaction> transactions;
     Integer food=0;
     Integer bills=0;
     Integer emi=0;
     Integer entertainment=0;
     Integer fuel=0;
     Integer health=0;
     Integer travel=0;
     Integer others=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb")
                .allowMainThreadQueries().build();

        transactions = MainActivity.myAppDatabase.myDao().getAllTransactions();
        ArrayList<TransactionData> transactionDataList = new ArrayList<>();
        for (Transaction transaction: transactions){
            TransactionData transactionData = new TransactionData();
            transactionData.setAmount(transaction.getAmount());
            transactionData.setCategory(transaction.getCategory());
            transactionData.setDate(transaction.getTransactionDate());
            transactionDataList.add(transactionData);
        }

        anyChartView = findViewById(R.id.any_chart_view);
        setupPieChart(transactionDataList);
   }

    public void setupPieChart(ArrayList<TransactionData> transations) {
        Pie pie = AnyChart.pie();
        //Cartesian pie = AnyChart.column();

        ArrayList<DataEntry> Entries = new ArrayList<>();

        categoryTotal();
        Entries.add(new ValueDataEntry("food",food));
        Entries.add(new ValueDataEntry("bills",bills));
        Entries.add(new ValueDataEntry("shopping",emi));
        Entries.add(new ValueDataEntry("entertainment",entertainment));
        Entries.add(new ValueDataEntry("fuel",fuel));
        Entries.add(new ValueDataEntry("health",health));
        Entries.add(new ValueDataEntry("travel",travel));
        Entries.add(new ValueDataEntry("others",others));

        pie.data(Entries);
        pie.fill("aquastyle");
        anyChartView.setChart(pie);

    }


    public void categoryTotal(){
        for (Transaction transaction: transactions){
            if(transaction.getCategory().equals("Food")){
                food += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Bill")){
                bills += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Shopping")){
                emi += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Entertainment")){
                entertainment += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("fuel")){
                fuel += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Health")){
                health += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Travel")){
                travel += Integer.parseInt(transaction.getAmount());
            }
            else {
                others += Integer.parseInt(transaction.getAmount());
            }
        }
    }

    public void setupColumnGraph(List<Transaction> transactions){

        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String currentdate = sdf.format(currentDate);

        List<Transaction> currentMonth = new ArrayList<>();
        List<Transaction> lastMonth = new ArrayList<>();
        List<Transaction> lastSecondMonth = new ArrayList<>();

        for(Transaction transaction :transactions){

            if(currentdate.substring(0,2).equals(transaction.getTransactionDate().substring(0,2)))
                currentMonth.add(transaction);

            else if(Integer.parseInt(currentdate.substring(0,2)) ==
                    ((Integer.parseInt(transaction.getTransactionDate().substring(0,2)))-1))
                lastMonth.add(transaction);

            else if(Integer.parseInt(currentdate.substring(0,2)) ==
                    ((Integer.parseInt(transaction.getTransactionDate().substring(0,2)))-2))
                lastSecondMonth.add(transaction);

        }
        Cartesian pie = AnyChart.column();
        ArrayList<DataEntry> Entries = new ArrayList<>();
        categorySeperator(lastMonth);
        Entries.add(new ValueDataEntry("food",food));
        Entries.add(new ValueDataEntry("bills",bills));
        Entries.add(new ValueDataEntry("shopping",emi));
        Entries.add(new ValueDataEntry("entertainment",entertainment));
        Entries.add(new ValueDataEntry("fuel",fuel));
        Entries.add(new ValueDataEntry("health",health));
        Entries.add(new ValueDataEntry("travel",travel));
        Entries.add(new ValueDataEntry("others",others));

        pie.data(Entries);
        anyChartView.setChart(pie);
    }

    public void categorySeperator(List<Transaction> transactions){
        for (Transaction transaction: transactions){
            if(transaction.getCategory().equals("Food")){
                food += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Bill")){
                bills += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Shopping")){
                emi += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Entertainment")){
                entertainment += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("fuel")){
                fuel += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Health")){
                health += Integer.parseInt(transaction.getAmount());
            }
            else if(transaction.getCategory().equals("Travel")){
                travel += Integer.parseInt(transaction.getAmount());
            }
            else {
                others += Integer.parseInt(transaction.getAmount());
            }
        }
    }


}
