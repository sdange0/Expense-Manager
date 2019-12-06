package com.debug.messagereceiver.History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.debug.messagereceiver.MainActivity;
import com.debug.messagereceiver.RecyclerView.NotificationViewAdapter;
import com.debug.messagereceiver.database.MyAppDatabase;
import com.debug.messagereceiver.R;
import com.debug.messagereceiver.RecyclerView.RecyclerViewAdapter;
import com.debug.messagereceiver.Transaction;
import com.debug.messagereceiver.TransactionData;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private static final String TAG = "History";
    public static MyAppDatabase myAppDatabase;
    private ArrayList<String> amounts=new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> benificiaries = new ArrayList<>();
    private  ArrayList<Integer> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb")
                .allowMainThreadQueries().build();

        List<Transaction> transactions = MainActivity.myAppDatabase.myDao().getAllTransactions();
        //List<Transaction> transactions = MainActivity.myAppDatabase.myDao().getSelectedTransactions("Others");

        ArrayList<TransactionData> transactionDataList = new ArrayList<>();
        for (Transaction transaction: transactions){
            amounts.add(0,"Rs. "+transaction.getAmount());
            benificiaries.add(0,transaction.getBeneficiary() + "\n(Via "+transaction.getTransaction_source()+ ")");
            dates.add(0,transaction.getTransactionDate());
            images.add(0,getIcon(transaction.getCategory()));
        }
        initRecyclerView();
    }

    private Integer getIcon(String category){
        switch(category){
            case "Food":
                return R.drawable.food;
            case "Bill":
                return R.drawable.bill;
            case "Shopping":
                return R.drawable.shopping;
            case "fuel":
                return R.drawable.fuel;
            case "Others":
                return R.drawable.other;
        }
        return R.drawable.ic_launcher_background;
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(benificiaries,amounts,dates,images,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*NotificationViewAdapter adapter = new NotificationViewAdapter(benificiaries,amounts,dates,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
    }
}
