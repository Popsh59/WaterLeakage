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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlumberViewLeakages extends AppCompatActivity implements LeakageAdapter.ItemClicked {
    TextView tvPlumberLeaks;
    RecyclerView rvList;
    LeakageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Leakage> list;
    DatabaseReference dRef;
    Query q;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_view_leakages);

        rvList = findViewById(R.id.rvList);
        tvPlumberLeaks = findViewById(R.id.tvPlumberLeaks);

        rvList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        list= new ArrayList<>();

        dRef = FirebaseDatabase.getInstance().getReference("leakages");
        q = dRef.orderByChild("leakPlumber").equalTo(ApplicationClass.userEmail);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    Leakage leakage = ds.getValue(Leakage.class);
                    list.add(leakage);
                }
                adapter = new LeakageAdapter(PlumberViewLeakages.this,list);
                rvList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(PlumberViewLeakages.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onItemClicked(int index) {

        intent = new Intent(PlumberViewLeakages.this,PlumberLeakageStatus.class);
        intent.putExtra("LType",list.get(index).getLeakType());
        intent.putExtra("LDescrip",list.get(index).getLeakDescription());
        intent.putExtra("LStatus",list.get(index).getLeakStatus());
        intent.putExtra("LLat",list.get(index).getLat());
        intent.putExtra("LLon",list.get(index).getLon());
        startActivity(intent);

    }
}