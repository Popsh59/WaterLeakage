package com.example.waterleakage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminViewLeakages extends AppCompatActivity implements LeakageAdapter.ItemClicked {
    TextView tvAdminLeaks;
    RecyclerView rvList;
    LeakageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Leakage> list;
    DatabaseReference dRef;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_leakages);

        tvAdminLeaks = findViewById(R.id.tvAdminLeaks);
        rvList = findViewById(R.id.recyclerView);

        rvList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        list= new ArrayList<>();

        dRef = FirebaseDatabase.getInstance().getReference("leakages");
        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Leakage leakage = ds.getValue(Leakage.class);
                        list.add(leakage);
                    }
                    adapter = new LeakageAdapter(AdminViewLeakages.this, list);
                    rvList.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(AdminViewLeakages.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemClicked(int index) {
       intent = new Intent(AdminViewLeakages.this,AdminAssignPlumber.class);
       intent.putExtra("leakType",list.get(index).getLeakType());
       intent.putExtra("leakDes",list.get(index).getLeakDescription());
       intent.putExtra("leakStatus",list.get(index).getLeakStatus());
       startActivity(intent);
    }
}