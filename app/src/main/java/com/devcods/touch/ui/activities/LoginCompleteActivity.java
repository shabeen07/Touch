package com.devcods.touch.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devcods.touch.R;
import com.devcods.touch.constants.ConstantValues;
import com.devcods.touch.databinding.ActivityLoginBinding;
import com.devcods.touch.databinding.ActivityLoginCompleteBinding;
import com.devcods.touch.models.User;
import com.devcods.touch.ui.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class LoginCompleteActivity extends AppCompatActivity {

    private ActivityLoginCompleteBinding binding;
    private SharedPreferences sharedPreferences;
    private int IMAGE_REQUEST_ID=101;
    private Uri imagePath;
    private ProgressDialog progressDialog;
    String userId,mobileNo,countryCode,country;
    private DatabaseReference userDatabaseReference;
    private StorageReference rootStorageReference;
    private static final String TAG = "LoginCompleteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivityLoginCompleteBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        progressDialog=new ProgressDialog(this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences( this );
        userId=sharedPreferences.getString( "userId","" );
        mobileNo=sharedPreferences.getString( "mobileNo","" );
        countryCode=sharedPreferences.getString( "countryCode","" );
        country=sharedPreferences.getString( "country","" );
        userDatabaseReference= FirebaseDatabase.getInstance().getReference(ConstantValues.CHAT_TABLE_USERS);
        rootStorageReference= FirebaseStorage.getInstance().getReference( ConstantValues.USER_PROFILE_IMAGE_STORAGE);
        binding.btnContinue.setOnClickListener( view -> {
            updateUser();
            startActivity( new Intent( LoginCompleteActivity.this, MainActivity.class ));
        });

        binding.ivUserImage.setOnClickListener( view -> {
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent,"Pick image"),IMAGE_REQUEST_ID);
        });
    }

    private void updateUser() {
        String userName=binding.edUserName.getText().toString();
        if (TextUtils.isEmpty( userName )){
            binding.edUserName.setError( "Enter your Name" );
            binding.edUserName.requestFocus();
        }else {
            addUserToFirebase(userId,userName,"",mobileNo,countryCode,country);
        }
    }

    private void addUserToFirebase(String userId,String userName,String userEmail,String userPhone,String countryCodeWithPlus,String country) {
        try {
            User user = new User( userId, userName, userEmail, userPhone, ConstantValues.CHAT_STATUS_ONLINE, countryCodeWithPlus, country );
            userDatabaseReference.child( userId ).setValue( user );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode== IMAGE_REQUEST_ID && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imagePath=data.getData();
//            sendImage();
            try {
                InputStream inputStream=getContentResolver().openInputStream(data.getData());
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                binding.ivUserImage.setImageBitmap( bitmap );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendImage() {
        try {
            progressDialog.setTitle("Uploading..");
            progressDialog.show();
            if (userDatabaseReference!=null){
                if (userId!=null){
                    StorageReference reference=rootStorageReference.child(userId+".jpg");
                    reference.putFile(imagePath)
                            .addOnSuccessListener(taskSnapshot -> {
                                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                Toast.makeText(LoginCompleteActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                                });
                                progressDialog.dismiss();
                              Toast.makeText(LoginCompleteActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> Log.d(TAG, "sendImage: "+e.getLocalizedMessage()))
                            .addOnProgressListener(snapshot -> {
                                double progress=((100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount());
                                progressDialog.setMessage(((int) progress)+"% Uploaded");
                            });
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}