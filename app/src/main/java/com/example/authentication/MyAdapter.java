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

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dwnld_area=holder.tvArea.getText().toString().trim();
                String dwnld_time=holder.tvTime.getText().toString().trim();
                String dwnld_tranid=holder.tvTranID.getText().toString().trim();
                String dwnld_pnp=holder.tvPnp.getText().toString().trim();
                String dwnld_name=HomePage.fullname;
                String dwnld_email=HomePage.email;

                    generatePDF(dwnld_name,dwnld_email,dwnld_area
                            ,dwnld_time,dwnld_tranid,dwnld_pnp);

            }
        });
    }



    @Override
    public int getItemCount() {
        return historyModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvArea, tvTime, tvTranID, tvPnp;
        AppCompatImageButton download;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            download=itemView.findViewById(R.id.btn_download);
            tvArea = itemView.findViewById(R.id.list_area);
            tvTranID = itemView.findViewById(R.id.list_tranID);
            tvTime = itemView.findViewById(R.id.list_time);
            tvPnp = itemView.findViewById(R.id.list_pnp);
        }
    }


    private boolean checkPermission() {
        int permission1= ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int permission2= ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);


        return permission1== PackageManager.PERMISSION_GRANTED&&
                permission2==PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context,new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("ResourceType")
    public void  generatePDF(String Fullname, String Email, String Area, String Time, String Tnsid,String PNP) {
        document=new PdfDocument();
        PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(pdfWidth,pdfHeight,1).create();
        PdfDocument.Page page=document.startPage(pageInfo);
        //Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.logo1);
        //Bitmap bitmap1= BitmapFactory.decodeResource(context.getResources(),R.drawable.verfy);

        Canvas canvas=page.getCanvas();
        Paint paintText=new Paint();
        paintText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        paintText.setTextSize(8);
        paintText.setColor(ContextCompat.getColor(context,R.color.black));
        paintText.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("PDF Generated on "+String.valueOf(Calendar.getInstance().getTime()),10,10,paintText);

        paintText.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD,Typeface.NORMAL));
        paintText.setTextSize(25);
        paintText.setColor(ContextCompat.getColor(context,R.color.black));
        paintText.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Invoice ",380,50,paintText);


        paintText.setTextAlign(Paint.Align.LEFT);

        //canvas.drawBitmap(bitmap,50,90,null);
        paintText.setTextSize(27);
        canvas.drawText("Pay & Park®",250,190,paintText);

        paintText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        paintText.setTextSize(17);
        canvas.drawText("Name :",50,300,paintText);
        canvas.drawText("Email :",50,350,paintText);
        canvas.drawText("Time :",50,400,paintText);
        canvas.drawText("Area :",50,450,paintText);
        canvas.drawText("Transaction ID :",50,500,paintText);
        canvas.drawText("PNP :",50,550,paintText);
        paintText.setColor(ContextCompat.getColor(context,R.color.grey_30));
        canvas.drawText(Fullname,200,300,paintText);
        canvas.drawText(Email,200,350,paintText);
        canvas.drawText(Time,200,400,paintText);
        canvas.drawText(Area,200,450,paintText);
        canvas.drawText(Tnsid,200,500,paintText);
        canvas.drawText(PNP,200,550,paintText);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD,Typeface.NORMAL));
        paintText.setColor(ContextCompat.getColor(context,R.color.black));
        paintText.setTextSize(20);
        canvas.drawText("------------------------------------------------------------------------------------------------------------------------"
                ,10,700,paintText);
        canvas.drawText("Amount :",400,710,paintText);
        canvas.drawText("100.00",600,710,paintText);
        //canvas.drawBitmap(bitmap1,200,810,null);


        document.finishPage(page);
        createFile();
    }
    private  void createFile() {
        Intent intent=new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE,"invoice_"+ Calendar.getInstance().getTimeInMillis()+".pdf");
        intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME,"com.example.authentication");
//        context.startActivity(intent);
        ((Activity)context).startActivityForResult(intent,CREATE_FILE);
    }


    @Deprecated
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {
        onActivityResult(requestCode, resultCode, resultData);
        if(requestCode==CREATE_FILE
                && resultCode== Activity.RESULT_OK){
            Uri uri=null;
            if(resultData!=null){
                uri =resultData.getData();

                if (document!=null){
                    ParcelFileDescriptor pfd = null;
                    try {
                        pfd =context.getContentResolver().openFileDescriptor(uri,"w");
                        FileOutputStream fileOutputStream=
                                new FileOutputStream(pfd.getFileDescriptor());
                        document.writeTo(fileOutputStream);
                        document.close();
                        MainActivity.booked[Area][bookslot2.Slot]+=1;

                        Toast.makeText(context, String.valueOf(bookslot2.cheBook()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Pdf Saved Successfully", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context,HomePage.class));
                    }catch (IOException e){
                        try {
                            DocumentsContract.deleteDocument(context.getContentResolver(),uri);
                        }catch (FileNotFoundException ex){
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            }
        }else {
            context.startActivity(new Intent(context,HomePage.class));

        }
    }
}
