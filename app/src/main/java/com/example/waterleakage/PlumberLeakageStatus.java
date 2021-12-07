package com.example.waterleakage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PlumberLeakageStatus extends AppCompatActivity {

    TextView tvChangeSta,tvPLeakType,tvPLeakD;
    TextInputLayout lytLeakStatus;
    AutoCompleteTextView autoTvStatus;
    Button btnOpenLoc,btnUpdateSta;
    String lat,lon,status,descrip,type,statusId;
    Query checkStatus;
    DatabaseReference reference;
    Leakage leakage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_leakage_status);

        tvChangeSta = findViewById(R.id.tvChangeSta);
        tvPLeakType = findViewById(R.id.tvPLeakType);
        tvPLeakD = findViewById(R.id.tvPLeakD);
        lytLeakStatus = findViewById(R.id.lytLeakStatus);
        autoTvStatus = findViewById(R.id.autoTvStatus);
        btnOpenLoc = findViewById(R.id.btnOpenLoc);
        btnUpdateSta = findViewById(R.id.btnUpdateSta);

        type = getIntent().getStringExtra("LType");
        descrip = getIntent().getStringExtra("LDescrip");
        status = getIntent().getStringExtra("LStatus");
        lon = getIntent().getStringExtra("LLon");
        lat = getIntent().getStringExtra("LLat");

        reference = FirebaseDatabase.getInstance().getReference("leakages");
        checkStatus = reference.orderByChild("leakDescription").equalTo(descrip);

        checkStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    for(DataSnapshot ds : snapshot.getChildren())
                    {
                        leakage = ds.getValue(Leakage.class);
                        statusId = ds.getKey();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(PlumberLeakageStatus.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        tvPLeakType.setText("Leak Type:" + type);
        tvPLeakType.setText("Leak Description:" + descrip);

        ArrayAdapter<String> leakStatusAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.status_list));

        autoTvStatus.setAdapter(leakStatusAdapter);

        autoTvStatus.postDelayed(new Runnable() {
            @Override
            public void run() {

                autoTvStatus.setText(status);
                autoTvStatus.showDropDown();

            }
        },10);


        autoTvStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                status = (String) parent.getItemAtPosition(position);
            }
        });

        btnOpenLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creates an Intent that will load a map of San Francisco
                Uri gmmIntentUri = Uri.parse("geo:"+lat + "," + lon);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }



            }
        });

        btnUpdateSta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> map = new HashMap<>();
                map.put("leakStatus", status);
                reference.child(statusId).updateChildren(map);
                Toast.makeText(PlumberLeakageStatus.this, "Leak status successfully updated.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });


    }
}