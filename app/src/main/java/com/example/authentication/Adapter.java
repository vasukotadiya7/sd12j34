package com.example.authentication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public Adapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
            User user=userArrayList.get(position);

            holder.tvFname.setText(user.fullName);
            holder.tvEmail.setText(user.email);
            holder.tvAge.setText(user.age);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvFname,tvEmail,tvAge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFname=itemView.findViewById(R.id.list_name);
            tvEmail=itemView.findViewById(R.id.list_email);
            tvAge=itemView.findViewById(R.id.list_age);
        }
    }
}
