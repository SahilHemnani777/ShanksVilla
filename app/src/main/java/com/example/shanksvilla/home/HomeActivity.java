package com.example.shanksvilla.home;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.shanksvilla.R;
import com.example.shanksvilla.home.fragments.HomeFragment;
import com.example.shanksvilla.home.fragments.ProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "HomeActivity";

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

        fragmentManager=getSupportFragmentManager();

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
                        ProfileFragment profileFragment = new ProfileFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragmentHolder,profileFragment,null).commit();
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
}