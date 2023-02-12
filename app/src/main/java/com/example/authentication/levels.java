package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class levels extends AppCompatActivity implements View.OnClickListener{
    static int level = 0;
    private Button level1;
    private Button level2;
    private Button level3;
    private Button level4;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        level1 = findViewById(R.id.level1);
        level1.setOnClickListener((View.OnClickListener) this);
        level2 = findViewById(R.id.level2);
        level2.setOnClickListener((View.OnClickListener) this);
        level3 = findViewById(R.id.level3);
        level3.setOnClickListener((View.OnClickListener) this);
        level4 = findViewById(R.id.level4);
        level4.setOnClickListener((View.OnClickListener) this);


    }

    public void displayTost(View v) {
        Toast.makeText(levels.this, "Your level has been selected", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level1:
                level = 1;
                startActivity(new Intent(this,scroll.class));
                break;
            case R.id.level2:
                level = 2;
                startActivity(new Intent(this,scroll.class));
                break;
            case R.id.level3:
                level = 3;
                startActivity(new Intent(this,scroll.class));
                break;
            case R.id.level4:
                level = 4;
                startActivity(new Intent(this,scroll.class));
                break;

        }
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

}