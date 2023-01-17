package com.example.authentication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class payment extends AppCompatActivity implements PaymentStatusListener {
    EditText amountent,upiident,amount;
    static Button pay;
    static final int UPI_PAYMENT = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceStaes){
        super.onCreate(savedInstanceStaes);
        setContentView(R.layout.activity_payment);

//        amount=findViewById(R.id.amount);
        pay=findViewById(R.id.idBtnMakePayment);
//        upiid=findViewById(R.id.upiid);

//        initializeViews();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    makePayment();
                } catch (AppNotFoundException e) {
                    e.printStackTrace();
                }
//                String amount = amountent.getText().toString();
                String amount = "100";
//                String upiid = upiident.getText().toString();
                String upiid = "6353359477@paytm";
                String note="sector 16 time 3pm to 4pm";
                String name="Vasu";
                payUsingUpi(amount,upiid,note,name);


            }
        });
    }


    void initializeViews(){
        pay=findViewById(R.id.idBtnMakePayment);
        amountent=findViewById(R.id.idEdtAmount);
        upiident=findViewById(R.id.idEdtUpi);
    }

    void payUsingUpi(String amount, String upiid, String note, String name){

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
       // void onActivityResult(int requestCode,int resultCode,Intent data){

         //           super.onActivityResult(requestCode,resultCode,data);
           //         switch (requestCode){
             //           case UPI_PAYMENT:
               //             if (RESULT_OK == resultCode || resultCode == 11) {

                 //   }
        }
    void makePayment() throws AppNotFoundException {

        // on below line we are calling an easy payment method and passing

        // all parameters to it such as upi id,name, description and others.

         EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder(payment.this)

                 .with()

                // on below line we are adding upi id.

                .setPayeeVpa("vasukotadiya224@okaxixs")

                // on below line we are setting name to which we are making payment.

                .setPayeeName("Pay Park")

                // on below line we are passing transaction id.

                .setTransactionId("123abc456def")

                // on below line we are passing transaction ref id.

                .setTransactionRefId("123abc456def")

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


        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();

    }


    //@Override

    public void onAppNotFound() {


        Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}












//
//public class payment extends AppCompatActivity {
//
//    EditText amount;
//    Button pay;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);
//
//        amount=findViewById(R.id.amount);
//        pay=findViewById(R.id.pay);
//
//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PaymentGatewayStart();
//            }
//        });
//    }
//
//    public void PaymentGatewayStart() {
//        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(payment.this)
//                .setAmount("100")
//                .build()
//    }
//}

//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//
//import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
//import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
//import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
//import dev.shreyaspatil.easyupipayment.model.TransactionDetails;
//
//public class payment extends AppCompatActivity implements PaymentStatusListener {
//
//
//
//    // initializing variables for our edit text and button.
//
//    private EditText amountEdt, upiEdt, nameEdt, descEdt;
//
//    private TextView transactionDetailsTV;
//
//
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_payment);
//
//
//
//        // initializing all our variables.
//
//        amountEdt = findViewById(R.id.idEdtAmount);
//
//        upiEdt = findViewById(R.id.idEdtUpi);
//
//        nameEdt = findViewById(R.id.idEdtName);
//
//        descEdt = findViewById(R.id.idEdtDescription);
//
//        Button makePaymentBtn = findViewById(R.id.idBtnMakePayment);
//
//        transactionDetailsTV = findViewById(R.id.idTVTransactionDetails);
//
//
//
//        // on below line we are getting date and then we are setting this date as transaction id.
//
//        Date c = Calendar.getInstance().getTime();
//
//        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
//
//        String transcId = df.format(c);
//
//
//
//        // on below line we are adding click listener for our payment button.
//
//        makePaymentBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//
//            public void onClick(View v) {
//
//                // on below line we are getting data from our edit text.
//
//                String amount = amountEdt.getText().toString();
//
//                String upi = upiEdt.getText().toString();
//
//                String name = nameEdt.getText().toString();
//
//                String desc = descEdt.getText().toString();
//
//                // on below line we are validating our text field.
//
//                if (TextUtils.isEmpty(amount) && TextUtils.isEmpty(upi) && TextUtils.isEmpty(name) && TextUtils.isEmpty(desc)) {
//
//                    Toast.makeText(payment.this, "Please enter all the details..", Toast.LENGTH_SHORT).show();
//
//                } else {
//
//                    // if the edit text is not empty then
//
//                    // we are calling method to make payment.
//
//                    try {
//                        makePayment(amount, upi, name, desc, transcId);
//                    } catch (AppNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            }
//
//        });
//
//    }
//
//
//    private void makePayment(String amount, String upi, String name, String desc, String transactionId) throws AppNotFoundException {
//
//        // on below line we are calling an easy payment method and passing
//
//        // all parameters to it such as upi id,name, description and others.
//
//         EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder(payment.this)
//
//                 .with()
//
//                // on below line we are adding upi id.
//
//                .setPayeeVpa(upi)
//
//                // on below line we are setting name to which we are making payment.
//
//                .setPayeeName(name)
//
//                // on below line we are passing transaction id.
//
//                .setTransactionId(transactionId)
//
//                // on below line we are passing transaction ref id.
//
//                .setTransactionRefId(transactionId)
//
//                // on below line we are adding description to payment.
//
//                .setDescription(desc)
//
//                // on below line we are passing amount which is being paid.
//
//                .setAmount(amount)
//
//                // on below line we are calling a build method to build this ui.
//
//                .build();
//
//        // on below line we are calling a start
//
//        // payment method to start a payment.
//
//        easyUpiPayment.startPayment();
//
//        // on below line we are calling a set payment
//
//        // status listener method to call other payment methods.
//
//        easyUpiPayment.setPaymentStatusListener(this);
//
//    }
//
//
//    @Override
//
//    public void onTransactionCompleted(TransactionDetails transactionDetails) {
//
//
//        //String transcDetails = transactionDetails.getStatus().toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId();
//
//        transactionDetailsTV.setVisibility(View.VISIBLE);
//
//
//        //transactionDetailsTV.setText(transcDetails);
//
//    }
//
//
////    @Override
//
//    public void onTransactionSuccess() {
//
//
//        Toast.makeText(this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show();
//
//    }
//
//
//    //@Override
//
//    public void onTransactionSubmitted() {
//
//
//        Log.e("TAG", "TRANSACTION SUBMIT");
//
//    }
//
//
//    //@Override
//
//    public void onTransactionFailed() {
//
//
//        Toast.makeText(this, "Failed to complete transaction", Toast.LENGTH_SHORT).show();
//
//    }
//
//
//    @Override
//
//    public void onTransactionCancelled() {
//
//
//        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();
//
//    }
//
//
//    //@Override
//
//    public void onAppNotFound() {
//
//
//        Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show();
//
//    }
//}