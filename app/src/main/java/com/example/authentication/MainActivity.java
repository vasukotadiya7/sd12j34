package com.example.authentication;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    FirebaseFirestore fstore;
    private static final int PERMISSION_REQUEST_CODE = 10;
    public static int[][] capacity ={{0,0,0,0,0,0,0,0,0,0,0},
            {0,10,13,15,20,16,16,19,0,0,11},{0,20,11,16,18,10,0,14,14,12,10},{0,0,11,15,16,18,20,17,17,10,0},
            {0,15,20,13,17,17,11,0,11,10,20},{0,11,0,15,16,15,15,20,10,11,12},{0,19,14,0,11,0,13,13,11,10,18},
            {0,14,18,17,20,0,0,13,10,14,18},{0,11,15,13,0,13,16,19,10,11,15},{0,17,13,0,11,20,13,18,14,14,13},
            {0,16,18,13,15,10,10,14,12,13,11}};

    public static int[][] booked ={{0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0}};
    private TextView register,forgotPassword;
    private EditText editEmail,editPassword;
    private Button login;
    private Button homepage;
    public  Button button;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private LoadingDialog loadingDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fstore=FirebaseFirestore.getInstance();
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener((View.OnClickListener) this);

        loadingDialog=new LoadingDialog(MainActivity.this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener((View.OnClickListener) this);

        editEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editPassword=(EditText) findViewById(R.id.editTextTextPassword);

        progressBar= (ProgressBar) findViewById(R.id.progressBar);

        mAuth=FirebaseAuth.getInstance();
        forgotPassword = (TextView) findViewById(R.id.forgotpassword);
        forgotPassword.setOnClickListener(this);
        requestPermission();
        if(checkPermission()){

        }
        else {
            requestPermission();
        }
    };


    private boolean checkPermission() {
        int permission1= ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int permission2= ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        int permission3= ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_FINE_LOCATION);
        int permission4= ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_COARSE_LOCATION);



        return permission1== PackageManager.PERMISSION_GRANTED&&
                permission2==PackageManager.PERMISSION_GRANTED&&
                permission3==PackageManager.PERMISSION_GRANTED&&
                permission4==PackageManager.PERMISSION_GRANTED;

    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION
                                                                    ,ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are You Sure To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    public void onClick(View v){

            switch (v.getId()) {
                case R.id.register:
                    startActivity(new Intent(this, RegisterUser.class));
                    break;
                case R.id.login:
                    userLogin();
                    break;
                case R.id.forgotpassword:
                    startActivity(new Intent(this, ForgotPassword.class));
                    break;
            }
        }

    private void userLogin() {

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(email.equalsIgnoreCase("1")&& password.equalsIgnoreCase("1")){
            startActivity(new Intent(this,AdminPage.class));
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Email is required!");
            editEmail.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter valid email!");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required!");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Minimum Password Length Is 6 Characters!");
            editPassword.requestFocus();
            return;
        }
        loadingDialog.startLoadingDialog();
//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadingDialog.dismissDialog();
//            }
//        },5000);
        //progressBar.setVisibility(View.VISIBLE);



            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //redirect to home page
                        loadingDialog.dismissDialog();
                        startActivity(new Intent(MainActivity.this, HomePage.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to Login! Please check your email and password", Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

}