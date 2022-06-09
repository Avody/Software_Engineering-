package com.example.shareytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shareytrips.Fragments.ProfileFragment;
import com.example.shareytrips.Fragments.SearchFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.*;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private AlertDialog.Builder reset_alert;
    private LayoutInflater inflater;

    //for the bottom navigation bar
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Firebase connected", Toast.LENGTH_LONG).show();
        auth = FirebaseAuth.getInstance();


        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                        selectorFragment = null;
                        startActivity(new Intent(MainActivity.this , Home.class));
                        break;

                    case R.id.nav_search :
                        selectorFragment = new SearchFragment();
                        break;

                    case R.id.nav_add :
                        selectorFragment = null;
                        startActivity(new Intent(MainActivity.this , PostActivity.class));
                        break;

                    case R.id.nav_profile :
                        selectorFragment = new ProfileFragment();
                        break;
                }
                if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , selectorFragment).commit();
                }

                return  true;

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
    //profile menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.resetUserPassword){
            startActivity(new Intent(getApplicationContext(),ResetPassword.class));
        }
        if(item.getItemId() == R.id.updateEmailManu){
            View view1 = inflater.inflate(R.layout.reset_password,null);

            reset_alert.setTitle("Update email ?")
                    .setMessage("Enter new email address")
                    .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            //validate email address
                            EditText email = view1.findViewById(R.id.reset_email_pop);
                            if(email.getText().toString().isEmpty()){
                                email.setError("Required field");
                                return;
                            }
                            FirebaseUser user = auth.getCurrentUser();
                            user.updateEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this,"Email Updated",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).setNegativeButton("Cancel", null)
                    .setView(view1)
                    .create().show();
        }

        if(item.getItemId() == R.id.deleteAcc){
            reset_alert.setTitle("Delete Account Permantly ?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = auth.getCurrentUser();
                            user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(MainActivity.this,"Account Deleted",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).setNegativeButton("Cnacel",null)
                    .create().show();
        }
        if(item.getItemId() == R.id.LogoutBtn){
            reset_alert.setTitle("Logout?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }).setNegativeButton("Cnacel",null)
                    .create().show();
        }

        return super.onOptionsItemSelected(item);
    }
}


