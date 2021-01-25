package com.example.shanksvilla;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;


    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;

    private Button GoogleSignInButton;
    private ImageView FacebookSignInButton;
    private ImageView TwitterSignInButton;
    private ImageView PhoneSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInButton = findViewById(R.id.googleSignInbtn);
    }
}