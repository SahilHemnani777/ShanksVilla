package com.example.shanksvilla.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.shanksvilla.R;
import com.example.shanksvilla.home.booking.BookingActivity;
import com.example.shanksvilla.home.fragments.DescriptionFragment;
import com.example.shanksvilla.home.fragments.HomeFragment;
import com.example.shanksvilla.home.fragments.ProfileFragment;
import com.example.shanksvilla.signin.GoogleSignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "HomeActivity";
    private Snackbar snackBar;

    private Button buttonLogout;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
//        Function for the database setup(don't use afterwards...)
//        database_init();


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
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.itemHome:
                    HomeFragment homeFragment1 = new HomeFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragmentHolder, homeFragment1,null).commit();
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
                    if (mAuth.getCurrentUser()!=null){
                        startActivity(new Intent(HomeActivity.this, BookingActivity.class));
                        break;
                    }else{
                        snackBar.show();
                    }
                    break;
            }
            return true;
        });
        Log.d(TAG, "onCreate: finished");
    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    FirebaseAuth.getInstance().signOut();
                    finish();
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

    private void database_init(){
             String dates = "01-01-2021\n" +
            "02-01-2021\n" +
            "03-01-2021\n" +
            "04-01-2021\n" +
            "05-01-2021\n" +
            "06-01-2021\n" +
            "07-01-2021\n" +
            "08-01-2021\n" +
            "09-01-2021\n" +
            "10-01-2021\n" +
            "11-01-2021\n" +
            "12-01-2021\n" +
            "13-01-2021\n" +
            "14-01-2021\n" +
            "15-01-2021\n" +
            "16-01-2021\n" +
            "17-01-2021\n" +
            "18-01-2021\n" +
            "19-01-2021\n" +
            "20-01-2021\n" +
            "21-01-2021\n" +
            "22-01-2021\n" +
            "23-01-2021\n" +
            "24-01-2021\n" +
            "25-01-2021\n" +
            "26-01-2021\n" +
            "27-01-2021\n" +
            "28-01-2021\n" +
            "29-01-2021\n" +
            "30-01-2021\n" +
            "31-01-2021\n" +
            "01-02-2021\n" +
            "02-02-2021\n" +
            "03-02-2021\n" +
            "04-02-2021\n" +
            "05-02-2021\n" +
            "06-02-2021\n" +
            "07-02-2021\n" +
            "08-02-2021\n" +
            "09-02-2021\n" +
            "10-02-2021\n" +
            "11-02-2021\n" +
            "12-02-2021\n" +
            "13-02-2021\n" +
            "14-02-2021\n" +
            "15-02-2021\n" +
            "16-02-2021\n" +
            "17-02-2021\n" +
            "18-02-2021\n" +
            "19-02-2021\n" +
            "20-02-2021\n" +
            "21-02-2021\n" +
            "22-02-2021\n" +
            "23-02-2021\n" +
            "24-02-2021\n" +
            "25-02-2021\n" +
            "26-02-2021\n" +
            "27-02-2021\n" +
            "28-02-2021\n" +
            "01-03-2021\n" +
            "02-03-2021\n" +
            "03-03-2021\n" +
            "04-03-2021\n" +
            "05-03-2021\n" +
            "06-03-2021\n" +
            "07-03-2021\n" +
            "08-03-2021\n" +
            "09-03-2021\n" +
            "10-03-2021\n" +
            "11-03-2021\n" +
            "12-03-2021\n" +
            "13-03-2021\n" +
            "14-03-2021\n" +
            "15-03-2021\n" +
            "16-03-2021\n" +
            "17-03-2021\n" +
            "18-03-2021\n" +
            "19-03-2021\n" +
            "20-03-2021\n" +
            "21-03-2021\n" +
            "22-03-2021\n" +
            "23-03-2021\n" +
            "24-03-2021\n" +
            "25-03-2021\n" +
            "26-03-2021\n" +
            "27-03-2021\n" +
            "28-03-2021\n" +
            "29-03-2021\n" +
            "30-03-2021\n" +
            "31-03-2021\n" +
            "01-04-2021\n" +
            "02-04-2021\n" +
            "03-04-2021\n" +
            "04-04-2021\n" +
            "05-04-2021\n" +
            "06-04-2021\n" +
            "07-04-2021\n" +
            "08-04-2021\n" +
            "09-04-2021\n" +
            "10-04-2021\n" +
            "11-04-2021\n" +
            "12-04-2021\n" +
            "13-04-2021\n" +
            "14-04-2021\n" +
            "15-04-2021\n" +
            "16-04-2021\n" +
            "17-04-2021\n" +
            "18-04-2021\n" +
            "19-04-2021\n" +
            "20-04-2021\n" +
            "21-04-2021\n" +
            "22-04-2021\n" +
            "23-04-2021\n" +
            "24-04-2021\n" +
            "25-04-2021\n" +
            "26-04-2021\n" +
            "27-04-2021\n" +
            "28-04-2021\n" +
            "29-04-2021\n" +
            "30-04-2021\n" +
            "01-05-2021\n" +
            "02-05-2021\n" +
            "03-05-2021\n" +
            "04-05-2021\n" +
            "05-05-2021\n" +
            "06-05-2021\n" +
            "07-05-2021\n" +
            "08-05-2021\n" +
            "09-05-2021\n" +
            "10-05-2021\n" +
            "11-05-2021\n" +
            "12-05-2021\n" +
            "13-05-2021\n" +
            "14-05-2021\n" +
            "15-05-2021\n" +
            "16-05-2021\n" +
            "17-05-2021\n" +
            "18-05-2021\n" +
            "19-05-2021\n" +
            "20-05-2021\n" +
            "21-05-2021\n" +
            "22-05-2021\n" +
            "23-05-2021\n" +
            "24-05-2021\n" +
            "25-05-2021\n" +
            "26-05-2021\n" +
            "27-05-2021\n" +
            "28-05-2021\n" +
            "29-05-2021\n" +
            "30-05-2021\n" +
            "31-05-2021\n" +
            "01-06-2021\n" +
            "02-06-2021\n" +
            "03-06-2021\n" +
            "04-06-2021\n" +
            "05-06-2021\n" +
            "06-06-2021\n" +
            "07-06-2021\n" +
            "08-06-2021\n" +
            "09-06-2021\n" +
            "10-06-2021\n" +
            "11-06-2021\n" +
            "12-06-2021\n" +
            "13-06-2021\n" +
            "14-06-2021\n" +
            "15-06-2021\n" +
            "16-06-2021\n" +
            "17-06-2021\n" +
            "18-06-2021\n" +
            "19-06-2021\n" +
            "20-06-2021\n" +
            "21-06-2021\n" +
            "22-06-2021\n" +
            "23-06-2021\n" +
            "24-06-2021\n" +
            "25-06-2021\n" +
            "26-06-2021\n" +
            "27-06-2021\n" +
            "28-06-2021\n" +
            "29-06-2021\n" +
            "30-06-2021\n" +
            "01-07-2021\n" +
            "02-07-2021\n" +
            "03-07-2021\n" +
            "04-07-2021\n" +
            "05-07-2021\n" +
            "06-07-2021\n" +
            "07-07-2021\n" +
            "08-07-2021\n" +
            "09-07-2021\n" +
            "10-07-2021\n" +
            "11-07-2021\n" +
            "12-07-2021\n" +
            "13-07-2021\n" +
            "14-07-2021\n" +
            "15-07-2021\n" +
            "16-07-2021\n" +
            "17-07-2021\n" +
            "18-07-2021\n" +
            "19-07-2021\n" +
            "20-07-2021\n" +
            "21-07-2021\n" +
            "22-07-2021\n" +
            "23-07-2021\n" +
            "24-07-2021\n" +
            "25-07-2021\n" +
            "26-07-2021\n" +
            "27-07-2021\n" +
            "28-07-2021\n" +
            "29-07-2021\n" +
            "30-07-2021\n" +
            "31-07-2021\n" +
            "01-08-2021\n" +
            "02-08-2021\n" +
            "03-08-2021\n" +
            "04-08-2021\n" +
            "05-08-2021\n" +
            "06-08-2021\n" +
            "07-08-2021\n" +
            "08-08-2021\n" +
            "09-08-2021\n" +
            "10-08-2021\n" +
            "11-08-2021\n" +
            "12-08-2021\n" +
            "13-08-2021\n" +
            "14-08-2021\n" +
            "15-08-2021\n" +
            "16-08-2021\n" +
            "17-08-2021\n" +
            "18-08-2021\n" +
            "19-08-2021\n" +
            "20-08-2021\n" +
            "21-08-2021\n" +
            "22-08-2021\n" +
            "23-08-2021\n" +
            "24-08-2021\n" +
            "25-08-2021\n" +
            "26-08-2021\n" +
            "27-08-2021\n" +
            "28-08-2021\n" +
            "29-08-2021\n" +
            "30-08-2021\n" +
            "31-08-2021\n" +
            "01-09-2021\n" +
            "02-09-2021\n" +
            "03-09-2021\n" +
            "04-09-2021\n" +
            "05-09-2021\n" +
            "06-09-2021\n" +
            "07-09-2021\n" +
            "08-09-2021\n" +
            "09-09-2021\n" +
            "10-09-2021\n" +
            "11-09-2021\n" +
            "12-09-2021\n" +
            "13-09-2021\n" +
            "14-09-2021\n" +
            "15-09-2021\n" +
            "16-09-2021\n" +
            "17-09-2021\n" +
            "18-09-2021\n" +
            "19-09-2021\n" +
            "20-09-2021\n" +
            "21-09-2021\n" +
            "22-09-2021\n" +
            "23-09-2021\n" +
            "24-09-2021\n" +
            "25-09-2021\n" +
            "26-09-2021\n" +
            "27-09-2021\n" +
            "28-09-2021\n" +
            "29-09-2021\n" +
            "30-09-2021\n" +
            "01-10-2021\n" +
            "02-10-2021\n" +
            "03-10-2021\n" +
            "04-10-2021\n" +
            "05-10-2021\n" +
            "06-10-2021\n" +
            "07-10-2021\n" +
            "08-10-2021\n" +
            "09-10-2021\n" +
            "10-10-2021\n" +
            "11-10-2021\n" +
            "12-10-2021\n" +
            "13-10-2021\n" +
            "14-10-2021\n" +
            "15-10-2021\n" +
            "16-10-2021\n" +
            "17-10-2021\n" +
            "18-10-2021\n" +
            "19-10-2021\n" +
            "20-10-2021\n" +
            "21-10-2021\n" +
            "22-10-2021\n" +
            "23-10-2021\n" +
            "24-10-2021\n" +
            "25-10-2021\n" +
            "26-10-2021\n" +
            "27-10-2021\n" +
            "28-10-2021\n" +
            "29-10-2021\n" +
            "30-10-2021\n" +
            "31-10-2021\n" +
            "01-11-2021\n" +
            "02-11-2021\n" +
            "03-11-2021\n" +
            "04-11-2021\n" +
            "05-11-2021\n" +
            "06-11-2021\n" +
            "07-11-2021\n" +
            "08-11-2021\n" +
            "09-11-2021\n" +
            "10-11-2021\n" +
            "11-11-2021\n" +
            "12-11-2021\n" +
            "13-11-2021\n" +
            "14-11-2021\n" +
            "15-11-2021\n" +
            "16-11-2021\n" +
            "17-11-2021\n" +
            "18-11-2021\n" +
            "19-11-2021\n" +
            "20-11-2021\n" +
            "21-11-2021\n" +
            "22-11-2021\n" +
            "23-11-2021\n" +
            "24-11-2021\n" +
            "25-11-2021\n" +
            "26-11-2021\n" +
            "27-11-2021\n" +
            "28-11-2021\n" +
            "29-11-2021\n" +
            "30-11-2021\n" +
            "01-12-2021\n" +
            "02-12-2021\n" +
            "03-12-2021\n" +
            "04-12-2021\n" +
            "05-12-2021\n" +
            "06-12-2021\n" +
            "07-12-2021\n" +
            "08-12-2021\n" +
            "09-12-2021\n" +
            "10-12-2021\n" +
            "11-12-2021\n" +
            "12-12-2021\n" +
            "13-12-2021\n" +
            "14-12-2021\n" +
            "15-12-2021\n" +
            "16-12-2021\n" +
            "17-12-2021\n" +
            "18-12-2021\n" +
            "19-12-2021\n" +
            "20-12-2021\n" +
            "21-12-2021\n" +
            "22-12-2021\n" +
            "23-12-2021\n" +
            "24-12-2021\n" +
            "25-12-2021\n" +
            "26-12-2021\n" +
            "27-12-2021\n" +
            "28-12-2021\n" +
            "29-12-2021\n" +
            "30-12-2021\n" +
            "31-12-2021\n";


        DecimalFormat formatter = new DecimalFormat("00");

        ArrayList<String> cal = new ArrayList<>();

        String strNew = dates.replace("-", "");

        String strNewNew = strNew.replace("\n", "");
//        Log.d(TAG, "onCreate: "+ strNewNew);

        for (int i =0; i<strNew.length() ; i=i+8){
            if(i+8<=strNewNew.length()){
                String temp = strNewNew.substring(i, i+8);
                cal.add(temp);
            }
        }

        Log.d(TAG, "onCreate: "+ cal);

        for(int i =0; i<cal.size(); i++){
            myRef.child("database1").child(cal.get(i)).child("vacancies").setValue(20);
//            myRef.child("database").child(cal.get(i)).child("allotted").setValue(i);

        }
        for(int i =0; i<cal.size(); i++){
            myRef.child("database2").child(cal.get(i)).child("vacancies").setValue(20);
//            myRef.child("database").child(cal.get(i)).child("allotted").setValue(i);

        }
    }
}
