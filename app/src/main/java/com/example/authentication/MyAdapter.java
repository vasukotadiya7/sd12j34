package com.example.authentication;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.authentication.BookSlot1.Area;
import static com.example.authentication.SlotBooking.CREATE_FILE;
import static com.example.authentication.SlotBooking.pdfHeight;
import static com.example.authentication.SlotBooking.pdfWidth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public static int hs=0;
    public static String dwnld_area;
    public static String dwnld_time,dwnld_level;
    public static String dwnld_tranid;
    public static String dwnld_pnp;
    public static String dwnld_name;
    public static String dwnld_email;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Context context;
    PdfDocument document;

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
        holder.tvLevel.setText(historyModel.getLevel());

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dwnld_area=holder.tvArea.getText().toString().trim();
                dwnld_time=holder.tvTime.getText().toString().trim();
                dwnld_tranid=holder.tvTranID.getText().toString().trim();
                dwnld_pnp=holder.tvPnp.getText().toString().trim();
                dwnld_level=holder.tvLevel.getText().toString().trim();
                dwnld_name=HomePage.fullname;
                dwnld_email=HomePage.email;
                hs=1;
                context.startActivity(new Intent(context,SlotBooking.class));
//                    generatePDF(dwnld_name,dwnld_email,dwnld_area
//                            ,dwnld_time,dwnld_tranid,dwnld_pnp);

            }
        });
    }



    @Override
    public int getItemCount() {
        return historyModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvArea, tvTime, tvTranID, tvPnp,tvLevel;
        AppCompatImageButton download;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            download=itemView.findViewById(R.id.btn_download);
            tvArea = itemView.findViewById(R.id.list_area);
            tvTranID = itemView.findViewById(R.id.list_tranID);
            tvTime = itemView.findViewById(R.id.list_time);
            tvPnp = itemView.findViewById(R.id.list_pnp);
            tvLevel=itemView.findViewById(R.id.list_level);
        }
    }

}