package com.example.authentication;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_MUTABLE;
import static com.example.authentication.BookSlot1.Area;
import static com.example.authentication.MyAdapter.dwnld_area;
import static com.example.authentication.MyAdapter.dwnld_email;
import static com.example.authentication.MyAdapter.dwnld_level;
import static com.example.authentication.MyAdapter.dwnld_name;
import static com.example.authentication.MyAdapter.dwnld_pnp;
import static com.example.authentication.MyAdapter.dwnld_time;
import static com.example.authentication.MyAdapter.dwnld_tranid;
import static com.example.authentication.MyAdapter.hs;
import static com.example.authentication.payment.UPI_PAYMENT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class SlotBooking extends AppCompatActivity implements PaymentStatusListener,PaymentResultListener {
    private Button btConfrom;
    public TextView tvFN,tvEM,tvArea,tvTime,tvLevel;
    public String area,time,fname,email,Level;
//    private static final int PERMISSION_REQUEST_CODE = 10;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final String TAG= String.valueOf(Calendar.DATE)+" "+String.valueOf(Calendar.MINUTE);
    static int pdfHeight=1080;
    static int pdfWidth=720;
    private static PdfDocument document;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    DocumentReference documentReference;
    public String userID;
    Bitmap bmp,sclaledbmp;
    public String trnid;
    public String nobs;
    private String pnp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            Uri custom=Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.notify_sound);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            if (hs==1){
                generatePDF(dwnld_name,dwnld_email,dwnld_area
                            ,dwnld_time,dwnld_tranid,dwnld_pnp,dwnld_level);
            }
            CharSequence name="ReminderChanel";
            String description="Send Reminder for slot";
            NotificationChannel channel=new NotificationChannel("My Notification","nofity",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            channel.setSound(custom,audioAttributes);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        setContentView(R.layout.activity_slot_booking);
        btConfrom=findViewById(R.id.conformbt);
        tvArea=findViewById(R.id.tv_area);
        tvTime=findViewById(R.id.tv_time);
        tvFN=findViewById(R.id.tv_fn);
        tvEM=findViewById(R.id.tv_em);
        tvLevel=findViewById(R.id.tv_level);
        fstore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();
        documentReference=fstore.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tvFN.setText(value.getString("fullname"));
                tvEM.setText(value.getString("email"));
                fname=value.getString("fullname");
                email=value.getString("email");
            }
        });

        switch (Area){
            case 1:
                area="Sector 16";
                break;
            case 2:
                area="Sector 24";
                break;
            case 3:
                area="Sector 8";
                break;
            case 4:
                area="Sector 14";
                break;
            case 5:
                area="Sector 30";
                break;
            case 6:
                area="Sector 20";
                break;
            case 7:
                area="Sector 2";
                break;
            case 8:
                area="Sector 21";
                break;
            case 9:
                area="Sector 10";
                break;
            case 10:
                area="Sector 7";
                break;

        }switch (levels.level) {
            case 1:
                Level = "Level 1";
                break;
            case 2:
                Level = "Level 2";
                break;
            case 3:
                Level = "Level 3";
                break;
            case 4:
                Level = "Level 4";
                break;
        }

        switch (scroll.time){
            case 1:
                time="1 AM TO 2 AM";
                break;
            case 2:
                time="2 AM TO 3 AM";
                break;
            case 3:
                time="3 AM TO 4 AM";
                break;
            case 4:
                time="4 AM TO 5 PM";
                break;
            case 5:
                time="5 PM TO 6 PM";
                break;
            case 6:
                time="6 PM TO 7 PM";
                break;
            case 7:
                time="7 PM TO 8 PM";
                break;
            case 8:
                time="8 PM TO 9 PM";
                break;
            case 9:
                time="9 PM TO 10 PM";
                break;
            case 10:
                time="10 PM TO 11 PM";
                break;
            case 11:
                time="11 PM TO 12 PM";
                break;

            default:


        }

//        tvFN.setText(fname);
//        tvEM.setText(email);
//        Toast.makeText(this, ProfileActivity.fullname+ProfileActivity.email, Toast.LENGTH_SHORT).show();
        tvArea.setText(area);
        tvTime.setText(time);
        tvLevel.setText(Level);
        btConfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
                    trnid= String.valueOf(Calendar.getInstance().getTimeInMillis()/BookSlot1.Area)+String.valueOf(BookSlot1.Area);
