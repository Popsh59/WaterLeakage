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

public class PlumberHomeScreen extends AppCompatActivity {

    TextView tvPlumberT,tvPLeaks;
    CardView cvPViewLeaks;
    ImageView ivPWater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_home_screen);

        tvPlumberT = findViewById(R.id.tvPlumberT);
        tvPLeaks = findViewById(R.id.tvPLeaks);
        cvPViewLeaks = findViewById(R.id.cvPViewLeaks);
        ivPWater = findViewById(R.id.ivPWater);

        cvPViewLeaks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlumberHomeScreen.this, PlumberViewLeakages.class));
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