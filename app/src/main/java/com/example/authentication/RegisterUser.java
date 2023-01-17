package com.example.authentication;

import static com.google.firebase.messaging.Constants.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    public TextView banner;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    public Button signIn,registerUser;
    private FirebaseAuth mAuthentication;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuthentication = FirebaseAuth.getInstance();
        banner =  findViewById(R.id.banner);
        banner.setOnClickListener(this);

        signIn=findViewById(R.id.signin);
        signIn.setOnClickListener(this);
        registerUser =  findViewById(R.id.registeruser);
        registerUser.setOnClickListener(this);

        editTextFullName =findViewById(R.id.fullName);
        editTextAge =  findViewById(R.id.age);

        editTextEmail =  findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        progressBar =  findViewById(R.id.progressBar);
        fstore=FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
            case R.id.signin:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registeruser:
                registerUser();
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullname = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if (fullname.isEmpty()) {
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is Required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid Email");
            editTextEmail.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            editTextAge.setError("Age is Required!");
            editTextAge.requestFocus();
            return;
        }
        int i = Integer.parseInt(age);

        if (i < 18) {
            editTextAge.setError("Not eligible for parking");
            editTextAge.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Min password must be greater than 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuthentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            User user = new User(fullname, age, email);
                            userID=mAuthentication.getCurrentUser().getUid();
                            DocumentReference documentReference= fstore.collection("Users")
                                            .document(userID);
                            Map<String,Object> user =new HashMap<>();
                            user.put("fullname",fullname);
                            user.put("email",email);
                            user.put("age",age);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+e.toString());
                                }
                            });
                            Toast.makeText(RegisterUser.this, "User Has Been Registered Successfully!", Toast.LENGTH_LONG).show();


//                            Calendar FirebaseDatabase;
//                            FirebaseDatabase.getInstance().getReference("Users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(RegisterUser.this, "User Has Been Registered Successfully!", Toast.LENGTH_LONG).show();
//                                                progressBar.setVisibility(View.VISIBLE);
//
//
//                                            } else {
//                                                Toast.makeText(RegisterUser.this, "Failed To Register! Try Again", Toast.LENGTH_SHORT).show();
//                                                progressBar.setVisibility(View.GONE);
//
//                                            }
//                                        }
//                                    });

                        } else {
                            Toast.makeText(RegisterUser.this, "Failed To Register! Try Again", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