//                    //makePayment();
//                } catch (AppNotFoundException e) {
//                    e.printStackTrace();
//                }
                onlinePayment("LDRPITR","Prepaid","100.00");
                String amount = "10.00";
                String upiid = "vasukotadiya224@okaxis";
                String note="test";
                String name="Pay Park";
                //payUsingUpi(amount,upiid,note,name);

            }
        });

    }
    private void onlinePayment(String buyerAddress, String method, String amount) {

        final Activity activity =this;

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_jljHQIGmW8hXph");
        checkout.setImage(R.drawable.logo);

        double finalAmount = Float.parseFloat(amount)*100;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Joy");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#C83232");
            options.put("currency", "INR");
            options.put("amount", ""+finalAmount);//pass amount in currency subunits
            options.put("prefill.email", "joyapp@gmail.com");
            options.put("prefill.contact","9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }

    }
    private void payUsingUpi(String amount, String upiid, String note, String name) {
        Uri uri=Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa",upiid)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn",note)
                .appendQueryParameter("am",amount)
                .appendQueryParameter("cu","INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent,"Pay with");

        if(null != chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser,UPI_PAYMENT);
        }else {
            Toast.makeText(this, "No UPI apps found in your phone!!!", Toast.LENGTH_LONG).show();
        }
    }

    void makePayment() throws AppNotFoundException {

        // on below line we are calling an easy payment method and passing

        // all parameters to it such as upi id,name, description and others.

        EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder(SlotBooking.this)

                .with()

                // on below line we are adding upi id.

                .setPayeeVpa("vasukotadiya224@okaxixs")

                // on below line we are setting name to which we are making payment.

                .setPayeeName("Pay Park")

                // on below line we are passing transaction id.

                .setTransactionId(trnid)

                // on below line we are passing transaction ref id.

                .setTransactionRefId(trnid)

                // on below line we are adding description to payment.

                .setDescription("test")

                // on below line we are passing amount which is being paid.

                .setAmount("10.00")
                .setPayeeMerchantCode("15hjgjk32135")

                // on below line we are calling a build method to build this ui.

                .build();

        // on below line we are calling a start

        // payment method to start a payment.

        easyUpiPayment.startPayment();

        // on below line we are calling a set payment

        // status listener method to call other payment methods.

        easyUpiPayment.setPaymentStatusListener(this);

    }


    //@Override

    public void onTransactionCompleted(TransactionDetails transactionDetails) {


        //String transcDetails = transactionDetails.getStatus().toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId();

        //transactionDetailsTV.setVisibility(View.VISIBLE);


        //transactionDetailsTV.setText(transcDetails);

    }


//    @Override

    public void onTransactionSuccess() {


        Toast.makeText(this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show();

    }


    //@Override

    public void onTransactionSubmitted() {


        Log.e("TAG", "TRANSACTION SUBMIT");

    }


    //@Override

    public void onTransactionFailed() {


        Toast.makeText(this, "Failed to complete transaction", Toast.LENGTH_SHORT).show();

    }


    //@Override

    public void onTransactionCancelled() {
        DateFormat df= new SimpleDateFormat("yyyyMMddHHmmss");
        pnp=df.format(Calendar.getInstance().getTime());
//        pnp= String.valueOf(Calendar.YEAR)+String.valueOf(Calendar.DAY_OF_YEAR)+
//                String.valueOf(Calendar.HOUR_OF_DAY)+String.valueOf(Calendar.MINUTE)+
//                String.valueOf(Calendar.SECOND)+String.valueOf(Calendar.MILLISECOND);
        DocumentReference documentReference2= fstore.collection("History")
                .document(String.valueOf(Area)+String.valueOf(scroll.time)+Calendar.getInstance().getTimeInMillis());
        Map<String,Object> history =new HashMap<>();
        history.put("e-mail",email);
        history.put("name",fname);
        history.put("area",area);
        history.put("time",time);
        history.put("PNP",pnp);
        history.put("transactionID",trnid);
        history.put("Level",Level);
        documentReference2.set(history).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
//                Toast.makeText(SlotBooking.this, "Slot Booked Successfully", Toast.LENGTH_LONG).show();
                Notify("Slot Booked Successfully","To Download Recipt Please Visit History Section");
                createAlert();
                if(checkPermission()){
                    generatePDF(fname,email,area,time,trnid,pnp,Level);

                }
                else {
                    requestPermission();
                }
            }
        });

        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();

    }

    private void createAlert() {

        Intent intent=new Intent(this,ReminderBroadcast.class);
        PendingIntent pendingIntent1=PendingIntent.getBroadcast(SlotBooking.this,0,intent,FLAG_MUTABLE);

        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtBttonClick =System.currentTimeMillis();
        long tensec=1000*10;
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),pendingIntent1);
    }

    private  void Notify(String msgtitle, String msgtext) {
        Uri custom=Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.notify_sound);

