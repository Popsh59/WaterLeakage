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

public class Register extends AppCompatActivity {

    TextView tvWelcome;
    TextInputLayout lytName,lytEmail,lytCell,lytPassword;
    TextInputEditText edtName,edtEmail,edtCell,edtPassword;
    Button btnSubmit;
    ProgressBar progressBar2;
    FirebaseAuth firebaseAuth;
    String name,email,cell,password,role;
    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvWelcome = findViewById(R.id.tvWelcome);
        lytName = findViewById(R.id.lytName);
        lytEmail = findViewById(R.id.lytEmail);
        lytPassword = findViewById(R.id.lytPassword);
        lytCell = findViewById(R.id.lytCell);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtCell = findViewById(R.id.edtCell);
        edtPassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar2 = findViewById(R.id.progressBar2);

        role = "User";
        progressBar2.setVisibility(View.GONE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);

                if(edtName.getText().toString().trim().isEmpty() || edtEmail.getText().toString().trim().isEmpty() || edtCell.getText().toString().trim().isEmpty())
                {
                    lytName.setError("Field required");
                    lytEmail.setError("Field required");
                    lytCell.setError("Field required");
                    progressBar2.setVisibility(View.GONE);
                }
                else
                {

                        email = edtEmail.getText().toString().trim();
                        password = edtPassword.getText().toString().trim();
                        cell = edtCell.getText().toString().trim();
                        name = edtName.getText().toString().trim();

                        firebaseAuth = FirebaseAuth.getInstance();

                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this,
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
                                    progressBar2.setVisibility(View.GONE);
                                    finish();

                                }
                                else
                                {
                                    progressBar2.setVisibility(View.GONE);
                                    Toast.makeText(Register.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });




                }

            }
        });

    }
}