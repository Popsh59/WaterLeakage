package com.example.waterleakage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminHomeScreen extends AppCompatActivity {
    TextView tvAdmin,tvAdd,tvLeaks;
    CardView cvAddPlumber,cvViewLeaks;
    ImageView ivWater,ivAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        tvAdmin = findViewById(R.id.tvAdmin);
        tvAdd = findViewById(R.id.tvAdd);
        tvLeaks = findViewById(R.id.tvLeaks);
        cvAddPlumber = findViewById(R.id.cvAddPlumber);
        cvViewLeaks = findViewById(R.id.cvViewLeaks);
        ivWater = findViewById(R.id.ivWater);
        ivAdd = findViewById(R.id.ivAdd);

        cvAddPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminHomeScreen.this,AdminAddPlumbers.class));

            }
        });

        cvViewLeaks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminHomeScreen.this,AdminViewLeakages.class));

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem logoutItem = menu.findItem(R.id.logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}