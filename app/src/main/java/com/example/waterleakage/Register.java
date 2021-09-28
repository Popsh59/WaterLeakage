package com.example.waterleakage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {

    TextView tvWelcome;
    TextInputLayout lytName,lytEmail,lytCell;
    TextInputEditText edtName,edtEmail,edtCell;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvWelcome = findViewById(R.id.tvWelcome);
        lytName = findViewById(R.id.lytName);
        lytEmail = findViewById(R.id.lytEmail);
        lytCell = findViewById(R.id.lytCell);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtCell = findViewById(R.id.edtCell);
        btnSubmit = findViewById(R.id.btnSubmit);



    }
}