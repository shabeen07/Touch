package com.devcods.touch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.devcods.touch.databinding.ActivityVerifyOtpBinding;

public class VerifyOtpActivity extends AppCompatActivity {

    ActivityVerifyOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding= ActivityVerifyOtpBinding.inflate( getLayoutInflater());
        setContentView(binding.getRoot());
        binding.edPinEntry.setOnPinEnteredListener( str -> {
            if (!TextUtils.isEmpty( str.toString()) && str.toString().equalsIgnoreCase( "123456" )) {
                startActivity( new Intent( VerifyOtpActivity.this,MainActivity.class));
            }
        });
    }
}