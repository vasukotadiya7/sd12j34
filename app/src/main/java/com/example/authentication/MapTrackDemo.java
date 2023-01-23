package com.example.authentication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapTrackDemo extends AppCompatActivity {
    private static final String[] CITY=new String[]{
           "Sector 2,Gandhinagar","Sector 7,Gandhinagar","Sector 8,Gandhinagar",
           "Sector 10,Gandhinagar","Sector 14,Gandhinagar","Sector 16,Gandhinagar",
          "Sector 20,Gandhinagar","Sector 21,Gandhinagar","Sector 24,Gandhinagar",
           "Sector 30,Gandhinagar"};
    EditText etSource, etDestination;
    Button btTrack,btLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_track_demo);

        AutoCompleteTextView editText=findViewById(R.id.et_Destination);
        AutoCompleteTextView editText1=findViewById(R.id.et_source);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,CITY);
        editText.setAdapter(adapter);
        editText1.setAdapter(adapter);
        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_Destination);
        btTrack = findViewById(R.id.bt_track);
//      button=(Button) findViewById(R.id.connect);
        btLocation=findViewById(R.id.bt_track1);
//        FusedLocationProviderClient fusedLocationProviderClient;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapTrackDemo.this);



        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ActivityCompat.checkSelfPermission(MapTrackDemo.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MapTrackDemo.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    getCurrentLocation();
                }
                else{
                    ActivityCompat.requestPermissions(MapTrackDemo.this,new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                    ,Manifest.permission.ACCESS_COARSE_LOCATION}
                            ,100 );
                }
            }
        });
        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sSource = etSource.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();

                if (sSource.equals("") || sDestination.equals("")) {

                    Toast.makeText(getApplicationContext(), "Please input source and destination both", Toast.LENGTH_SHORT).show();

                } else {
                    DisplayTrack(sSource, sDestination);


                }
            }
        });



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0
                && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager=(LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                     Location location=task.getResult();
                     if(location!=null){
                         etSource.setText(String.valueOf(location.getLatitude()+","+String.valueOf(location.getLongitude())));
                     }else {
                         LocationRequest locationRequest = new LocationRequest()
                                 .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                 .setInterval(10000)
                                 .setFastestInterval(1000)
                                 .setNumUpdates(1);
                         LocationCallback locationCallback=new LocationCallback(){
                             @Override
                             public void onLocationResult(LocationResult locationResult) {
                                 Location location1=locationResult.getLastLocation();
                                 etSource.setText(String.valueOf(location1.getLatitude()+","+String.valueOf(location1.getLongitude())));
                             }
                         };
                         fusedLocationProviderClient.requestLocationUpdates(
                                 locationRequest,locationCallback, Looper.myLooper());
                     }
                }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }



    private void DisplayTrack(String sSource, String sDestination) {

        try {

            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/" + sDestination);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}



