package com.example.waterleakage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    ImageView imageLogo;
    TextView tvGreeting;
    TextInputLayout lytCellNo;
    TextInputEditText edtCellNo;
    Button btnLogin,btnRegister;
    String userId,role,email,phoneNum;
    Query checkUser;
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    Intent intent;

    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        imageLogo = findViewById(R.id.imageLogo);
        tvGreeting = findViewById(R.id.tvVerify);
        lytCellNo = findViewById(R.id.lytCellNo);
        edtCellNo = findViewById(R.id.edtCellNo);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar1 = findViewById(R.id.progressBar1);
        btnRegister = findViewById(R.id.btnRegister);

        progressBar1.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtCellNo.getText().toString().length() < 10 && edtCellNo.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, "Please enter 10 characters for cell numbers", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth = FirebaseAuth.getInstance();
                    reference = FirebaseDatabase.getInstance().getReference("users");
                    checkUser = reference.orderByChild("cell").equalTo(edtCellNo.getText().toString().trim());
                    
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.exists())
                            {
                                for(DataSnapshot ds : snapshot.getChildren())
                                {

                                    role = ds.child("role").getValue(String.class);
                                    email = ds.child("email").getValue(String.class);
                                    phoneNum = edtCellNo.getText().toString().trim();


                                }

                                intent = new Intent(Login.this,OTP.class);
                                intent.putExtra("role",role);
                                intent.putExtra("email",email);
                                intent.putExtra("cell",phoneNum);
                                startActivity(intent);

                            }

                            else

                            {
                                Toast.makeText(Login.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });


    }
}