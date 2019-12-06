package com.debug.messagereceiver.RecyclerView;

import android.content.Context;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuggetionAdapter extends RecyclerView.Adapter<SuggetionAdapter.ViewHolder> {

    private List<String> suggetions = new ArrayList<>();
    private List<Integer> images = new ArrayList<>();
    private Context mContext;

    public SuggetionAdapter(List<String> suggetions,List<Integer> images, Context mContext) {
        this.suggetions = suggetions;
        this.images=images;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_suggetions,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(images.get(position))
                .into(holder.image);
        holder.msuggetions.setText(suggetions.get(position));
    }

    @Override
    public int getItemCount() {
        return suggetions.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView msuggetions;
        CircleImageView image;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msuggetions = itemView.findViewById(R.id.suggetion);
            image = itemView.findViewById(R.id.image);
            parentLayout = itemView.findViewById(R.id.suggetion_layout);
        }
    }
}
