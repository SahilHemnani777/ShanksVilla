package com.example.shanksvilla.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;

public class BookingActivity2 extends AppCompatActivity {
    private static final String TAG = "BookingActivity2";
    private TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        Intent intent = getIntent();
        date= findViewById(R.id.datestartend);
//        Log.d(TAG, "onCreate: " +intent.getIntExtra("startDate", 0));
//        Log.d(TAG, "onCreate: "+intent.getExtras().getInt("endDate"));

    }
}