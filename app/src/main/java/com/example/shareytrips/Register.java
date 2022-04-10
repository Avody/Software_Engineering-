package com.example.shareytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Register extends AppCompatActivity {
    private EditText mFullName,mEmail,mPassword,mConfPassword;
    private Button mRegisterBtn;
    private TextView mLoginBtn;

    //for interests checkbox items
    private Button interestsBtn;
    private TextView interestsSelected;
    private String[] listItems;
    private boolean[] checkedItems;
    private ArrayList<Integer> UserInterests = new ArrayList<>();

    private FirebaseAuth fAuth;
    private DatabaseReference mRootRef;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mConfPassword = findViewById(R.id.confPass);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mLoginBtn   = findViewById(R.id.createText);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        /**********************************************************************************************************************/
        //for interests checkbox items
        interestsBtn = (Button) findViewById(R.id.interestsBtn);
        interestsSelected = (TextView) findViewById(R.id.interestsText);
        listItems = getResources().getStringArray(R.array.interests);
        checkedItems = new boolean[listItems.length];

        interestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Register.this);
                mBuilder.setTitle("Travelling interests");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                       if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                        if(isChecked){
                            UserInterests.add(position);
                        }else{
                            UserInterests.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < UserInterests.size(); i++) {
                            item = item + listItems[UserInterests.get(i)];
                            if (i != UserInterests.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        interestsSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            UserInterests.clear();
                            interestsSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        /*Interests checkbox end*/
        /**********************************************************************************************************************/

        //change to login page
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        //register
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String fullname = mFullName.getText().toString();
                 String email = mEmail.getText().toString();
                 String password    = mPassword.getText().toString();
                 String confPassword    = mConfPassword.getText().toString();


                if(fullname.isEmpty()){
                    mFullName.setError("Full name is Required.");
                    return;
                }

                if(email.isEmpty()){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(password.isEmpty()){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                if(!password.equals(confPassword)){
                    mPassword.setError("Passwords dont macht");
                    return;
                }

                pd.setMessage("Please wait");
                pd.show();
                //data is validated
                //register the user using firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //create users data tree
                        User user = new User(mFullName.getText().toString(),mEmail.getText().toString().trim(),mPassword.getText().toString().trim(),interestsSelected.getText().toString(),fAuth.getCurrentUser().getUid());
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("fullname",user.getFullname());
                        map.put("email",user.getEmail());
                        map.put("id",user.getId());
                        map.put("interests",user.getInterests());
                        mRootRef.child("Users").child(fAuth.getCurrentUser().getUid()).setValue(map);
                        //send user to main page
                        pd.dismiss();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}