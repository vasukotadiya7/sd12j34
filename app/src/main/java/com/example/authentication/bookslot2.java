package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class bookslot2 extends AppCompatActivity implements View.OnClickListener, ExampleDialog.ExampleDialogListener {
    public static int Slot;
    FirebaseAuth fAuth;
    String Area="1",slot="1";
    DatabaseReference databaseReference;
    FirebaseFirestore fstore;
    private static Button slot1;
    private static Button slot2;
    private Button slot3;
    private Button slot4;
    private Button slot5;
    private Button slot6;
    private Button slot7;
    private Button slot8;
    private Button slot9;
    private Button slot10;
    public static int cap;
    static DocumentReference documentReference;
    private static int bkd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslot2);
        slot1=findViewById(R.id.slot1);
        slot1.setOnClickListener(this);
        slot2=findViewById(R.id.slot2);
        slot2.setOnClickListener(this);
        slot3=findViewById(R.id.slot3);
        slot3.setOnClickListener(this);
        slot4=findViewById(R.id.slot4);
        slot4.setOnClickListener(this);
        slot5=findViewById(R.id.slot5);
        slot5.setOnClickListener(this);
        slot6=findViewById(R.id.slot6);
        slot6.setOnClickListener(this);
        slot7=findViewById(R.id.slot7);
        slot7.setOnClickListener(this);
        slot8=findViewById(R.id.slot8);
        slot8.setOnClickListener(this);
        slot9=findViewById(R.id.slot9);
        slot9.setOnClickListener(this);
        slot10=findViewById(R.id.slot10);
        slot10.setOnClickListener(this);


        fstore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        documentReference=fstore.collection(Area).document(slot);
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable  DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//               cap=value.getString("Cap");
//            }
//        });
    }
    public void displayToast(View v){
        Toast.makeText(bookslot2.this,"YOUR SLOT HAS BEEN BOOK",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.slot1:
                Slot=1;
                check();
                break;
            case R.id.slot2:
                Slot=2;
                check();
                break;
            case R.id.slot3:
                Slot=3;
                check();
                break;
            case R.id.slot4:
                Slot=4;
                check();
                break;
            case R.id.slot5:
                Slot=5;
                check();
                break;
            case R.id.slot6:
                Slot=6;
                check();
                break;
            case R.id.slot7:
                Slot=7;
                check();
                break;
            case R.id.slot8:
                Slot=8;
                check();
                break;
            case R.id.slot9:
                Slot=9;
                check();
                break;
            case R.id.slot10:
                Slot=10;
                check();
                break;
        }
    }

//    public static void sendCap(){
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable  DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                cap= Integer.parseInt(value.getString("Cap"));
//                slot1.setText(String.valueOf(cap));
//
//            }
//        });
////        return MainActivity.capacity[BookSlot1.Area][bookslot2.Slot];
//    }
    public static int sendCap(){
        return MainActivity.capacity[BookSlot1.Area][bookslot2.Slot];
    }

    public static int cheBook(){
        return MainActivity.booked[BookSlot1.Area][bookslot2.Slot];
    }
//    public static void cheBook(){
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable  DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                bkd= Integer.parseInt(value.getString("Bkd"));
//                slot2.setText(String.valueOf(bkd));
//
//            }
//        });
////        return MainActivity.booked[BookSlot1.Area][bookslot2.Slot];
//    }

    public void openDialog(){
            ExampleDialog exampleDialog=new ExampleDialog();
            exampleDialog.show(getSupportFragmentManager(),"example dialog");
    }

    public void check(){
//        sendCap();
//        Toast.makeText(this, "Capacity :"+cap, Toast.LENGTH_SHORT).show();
        if(AdminPage.cs==1){
            openDialog();
        }else {
//            sendCap();
//            cheBook();
            if (sendCap()==cheBook()){
                Toast.makeText(this, "This time slot is full!!    Please try different time or location", Toast.LENGTH_SHORT).show();

            }
            else {
                startActivity(new Intent(this, SlotBooking.class));
            }
        }

    }

    @Override
    public void applyTexts(int cap) {
        cheBook();
        if(cap<cheBook()){
            Toast.makeText(this, "Booked slot is less than new capacity", Toast.LENGTH_SHORT).show();
        }
        else {
            MainActivity.capacity[BookSlot1.Area][bookslot2.Slot]=cap;
            Toast.makeText(this, "Capacity Changed successfully", Toast.LENGTH_LONG).show();
        }
    }
}