//        Uri custom=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+getPackageName()+"/raw/notify_sound.mp3");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(SlotBooking.this, "My Notification");
        builder.setSmallIcon(R.drawable.logo1);
        builder.setContentTitle(msgtitle);
        builder.setContentText(msgtext);
        builder.setSound(custom);
        builder.setAutoCancel(true);

        Intent intent=new Intent(SlotBooking.this,History.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message1",msgtitle);
        PendingIntent pendingIntent= null;
            pendingIntent = PendingIntent.getActivity(SlotBooking.this,0,intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE);

        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(SlotBooking.this);
        managerCompat.notify(1,builder.build());
    }


    //@Override

    public void onAppNotFound() {


        Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show();

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("ResourceType")
    public void  generatePDF(String Fullname, String Email, String Area, String Time, String Tnsid,String PNP,String Levels) {
        document=new PdfDocument();
        PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(pdfWidth,pdfHeight,1).create();
        PdfDocument.Page page=document.startPage(pageInfo);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.logo1);
        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.verfy);

        Canvas canvas=page.getCanvas();
        Paint paintText=new Paint();
        paintText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        paintText.setTextSize(8);
        paintText.setColor(ContextCompat.getColor(this,R.color.black));
        paintText.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("PDF Generated on "+String.valueOf(Calendar.getInstance().getTime()),10,10,paintText);

        paintText.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD,Typeface.NORMAL));
        paintText.setTextSize(25);
        paintText.setColor(ContextCompat.getColor(this,R.color.black));
        paintText.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Invoice ",380,50,paintText);


        paintText.setTextAlign(Paint.Align.LEFT);

        canvas.drawBitmap(bitmap,50,90,null);
        paintText.setTextSize(27);
        canvas.drawText("Pay & Park®",250,190,paintText);

        paintText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        paintText.setTextSize(17);
        canvas.drawText("Name :",50,300,paintText);
        canvas.drawText("Email :",50,350,paintText);
        canvas.drawText("Time :",50,400,paintText);
        canvas.drawText("Area :",50,450,paintText);
        canvas.drawText("Level :",50,500,paintText);
        canvas.drawText("Transaction ID :",50,550,paintText);
        canvas.drawText("PNP :",50,600,paintText);
        paintText.setColor(ContextCompat.getColor(this,R.color.grey_30));
        canvas.drawText(Fullname,200,300,paintText);
        canvas.drawText(Email,200,350,paintText);
        canvas.drawText(Time,200,400,paintText);
        canvas.drawText(Area,200,450,paintText);
        canvas.drawText(Levels,200,500,paintText);
        canvas.drawText(Tnsid,200,550,paintText);
        canvas.drawText(PNP,200,600,paintText);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD,Typeface.NORMAL));
        paintText.setColor(ContextCompat.getColor(this,R.color.black));
        paintText.setTextSize(20);
        canvas.drawText("------------------------------------------------------------------------------------------------------------------------"
                ,10,700,paintText);
        canvas.drawText("Amount :",400,710,paintText);
        canvas.drawText("100.00",600,710,paintText);
        canvas.drawBitmap(bitmap1,200,810,null);


        document.finishPage(page);
        createFile();
    }
    public static final int CREATE_FILE=1;
    private  void createFile() {
        Intent intent=new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE,"invoice_"+Calendar.getInstance().getTimeInMillis()+".pdf");
        intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME,"com.example.authentication");
        startActivityForResult(intent,CREATE_FILE);
    }



    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode==CREATE_FILE
                    && resultCode== Activity.RESULT_OK){
            Uri uri=null;
            if(resultData!=null){
                uri =resultData.getData();

                if (document!=null){
                    ParcelFileDescriptor pfd = null;
                    try {
                        pfd =getContentResolver().openFileDescriptor(uri,"w");
                        FileOutputStream fileOutputStream=
                                new FileOutputStream(pfd.getFileDescriptor());
                        document.writeTo(fileOutputStream);
                        document.close();
                        MainActivity.booked.get(Area).get(levels.level).set(scroll.time, 1);

                        Toast.makeText(SlotBooking.this, String.valueOf(scroll.cheBook()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Pdf Saved Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,HomePage.class));
                    }catch (IOException e){
                        try {
                            DocumentsContract.deleteDocument(getContentResolver(),uri);
                        }catch (FileNotFoundException ex){
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            }
        }else {
            startActivity(new Intent(this,HomePage.class));

        }
    }

    private boolean checkPermission() {
        int permission1= ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int permission2= ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);


        return permission1== PackageManager.PERMISSION_GRANTED&&
                permission2==PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {
        DateFormat df= new SimpleDateFormat("yyyyMMddHHmmss");
        pnp=df.format(Calendar.getInstance().getTime());
//        pnp= String.valueOf(Calendar.YEAR)+String.valueOf(Calendar.DAY_OF_YEAR)+
//                String.valueOf(Calendar.HOUR_OF_DAY)+String.valueOf(Calendar.MINUTE)+
//                String.valueOf(Calendar.SECOND)+String.valueOf(Calendar.MILLISECOND);
        DocumentReference documentReference2= fstore.collection("History")
                .document(String.valueOf(Area)+String.valueOf(scroll.time)+Calendar.getInstance().getTimeInMillis());
        Map<String,Object> history =new HashMap<>();
        history.put("e-mail",email);
        history.put("name",fname);
        history.put("area",area);
        history.put("time",time);
        history.put("PNP",pnp);
        history.put("transactionID",trnid);
        history.put("Level",Level);
        documentReference2.set(history).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
//                Toast.makeText(SlotBooking.this, "Slot Booked Successfully", Toast.LENGTH_LONG).show();
                Notify("Slot Booked Successfully","To Download Recipt Please Visit History Section");
                createAlert();
                if(checkPermission()){
                    generatePDF(fname,email,area,time,trnid,pnp,Level);

                }
                else {
                    requestPermission();
                }
            }
        });

        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();

    }

//    public void PdfDocument(){
//        mNativeDocument = nativeCreateDocument();
//        mCloseGuard.open("close");
//    }


}