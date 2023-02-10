package com.example.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {
    private Button addSlot,resetSlot,changeCap,users;
    public static int cs=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);


        resetSlot=findViewById(R.id.bt_bkd_slot);
        resetSlot.setOnClickListener(this);
        changeCap=findViewById(R.id.bt_chg_cap);
        changeCap.setOnClickListener(this);
        users=findViewById(R.id.bt_users);
        users.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bt_bkd_slot:
                for (int i=0;i<=10;i++){
                    for (int j=0;j<=10;j++){
                        for (int k = 0; k < 10; k++) {
                            MainActivity.booked.get(i).get(j).set(k, 0);
                        }

                    }
                }
                Toast.makeText(this, "All slots are reseted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_chg_cap:
                cs=1;
                startActivity(new Intent(this,BookSlot1.class));
                break;
            case R.id.bt_users:
                startActivity(new Intent(this,UsersList.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        cs=0;
        startActivity(new Intent(this,MainActivity.class));
    }
}