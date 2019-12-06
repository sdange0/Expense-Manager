package com.debug.messagereceiver.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.debug.messagereceiver.AssignCategories;
import com.debug.messagereceiver.MainActivity;
import com.debug.messagereceiver.R;
import com.debug.messagereceiver.Transaction;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationViewAdapter extends RecyclerView.Adapter<NotificationViewAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> transaction_names = new ArrayList<>();
    private ArrayList<String> amounts = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private Context mContext;

    public NotificationViewAdapter(ArrayList<String> transaction_names, ArrayList<String> amounts,
                                   ArrayList<String> dates,ArrayList<String>ids, Context mContext) {
        this.transaction_names = transaction_names;
        this.amounts = amounts;
        this.dates = dates;
        this.mContext = mContext;
        this.ids = ids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notification,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.d(TAG,"onBindViewHolder called");

        holder.transaction_name.setText(transaction_names.get(position));
        holder.amount.setText(amounts.get(position));
        holder.date.setText(dates.get(position));
        holder.foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignCategories.myAppDatabase.myDao().updateCategory("Food",ids.get(position));
                List<Transaction> transactions = MainActivity.myAppDatabase.myDao().getSelectedTransactions("Others");
                }
        });
    }

    @Override
    public int getItemCount() {
        return transaction_names.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView transaction_name;
        TextView amount;
        TextView date;
        RelativeLayout parentLayout;
        Button foodButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            transaction_name = itemView.findViewById(R.id.transaction_name);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            parentLayout = itemView.findViewById(R.id.notification);
            foodButton = itemView.findViewById(R.id.food);
        }
    }
}
