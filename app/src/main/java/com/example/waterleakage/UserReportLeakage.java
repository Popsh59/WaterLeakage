package com.example.waterleakage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserReportLeakage extends AppCompatActivity {

    TextView tvLeakage, tvLocation;
    TextInputLayout lytLeakDes, lytLeakType, lytLeakEmail;
    TextInputEditText edtLeakDes, edtLeakEmail;
    AutoCompleteTextView autoTvLeakType, autoTvLeakStatus;
    Button btnLeakSubmit, btnGetLoc;
    String leakType, status;
    FusedLocationProviderClient fusedLocationProviderClient;
    double lat , lon;
    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_leakage);

        tvLeakage = findViewById(R.id.tvLeakage);
        tvLocation = findViewById(R.id.tvLocation);
        lytLeakDes = findViewById(R.id.lytLeakDes);
        lytLeakType = findViewById(R.id.lytChoosePlumber);
        lytLeakEmail = findViewById(R.id.lytLeakEmail);
        edtLeakDes = findViewById(R.id.edtLeakDes);
        edtLeakEmail = findViewById(R.id.edtLeakEmail);
        autoTvLeakType = findViewById(R.id.autoTvLeakType);
        btnLeakSubmit = findViewById(R.id.btnLeakSubmit);
        btnGetLoc = findViewById(R.id.btnGetLoc);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        tvLocation.setVisibility(View.GONE);

        ArrayAdapter<String> leakTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.leak_list));


        autoTvLeakType.setAdapter(leakTypeAdapter);
        edtLeakEmail.setText(ApplicationClass.userEmail);
        edtLeakEmail.setEnabled(false);


        autoTvLeakType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leakType = (String) parent.getItemAtPosition(position);
            }
        });

        btnGetLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getLocation();
            }
        });

        btnLeakSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtLeakDes.getText().toString().isEmpty())
                {
                    lytLeakDes.setError("Please enter the description");
                }
                else
                {
                    database = FirebaseDatabase.getInstance();
                    reference = FirebaseDatabase.getInstance().getReference("leakages");
                    Leakage leakage = new Leakage(leakType,"Pending",edtLeakDes.getText().toString().trim(),ApplicationClass.userEmail,"",lat,lon);
                    String leakId = reference.push().getKey();
                    reference.child(leakId).setValue(leakage);
                    Toast.makeText(UserReportLeakage.this, "Leak added successfully...", Toast.LENGTH_SHORT).show();
                    finish();

                }

            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(UserReportLeakage.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            return;
        }
        else
        {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult();
                    if(location != null)
                    {
                        try
                        {
                            Geocoder geocoder = new Geocoder(UserReportLeakage.this, Locale.getDefault());

                            List<Address>  addresses = geocoder.getFromLocation(location.getLatitude(),
                                    location.getLongitude(),1);
                            tvLocation.setVisibility(View.VISIBLE);
                            btnGetLoc.setVisibility(View.GONE);
                            tvLocation.setText("Latitude " + addresses.get(0).getLatitude() + "\n Longitude "
                                    + addresses.get(0).getLongitude());
                            lat = addresses.get(0).getLatitude();
                            lon = addresses.get(0).getLongitude();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });
        }
    }
}