package com.example.authentication;


import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {
    //TextView tvFullname,tvEmail,tvAge;
    private FirebaseUser user;
    private DatabaseReference reference;
    //private String userID;
    String userID;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    public static String age;
    public static String fullname;
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


//        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference= FirebaseDatabase.getInstance().getReference("Users");
//        userID= user.getUid();

          //tvFullname= findViewById(R.id.tv_fullname);
          //tvEmail= findViewById(R.id.tv_email);
          //tvAge= findViewById(R.id.tv_age);
//        Toast.makeText(ProfileActivity.this, userID, Toast.LENGTH_SHORT).show();

        final TextView tvFullname=(TextView) findViewById(R.id.tv_fullname);
        final TextView tvEmail=(TextView)findViewById(R.id.tv_email);
        final TextView tvAge=(TextView)findViewById(R.id.tv_age);

        fAuth=FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fstore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fullname=documentSnapshot.getString("fullname");
                tvFullname.setText(fullname);
                email=documentSnapshot.getString("email");
                tvEmail.setText(email);
                age=documentSnapshot.getString("age");
                tvAge.setText(age);
            }
        });

//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile=snapshot.getValue(User.class);
//
//                if(userProfile!=null){
//                    Toast.makeText(ProfileActivity.this, "Something happens wrong!!!", Toast.LENGTH_SHORT).show();

//                    String fullname =userProfile.getFullName();
//                    String email =userProfile.getEmail();
//                    String age =userProfile.getAge();
//
//                    tvFullname.setText(fullname);
//                    tvEmail.setText(email);
//                    tvAge.setText(age);


//                }else {
//                    Toast.makeText(ProfileActivity.this, "Something happens wrong!!!", Toast.LENGTH_SHORT).show();
//
//                }
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ProfileActivity.this, "Something happens wrong!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}