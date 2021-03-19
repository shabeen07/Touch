package com.devcods.touch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.devcods.touch.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivityLoginBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();


        binding.btnGenerateOtp.setOnClickListener( view -> {
            String mobileNo=binding.edMobileNo.getText().toString();
            if (TextUtils.isEmpty( mobileNo )){
                Snackbar.make( binding.getRoot(),"Enter Mobile Number", BaseTransientBottomBar.LENGTH_SHORT );
            }else {
                sendOtp(mobileNo);
            }
        });

    }

    private void sendOtp(String mobileNo) {
        try {
            String countryCode=binding.ccp.getSelectedCountryCodeWithPlus();
            mobileNo=countryCode+mobileNo;
            String finalMobileNo = mobileNo;

            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(mobileNo)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    mobileNo,
                    30,
                    TimeUnit.SECONDS,
                    LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Snackbar.make( binding.getRoot() , Objects.requireNonNull( e.getMessage() ),BaseTransientBottomBar.LENGTH_SHORT);
                        }

                        @Override
                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            startActivity( new Intent( LoginActivity.this,VerifyOtpActivity.class )
                                    .putExtra( "verificationId",verificationId )
                                    .putExtra( "mobileNo", finalMobileNo ));

                        }
                    }
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}