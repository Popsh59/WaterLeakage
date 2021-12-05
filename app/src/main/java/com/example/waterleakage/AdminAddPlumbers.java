package com.example.waterleakage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddPlumbers extends AppCompatActivity {

    TextView tvPlumber;
    TextInputLayout lytPName,lytPEmail,lytPCell,lytPPassword;
    TextInputEditText edtPName,edtPEmail,edtPCell,edtPPassword;
    Button btnPSubmit;
    ProgressBar progressBar5;
    FirebaseAuth firebaseAuth;
    String name,email,cell,password,role;
    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_plumbers);

        tvPlumber = findViewById(R.id.tvPlumber);
        lytPName = findViewById(R.id.lytPName);
        lytPEmail = findViewById(R.id.lytPEmail);
        lytPCell = findViewById(R.id.lytPCell);
        lytPPassword = findViewById(R.id.lytPPassword);
        edtPName = findViewById(R.id.edtPName);
        edtPEmail = findViewById(R.id.edtPEmail);
        edtPCell = findViewById(R.id.edtPCell);
        edtPPassword = findViewById(R.id.edtPPassword);
        btnPSubmit = findViewById(R.id.btnPSubmit);
        progressBar5 = findViewById(R.id.progressBar5);

        role = "Plumber";

        progressBar5.setVisibility(View.GONE);

        btnPSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar5.setVisibility(View.VISIBLE);

                if(edtPName.getText().toString().trim().isEmpty() ||
                        edtPEmail.getText().toString().trim().isEmpty() ||
                        edtPCell.getText().toString().trim().isEmpty())
                {
                    lytPName.setError("Field required");
                    lytPEmail.setError("Field required");
                    lytPCell.setError("Field required");
                    progressBar5.setVisibility(View.GONE);

                }
                else
                {

                    email = edtPEmail.getText().toString().trim();
                    password = edtPPassword.getText().toString().trim();
                    cell = edtPCell.getText().toString().trim();
                    name = edtPName.getText().toString().trim();

                    firebaseAuth = FirebaseAuth.getInstance();

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(AdminAddPlumbers.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful())
                                    {
                                        User user = new User(name,email,cell,password,role);

                                        database = FirebaseDatabase.getInstance();
                                        reference = database.getReference("users");
                                        String userId = reference.push().getKey();
                                        reference.child(userId).setValue(user);
                                        progressBar5.setVisibility(View.GONE);
                                        finish();

                                    }
                                    else
                                    {
                                        progressBar5.setVisibility(View.GONE);
                                        Toast.makeText(AdminAddPlumbers.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                }

            }
        });

    }
}