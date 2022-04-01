package com.example.shareytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.*;

public class Login extends AppCompatActivity {

    private TextView createAccountBtn,forgot_password;
    private EditText mEmail,mPassword;
    private Button mLoginBtn;
    private FirebaseAuth firebaseAuth;
    private AlertDialog.Builder reset_alert;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();
        createAccountBtn = findViewById(R.id.createText);
        createAccountBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        }));

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.loginBtn);
        forgot_password = findViewById(R.id.forgotPassword);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start alertdialog
                View view1 = inflater.inflate(R.layout.reset_password,null);

                reset_alert.setTitle("Forgot Password ?")
                        .setMessage("Enter your email to get password reset link.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                //validate email address
                                EditText email = view1.findViewById(R.id.reset_email_pop);
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Required field");
                                    return;
                                }
                                //send the reset link
                                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Login.this,"Reset Email Sent",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel", null)
                        .setView(view1)
                        .create().show();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //extract and validate
                if(mEmail.getText().toString().isEmpty()){
                    mEmail.setError("Email is Missing");
                    return;
                }
                if(mPassword.getText().toString().isEmpty()){
                    mPassword.setError("Password is missing");
                    return;
                }
                //data is valid
                //Login user
                firebaseAuth.signInWithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /*If the users had already loged in the app before he will
    * automatically log into the main page  again without confiramton*/
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity((new Intent(getApplicationContext(),MainActivity.class)));
            finish();
        }
    }

}