package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class BookSlot1 extends AppCompatActivity implements View.OnClickListener {
    public static int Area;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth mAuth;
    private TextView sector16;
    private TextView sector24;
    private TextView sector8;
    private TextView sector14;
    private TextView sector30;
    private TextView sector2;
    private TextView sector21;
    private TextView sector10;
    private TextView sector7;
    private TextView sector20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot1);
        sector16= (TextView) findViewById(R.id.sector16);
        sector16.setOnClickListener(this);
        sector24= (TextView) findViewById(R.id.sector24);
        sector24.setOnClickListener(this);
        sector8= (TextView) findViewById(R.id.sector8);
        sector8.setOnClickListener(this);
        sector14= (TextView) findViewById(R.id.sector14);
        sector14.setOnClickListener(this);
        sector30= (TextView) findViewById(R.id.sector30);
        sector30.setOnClickListener(this);
        sector2= (TextView) findViewById(R.id.sector2);
        sector2.setOnClickListener(this);
        sector21= (TextView) findViewById(R.id.sector21);
        sector21.setOnClickListener(this);
        sector10= (TextView) findViewById(R.id.sector10);
        sector10.setOnClickListener(this);
        sector7= (TextView) findViewById(R.id.sector7);
        sector7.setOnClickListener(this);
        sector20 = (TextView) findViewById(R.id.sector20);
        sector20.setOnClickListener(this);



//        user = FirebaseAuth.getInstance().getCurrentUser();
       // reference= FirebaseDatabase.getInstance().getReference("Users");
//        userID= user.getUid();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sector16:
                Area=1;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector20:
                Area=6;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector24:
                Area=2;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector8:
                Area=3;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector14:
                Area=4;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector30:
                Area=5;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector2:
                Area=7;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector21:
                Area=8;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector10:
                Area=9;
                startActivity(new Intent(this,bookslot2.class));
                break;
            case R.id.sector7:
                Area=10;
                startActivity(new Intent(this,bookslot2.class));
                break;

}}}

