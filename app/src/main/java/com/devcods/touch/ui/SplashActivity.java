package com.devcods.touch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.devcods.touch.R;
import com.devcods.touch.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivitySplashBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );
        boolean isLogin= sharedPreferences.getBoolean( "isLogin",false);
        AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES);
        if (isLogin){
            new Handler(Looper.getMainLooper()).postDelayed( () -> {
                startActivity( new Intent( SplashActivity.this, MainActivity.class ));
                finish();
            },1800);
        }else {
            new Handler(Looper.getMainLooper()).postDelayed( () -> {
                startActivity( new Intent( SplashActivity.this, LoginActivity.class ));
                finish();
            },2400);
        }
    }
}