package com.example.waterleakage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    ImageView imageLog;
    TextView tvVerify;
    TextInputLayout lytOtp;
    TextInputEditText edtOtp;
    Button btnVerify;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String verificationCodeBySystem,cellNum,email,role,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        mAuth = FirebaseAuth.getInstance();

        imageLog = findViewById(R.id.imageLog);
        tvVerify = findViewById(R.id.tvVerify);
        lytOtp = findViewById(R.id.lytOtp);
        edtOtp = findViewById(R.id.edtOtp);
        btnVerify = findViewById(R.id.btnVerify);
        progressBar = findViewById(R.id.progressBar);

        cellNum = ApplicationClass.userCell;
        email = ApplicationClass.userEmail;
        role = ApplicationClass.userRole;
        String cel = "+27"+ cellNum;

        sendVerificationCodeToUser(cel);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code =  edtOtp.getText().toString();
                if(code.isEmpty() || code.length() < 6)
                {
                    progressBar.setVisibility(View.GONE);
                  lytOtp.setError("Wrong code entered ...");
                  lytOtp.requestFocus();
                  return;
                }
                else
                    {
                        verifyCode(code);
                    }



            }
        });
        
    }

    private void sendVerificationCodeToUser(String cellNum)
    {


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(cellNum)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.MINUTES) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

             code = phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                edtOtp.setText(code);
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }


        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void verifyCode(String verificationCode)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, verificationCode);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent;
                            if(role.equals("User"))
                            {
                                startActivity(new Intent(OTP.this,UserHomeScreen.class));

                            }
                            else if(role.equals("Plumber"))
                            {
                                startActivity(new Intent(OTP.this,PlumberHomeScreen.class));
                            }
                            else
                            {
                                startActivity(new Intent(OTP.this,AdminHomeScreen.class));
                            }

                        }
                        else {

                            Toast.makeText(OTP.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                });

    }

}