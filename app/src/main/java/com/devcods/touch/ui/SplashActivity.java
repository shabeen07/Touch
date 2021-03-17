package com.devcods.touch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.devcods.touch.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        new Handler(  ).postDelayed( () -> {
            startActivity( new Intent( SplashActivity.this, LoginActivity.class ));
            finish();
        },2500);
    }
}