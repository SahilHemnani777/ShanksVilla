package com.example.shanksvilla.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.shanksvilla.R;
import com.example.shanksvilla.home.fragments.DescriptionFragment;
import com.example.shanksvilla.home.fragments.HomeFragment;
import com.example.shanksvilla.home.fragments.ProfileFragment;
import com.example.shanksvilla.signin.GoogleSignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "HomeActivity";
    private Snackbar snackBar;

    private Button buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth= FirebaseAuth.getInstance();

        //setting up the snack bar for the login
        snackBar= Snackbar.make(findViewById(R.id.home_layout), "Please login for the action", BaseTransientBottomBar.LENGTH_SHORT);
        snackBar.setAction("Login", new LoginListener());



        fragmentManager=getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentHolder,homeFragment,null).commit();

        bottomNavigationView = findViewById(R.id.bnb);
        bottomNavigationView.setSelectedItemId(R.id.itemHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.itemHome:
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragmentHolder,homeFragment,null).commit();
                        break;

                    case R.id.itemProfile:
                        if (mAuth.getCurrentUser()!=null){
                            ProfileFragment profileFragment = new ProfileFragment();
                            fragmentManager.beginTransaction().replace(R.id.fragmentHolder,profileFragment,null).commit();
                            break;
                        }else{
                            snackBar.show();
                        }
                        break;



                    case R.id.itemAboutUs:
                        DescriptionFragment descriptionFragment = new DescriptionFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragmentHolder,descriptionFragment,null).commit();
                        break;

                    case R.id.itemBook:

//                        loading the BookingActivity because it'll be the main Activity
                        startActivity(new Intent(HomeActivity.this, BookingActivity.class));
//                        BookingFragment bookingFragment = new BookingFragment();
//                        fragmentManager.beginTransaction().replace(R.id.fragmentHolder,bookingFragment,null).commit();
                        break;
                }
                return true;
            }
        });
        Log.d(TAG, "onCreate: finished");
    }


    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });
    }

    //class for the listener on the snackBar
    class LoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent= new Intent(HomeActivity.this, GoogleSignInActivity.class);
            startActivity(intent);
        }
    }
}
