package com.example.shanksvilla.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BookingActivity2 extends AppCompatActivity {
    private static final String TAG = "BookingActivity2";
    private TextView date;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        date= findViewById(R.id.datestartend);


        Log.d(TAG, "onCreate: "+ bundle.getInt("startDate"));
        Log.d(TAG, "onCreate: "+ bundle.getInt("endDate"));
        Log.d(TAG, "onCreate: "+ bundle.getInt("count"));

    }
}