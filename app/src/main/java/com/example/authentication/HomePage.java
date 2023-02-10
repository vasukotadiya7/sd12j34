package com.example.authentication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomePage extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public Button book,help,mapp;
    TextView tvName;
    public int v;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    public static String email;
    public static String fullname;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                startActivity(new Intent(this,ProfileActivity.class));
                break;
            case R.id.nav_history:
                startActivity(new Intent(this,History.class));
                break;
            case R.id.nav_logout:
                logoutdialog();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fAuth=FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID=fAuth.getCurrentUser().getUid();


        DocumentReference documentReference=fstore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                email=documentSnapshot.getString("email");
                fullname=documentSnapshot.getString("fullname");

//                tvName.setText("Hello "+v);
                tvName.setText("Hello "+documentSnapshot.getString("fullname"));
            }
        });
//        String s="1";
//        capslot(s);
//        Toast.makeText(this, String.valueOf(t), Toast.LENGTH_SHORT).show();


        drawer =findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        book = (Button) findViewById(R.id.book);
        book.setOnClickListener((View.OnClickListener) this);


        tvName=findViewById(R.id.tv_name);


        help = (Button) findViewById(R.id.help);
        help.setOnClickListener((View.OnClickListener) this);
        mapp= (Button)  findViewById(R.id.mapp);
        mapp.setOnClickListener((View.OnClickListener) this);

    }

    public void capslot(String area){
        DocumentReference documentReference=fstore.collection("Capacity").document(area);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                int v= Integer.parseInt(documentSnapshot.getString(area));
                tvName.setText("Hello "+v);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            logoutdialog();
        }
    }

    private void logoutdialog() {
        new AlertDialog.Builder(this)
                .setMessage("Are You Sure To Logout from current account?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Logout();

                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MainActivity.class));
        return;
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.book:
                startActivity(new Intent(this, BookSlot1.class));
                break;
            case R.id.payment:
                startActivity(new Intent(this, payment.class));
                break;
            case R.id.help:
                startActivity(new Intent(this, HELP2.class));
                break;
            case R.id.mapp:
                startActivity(new Intent(this, MapTrackDemo.class));
                break;
//            case R.id.profile:
//                startActivity(new Intent(this, ProfileActivity.class));
//                break;
//            case R.id.logout:
//                logoutdialog();
//                break;
        }
    }

//    @Override
//    public void onClick(View view) {
//
//    }
}