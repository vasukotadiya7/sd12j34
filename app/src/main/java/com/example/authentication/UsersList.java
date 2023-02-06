package com.example.authentication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    RecyclerView.Adapter adapter;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
            progressDialog=new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        recyclerView=findViewById(R.id.firestorelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore=FirebaseFirestore.getInstance();
        userArrayList=new ArrayList<User>();
        adapter=new Adapter(UsersList.this,userArrayList);
        recyclerView.setAdapter(adapter);
        GetUsers();
        }

    private void GetUsers() {
        firebaseFirestore.collection("Users").orderBy("fullname", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error!=null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.d("Firestore Erorr",error.getMessage());
                            return;
                        }
                        for (DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(User.class));
                            }
                            adapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });

    }
}
