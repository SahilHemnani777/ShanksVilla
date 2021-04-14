package com.example.shanksvilla.signin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.admin.AdminActivity;
import com.example.shanksvilla.home.HomeActivity;
import com.example.shanksvilla.R;
import com.example.shanksvilla.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//Master login Screen for the Login Of all the Activities

public class GoogleSignInActivity extends AppCompatActivity {
    private static final String TAG = "GoogleSignInActivity";

    private Button GoogleSignInButton;


    private Button btnLogin;
    private EditText mEmail;
    private EditText mPassword;


    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView createNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started");
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.googleSignInbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        createNewAccount = findViewById(R.id.createNewAccount);
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoogleSignInActivity.this,CreateAccountActivity.class));
            }
        });

        mEmail= findViewById(R.id.emailLogin);
        mPassword = findViewById(R.id.passwordLogin);
        btnLogin= findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= mEmail.getText().toString();
                String pass= mPassword.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){

                    if(email.equals("7777777") && pass.equals("7777777")){
                        startActivity(new Intent(GoogleSignInActivity.this, AdminActivity.class));
                        finish();
                    }
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user.isEmailVerified()){
                                    //If the user has verified his email address then he/she can login otherwise he can not
                                    startActivity(new Intent(GoogleSignInActivity.this,HomeActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(GoogleSignInActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(GoogleSignInActivity.this, "Please enter Valid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(GoogleSignInActivity.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
//        Log.d(TAG, "onCreate: finished");

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            addUserToDataBase();
                            startActivity(new Intent(GoogleSignInActivity.this, HomeActivity.class));
                            Toast.makeText(GoogleSignInActivity.this, "Login as: "+mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        Log.d(TAG, "onStart: started");
//        updateUI(currentUser);
        if (currentUser!=null && currentUser.isEmailVerified()){
            Log.d(TAG, "onStart: current user is not null: "+ currentUser.getEmail());
            startActivity(new Intent(GoogleSignInActivity.this, HomeActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME));
            this.finish();
        }
        Log.d(TAG, "onStart: finished");
    }

    public void addUserToDataBase(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            //Taking values from the google Account
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            //Taking value of Uid from the Firebase Auth
            String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
            //using the model class to assign the values
            User user = new User(personName,personPhoto.toString(),id,personEmail);
            //setting up the database
            myRef.child("Users").child(user.getId()).setValue(user);
//            Log.d(TAG, "addUserToDataBase: done");
        }
    }
}