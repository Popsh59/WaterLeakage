package com.example.waterleakage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UserReportLeakage extends AppCompatActivity {

    TextView tvLeakage,tvLocation;
    TextInputLayout lytLeakDes,lytLeakStatus,lytLeakType,lytLeakEmail;
    TextInputEditText edtLeakDes,edtLeakEmail;
    AutoCompleteTextView autoTvLeakType,autoTvLeakStatus;
    Button btnLeakSubmit,btnGetLoc;
    String leakType,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_leakage);

        tvLeakage = findViewById(R.id.tvLeakage);
        tvLocation = findViewById(R.id.tvLocation);
        lytLeakDes = findViewById(R.id.lytLeakDes);
        lytLeakStatus = findViewById(R.id.lytLeakStatus);
        lytLeakType = findViewById(R.id.lytLeakType);
        lytLeakEmail = findViewById(R.id.lytLeakEmail);
        edtLeakDes = findViewById(R.id.edtLeakDes);
        edtLeakEmail = findViewById(R.id.edtLeakEmail);
        autoTvLeakType = findViewById(R.id.autoTvLeakType);
        autoTvLeakStatus = findViewById(R.id.autoTvLeakStatus);
        btnLeakSubmit = findViewById(R.id.btnLeakSubmit);
        btnGetLoc = findViewById(R.id.btnGetLoc);

        tvLocation.setVisibility(View.GONE);

        ArrayAdapter<String> leakTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.leak_list));

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.status_list));


        autoTvLeakStatus.setAdapter(statusAdapter);
        autoTvLeakType.setAdapter(leakTypeAdapter);

        autoTvLeakStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                status =(String) parent.getItemAtPosition(position);
            }
        });

        autoTvLeakType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leakType = (String) parent.getItemAtPosition(position);
            }
        });

    }
}