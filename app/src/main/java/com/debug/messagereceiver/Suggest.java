package com.debug.messagereceiver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.debug.messagereceiver.RecyclerView.SuggetionAdapter;
import com.debug.messagereceiver.predictor.Prediction;

import java.util.ArrayList;
import java.util.List;

public class Suggest extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        List<Transaction> transactions = MainActivity.myAppDatabase.myDao().getAllTransactions();
        List<String>suggest = new ArrayList<>();
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.food);
        images.add(R.drawable.bill);
        images.add(R.drawable.emi);
        images.add(R.drawable.enter);
        images.add(R.drawable.fuel);
        images.add(R.drawable.health);
        images.add(R.drawable.travel);
        images.add(R.drawable.other);
        images.add(R.drawable.ic_launcher_background);

        suggest =  Prediction.getNotification(transactions);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        SuggetionAdapter adapter = new SuggetionAdapter(suggest,images,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
