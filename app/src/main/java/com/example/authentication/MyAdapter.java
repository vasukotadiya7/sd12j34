package com.example.authentication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<HistoryModel> historyModelArrayList;

    public MyAdapter(Context context, ArrayList<HistoryModel> historyModelArrayList) {
        this.context = context;
        this.historyModelArrayList = historyModelArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HistoryModel historyModel = historyModelArrayList.get(position);

        holder.tvArea.setText(historyModel.area);
        holder.tvTime.setText(historyModel.time);
        holder.tvTranID.setText(historyModel.transactionID);
        holder.tvPnp.setText(historyModel.PNP);
    }



    @Override
    public int getItemCount() {
        return historyModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvArea, tvTime, tvTranID, tvPnp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.list_area);
            tvTranID = itemView.findViewById(R.id.list_tranID);
            tvTime = itemView.findViewById(R.id.list_time);
            tvPnp = itemView.findViewById(R.id.list_pnp);
        }
    }
}
