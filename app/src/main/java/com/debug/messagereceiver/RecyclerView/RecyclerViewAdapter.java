package com.debug.messagereceiver.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.debug.messagereceiver.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    public static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> transaction_names = new ArrayList<>();
    private ArrayList<String> amounts = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> transaction_names, ArrayList<String> amounts,
                               ArrayList<String> dates, ArrayList<Integer> images, Context mContext) {
        this.transaction_names = transaction_names;
        this.amounts = amounts;
        this.dates = dates;
        this.images = images;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder called");
        Glide.with(mContext)
                .asBitmap()
                .load(images.get(position))
                .into(holder.image);

        holder.transaction_name.setText(transaction_names.get(position));
        holder.amount.setText(amounts.get(position));
        holder.date.setText(dates.get(position));

    }

    @Override
    public int getItemCount() {
        return transaction_names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView transaction_name;
        TextView amount;
        TextView date;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            transaction_name = itemView.findViewById(R.id.transaction_name);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
