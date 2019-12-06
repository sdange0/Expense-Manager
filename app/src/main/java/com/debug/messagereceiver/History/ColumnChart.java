package com.debug.messagereceiver.History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.debug.messagereceiver.MainActivity;
import com.debug.messagereceiver.R;
import com.debug.messagereceiver.Transaction;
import com.debug.messagereceiver.TransactionData;
import com.debug.messagereceiver.database.MyAppDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ColumnChart extends AppCompatActivity {

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
        setContentView(R.layout.activity_column_chart);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb")
                .allowMainThreadQueries().build();

        transactions = MainActivity.myAppDatabase.myDao().getAllTransactions();

        anyChartView = findViewById(R.id.columnChart);
        setupColumnGraph(transactions);
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
                    ((Integer.parseInt(transaction.getTransactionDate().substring(0,2)))+1))
                lastMonth.add(transaction);

            else if(Integer.parseInt(currentdate.substring(0,2)) ==
                    ((Integer.parseInt(transaction.getTransactionDate().substring(0,2)))+2))
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
