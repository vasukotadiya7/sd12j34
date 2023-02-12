package com.example.authentication;

//import static com.example.authentication.bookslot2.cap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class scroll extends AppCompatActivity {
    Spinner spinner;
    static int time;
    String abc;
    String[] slot={"select your slot","01:00 AM","02:00 AM","03:00 AM","04:00 AM","05:00 AM","06:00 AM","07:00 AM","08:00 AM","09:00 AM","10:00 AM","11:00 AM","12:00 PM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        spinner =findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(scroll.this, android.R.layout.simple_spinner_item,slot);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String jawab=adapterView.getItemAtPosition(i).toString();
                abc=jawab;
                ItemJ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                abc="0";
            }


        });

    }

    private void ItemJ() {

        switch (abc){
            case "01:00 AM":
                time=1;
                check();
                break;
            case "02:00 AM":
                time=2;
                check();
                break;
            case "03:00 AM":
                time=3;
                check();
                break;
            case "04:00 AM":
                time=4;
                check();
                break;
            case "05:00 AM":
                time=5;
                check();
                break;
            case "06:00 AM":
                time=6;
                check();
                break;
            case "07:00 AM":
                time=7;
                check();
                break;
            case "08:00 AM":
                time=8;
                check();
                break;
            case "09:00 AM":
                time=9;
                check();
                break;
            case "10:00 AM":
                time=10;
                check();
                break;
            case "11:00 AM":
                time=11;
                check();
                break;
            case "12:00 PM":
                time=12;
                check();
                break;
            default:
                Toast.makeText(this, "select any one slot", Toast.LENGTH_SHORT).show();

        }
    }

    public static int sendCap() {
        return    MainActivity.capacity.get(BookSlot1.Area).get(levels.level).get(scroll.time);

    }

    public static int cheBook() {
        return MainActivity.booked.get(BookSlot1.Area).get(levels.level).get(scroll.time);
    }




    public void check() {
//        sendCap();
//        Toast.makeText(this, "Capacity :"+cap, Toast.LENGTH_SHORT).show();

//            sendCap();
//            cheBook();
            if (sendCap() == cheBook()) {
                Toast.makeText(this, "This time slot is full!!    Please try different time or location", Toast.LENGTH_SHORT).show();

            } else {
                startActivity(new Intent(this, SlotBooking.class));
            }


    }


}