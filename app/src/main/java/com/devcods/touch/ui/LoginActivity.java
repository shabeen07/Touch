package com.devcods.touch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.devcods.touch.R;
import com.devcods.touch.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivityLoginBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

        binding.btnGenerateOtp.setOnClickListener( view -> {
            startActivity( new Intent( LoginActivity.this,VerifyOtpActivity.class ));
        });

    }
}