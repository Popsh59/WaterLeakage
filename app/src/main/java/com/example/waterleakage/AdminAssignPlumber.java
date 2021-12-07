package com.example.waterleakage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminAssignPlumber extends AppCompatActivity {

    TextView tvAdminAssign,tvLeakType,tvLeakDes;
    ImageView ivAdminStatus;
    TextInputLayout lytChoosePlumber;
    AutoCompleteTextView autoTvPlumber;
    Button btnAssignSubmit;
    String plumber,leakType,leakDes,leakStatus,statusId;
    Query assignPlumber,query;
    DatabaseReference reference;
    Leakage leakage;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_assign_plumber);

        tvAdminAssign = findViewById(R.id.tvAdminAssign);
        tvLeakType = findViewById(R.id.tvLeakType);
        tvLeakDes = findViewById(R.id.tvLeakDes);
        ivAdminStatus = findViewById(R.id.ivAdminStatus);
        lytChoosePlumber = findViewById(R.id.lytChoosePlumber);
        autoTvPlumber = findViewById(R.id.autoTvPlumber);
        btnAssignSubmit = findViewById(R.id.btnAssignSubmit);

        leakType = getIntent().getStringExtra("leakType");
        leakDes = getIntent().getStringExtra("leakDes");
        leakStatus = getIntent().getStringExtra("leakStatus");
        list = new ArrayList<>();

        tvLeakType.setText("Leak Type: " + leakType);
        tvLeakDes.setText("Leak Description: " + leakDes);

        if(leakStatus.equals("Pending"))
        {
            ivAdminStatus.setImageResource(R.mipmap.pending);
        }
        else if(leakStatus.equals("In progress"))
        {
            ivAdminStatus.setImageResource(R.mipmap.progress);
        }
        else
        {
            ivAdminStatus.setImageResource(R.mipmap.complete);
        }


        reference = FirebaseDatabase.getInstance().getReference("users");
        query = reference.orderByChild("role").equalTo("Plumber");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        String email = ds.child("email").getValue(String.class);
                        list.add(email);
                    }

                    ArrayAdapter<String> plumbersAdapter = new ArrayAdapter<>(AdminAssignPlumber.this,
                            android.R.layout.simple_dropdown_item_1line, list);
                    autoTvPlumber.setAdapter(plumbersAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(AdminAssignPlumber.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        autoTvPlumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                plumber = (String) parent.getItemAtPosition(position);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("leakages");
        assignPlumber = reference.orderByChild("leakDescription").equalTo(leakDes);

        assignPlumber.addListenerForSingleValueEvent(new ValueEventListener() {
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

                Toast.makeText(AdminAssignPlumber.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        btnAssignSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> map = new HashMap<>();
                map.put("leakPlumber", plumber);
                reference.child(statusId).updateChildren(map);
                Toast.makeText(AdminAssignPlumber.this, "Plumber assigned successfully updated.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}