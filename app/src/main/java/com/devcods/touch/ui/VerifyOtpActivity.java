package com.devcods.touch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.devcods.touch.constants.ConstantValues;
import com.devcods.touch.databinding.ActivityVerifyOtpBinding;
import com.devcods.touch.models.User;
import com.devcods.touch.ui.activities.LoginCompleteActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {

    private static final String TAG = "VerifyOtpActivity";
    ActivityVerifyOtpBinding binding;
    private FirebaseAuth mAuth;
    private String mVerificationId,resendToken;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mobileNo,country,countryCodeWithPlus;
    private SharedPreferences sharedPreferences;
    private DatabaseReference userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding= ActivityVerifyOtpBinding.inflate( getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences( this );
        Intent intent=getIntent();
        if (intent!=null){
            mobileNo=intent.getStringExtra( "mobileNo" );
            countryCodeWithPlus=intent.getStringExtra( "countryCodeWithPlus" );
            country=intent.getStringExtra( "country" );
            mVerificationId=intent.getStringExtra( "mVerificationId" );
            resendToken=intent.getStringExtra( "mResendToken" );
        }
        userDatabaseReference= FirebaseDatabase.getInstance().getReference(ConstantValues.CHAT_TABLE_USERS);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
//                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                mVerificationId = verificationId;
//                mResendToken = token;
            }
        };
        binding.edPinEntry.setOnPinEnteredListener( str -> {
            if (!TextUtils.isEmpty( str.toString())) {
                String enteredCode=str.toString();
                verifyPhoneNumberWithCode( mVerificationId,enteredCode );
            }
        });
    }
    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential( credential );
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        if (user!=null){
                            String userId=user.getUid();
                            checkIfExistUser(userId);
                        }
                        // Update UI
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                        }
                    }
                });
    }

    private void checkIfExistUser(String userId) {
        try {
             /*  to check the user is already exists or not ..
                used single value event for data exist , execute once .*/
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference( ConstantValues.CHAT_TABLE_USERS ).child( userId );
            databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        startActivity( new Intent( VerifyOtpActivity.this , LoginCompleteActivity.class));
                    }else {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putBoolean( "isLogin",true );
                        editor.putString( "userId",userId );
                        editor.putString( "mobileNo",mobileNo );
                        editor.putString( "countryCode",countryCodeWithPlus );
                        editor.putString( "country",country );
                        editor.apply();
                        startActivity( new Intent( VerifyOtpActivity.this , MainActivity.class));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}