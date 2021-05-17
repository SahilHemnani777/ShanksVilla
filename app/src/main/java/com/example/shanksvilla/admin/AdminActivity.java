package com.example.shanksvilla.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.example.shanksvilla.signin.GoogleSignInActivity;

public class AdminActivity extends AppCompatActivity {
    Button bookingbutton, aboutUsButton;
    TextView shiftToNormalUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bookingbutton= findViewById(R.id.admin_boking_button);
        aboutUsButton= findViewById(R.id.admin_about_us_button);
        shiftToNormalUser= findViewById(R.id.admin_shift_buton);

        shiftToNormalUser.setOnClickListener(v -> {
            //if the admin want to switch to normal user
            startActivity(new Intent(AdminActivity.this, GoogleSignInActivity.class));
            finish();
        });

        bookingbutton.setOnClickListener(v->{
            startActivity(new Intent(AdminActivity.this, AdminBooking.class));
        });

        aboutUsButton.setOnClickListener(v->{
            startActivity(new Intent(AdminActivity.this, AdminAboutUs.class));
        });

    }


}