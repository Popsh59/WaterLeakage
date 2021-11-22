package com.example.waterleakage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView ivDrop,ivHand;
    TextView tvSlogan;
    Animation topA,bottomA;

    private static int  SPLASH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ivDrop = findViewById(R.id.ivDrop);
        ivHand = findViewById(R.id.ivHand);
        tvSlogan = findViewById(R.id.tvSlogan);

        topA = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomA = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        ivDrop.setAnimation(topA);
        ivHand.setAnimation(bottomA);
        tvSlogan.setAnimation(bottomA);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Login.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(ivDrop,"logo_image");
                pairs[1] = new Pair<View,String>(ivHand,"logo_text");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,activityOptions.toBundle());
            }
        },SPLASH);

    }
}