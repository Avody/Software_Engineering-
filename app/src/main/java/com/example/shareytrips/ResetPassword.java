package com.example.shareytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.*;
public class ResetPassword extends AppCompatActivity {
    EditText newPassword,confirmNewPassword;
    Button newPasswordBtn;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);

        user = FirebaseAuth.getInstance().getCurrentUser();

        newPasswordBtn = findViewById(R.id.newPasswordBtn);
        newPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newPassword.getText().toString().isEmpty()){
                    newPassword.setError("Required field");
                    return;
                }
                if(confirmNewPassword.getText().toString().isEmpty()){
                    confirmNewPassword.setError("Required field");
                    return;
                }
                if(!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){
                    confirmNewPassword.setError("Passwords do not much");
                    return;
                }

                user.updatePassword(newPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ResetPassword.this,"Password updated successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

            }
        });


    }
}