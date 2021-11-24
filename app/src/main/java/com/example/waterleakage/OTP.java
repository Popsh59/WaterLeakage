package com.example.waterleakage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class OTP extends AppCompatActivity {

    ImageView imageLog;
    TextView tvVerify;
    TextInputLayout lytOtp;
    TextInputEditText edtOtp;
    Button btnVerify;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        imageLog = findViewById(R.id.imageLog);
        tvVerify = findViewById(R.id.tvVerify);
        lytOtp = findViewById(R.id.lytOtp);
        edtOtp = findViewById(R.id.edtOtp);
        btnVerify = findViewById(R.id.btnVerify);
        progressBar = findViewById(R.id.progressBar);



    }
}