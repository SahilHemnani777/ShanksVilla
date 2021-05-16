package com.example.shanksvilla.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.example.shanksvilla.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";

    private EditText fullName;
    private EditText email;
    private EditText password;

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth= FirebaseAuth.getInstance();

        fullName= findViewById(R.id.personName);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        Button register = findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        String mName = fullName.getText().toString();
        String mEmail = email.getText().toString();
        String mPassword= password.getText().toString();

        PasswordValidator passwordValidator = new PasswordValidator();
        boolean isValid= passwordValidator.validate(mPassword);
        
        if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword)) {
            Toast.makeText(this, "Please enter all the details.", Toast.LENGTH_SHORT).show();
        }else if(!isValid){
            Toast.makeText(this, "Be between 8 and 40 characters long\n" +
                    "Contain at least one digit.\n" +
                    "Contain at least one lower case character.\n" +
                    "Contain at least one upper case character.\n" +
                    "Contain at least on special character from [ @ # $ % ! . ].", Toast.LENGTH_LONG).show();
        }else{
            //Success case- When email and passwords are correctly filled
            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
                                    Log.d(TAG, "onComplete: "+user.getUid());
                                    User ModelUser = new User(mName,"null",user.getUid(),mEmail);
                                    myRef.child("Users").child(user.getUid()).setValue(ModelUser);
                                    Toast.makeText(CreateAccountActivity.this, "Registration Successful, please check email for verification", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(CreateAccountActivity.this,GoogleSignInActivity.class));
                                }else{
                                    //if the task is not successful
                                    Toast.makeText(CreateAccountActivity.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(CreateAccountActivity.this, GoogleSignInActivity.class));
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed please ",
                                    Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                        }
                    });


        }
    }
    public class PasswordValidator {

        private Pattern pattern;
        private Matcher matcher;

        private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

        public PasswordValidator() {
            pattern = Pattern.compile(PASSWORD_PATTERN);
        }

        public boolean validate(final String password) {

            matcher = pattern.matcher(password);
            return matcher.matches();

        }
    }
}