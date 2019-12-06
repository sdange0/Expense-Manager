package com.debug.messagereceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.debug.messagereceiver.RecyclerView.NotificationViewAdapter;
import com.debug.messagereceiver.RecyclerView.RecyclerViewAdapter;
import com.debug.messagereceiver.database.MyAppDatabase;

import java.util.ArrayList;
import java.util.List;

public class AssignCategories extends AppCompatActivity {
    private static final String TAG = "History";
    public static MyAppDatabase myAppDatabase;
    private ArrayList<String> amounts=new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> benificiaries = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_categories);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class,"userdb")
                .allowMainThreadQueries().build();
        List<Transaction> transactions = MainActivity.myAppDatabase.myDao().getSelectedTransactions("Others");
        ArrayList<TransactionData> transactionDataList = new ArrayList<>();
        for (Transaction transaction: transactions){
            amounts.add(0,"Rs. "+transaction.getAmount());
            benificiaries.add(0,transaction.getBeneficiary() + "\n(Via "+transaction.getTransaction_source()+ ")");
            dates.add(0,transaction.getTransactionDate());
            ids.add(0,transaction.getTransactionId());
        }


        initRecyclerView();

    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        NotificationViewAdapter adapter = new NotificationViewAdapter(benificiaries,amounts,dates,ids,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
