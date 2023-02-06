package com.example.authentication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<HistoryModel> historyModelArrayList;
    RecyclerView.Adapter myAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    public static  String useremail,userID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        firebaseFirestore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                useremail=value.getString("email");
            }
        });
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        recyclerView=findViewById(R.id.rcy_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyModelArrayList=new ArrayList<HistoryModel>();
        myAdapter=new MyAdapter(History.this,historyModelArrayList);
        recyclerView.setAdapter(myAdapter);

        GetHistory();
    }
    public void GetHistory() {
        firebaseFirestore.collection("History").whereEqualTo("e-mail",HomePage.email).orderBy("PNP", Query.Direction.DESCENDING)
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
                            if(dc.getType()==DocumentChange.Type.ADDED  ||
                                    dc.getType()==DocumentChange.Type.MODIFIED ||
                                    dc.getType()==DocumentChange.Type.REMOVED ){
                                historyModelArrayList.add(dc.getDocument().toObject(HistoryModel.class));
                            }
                            myAdapter.notifyDataSetChanged();

                        }
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });

    }
}