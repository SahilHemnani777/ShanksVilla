package com.example.shanksvilla.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class BookingActivity2 extends AppCompatActivity {
    private static final String TAG = "BookingActivity2";
    private TextView date;
    private String startDate;
    private String endDate;
    private int people;
    private ArrayList<String> emptyrooms = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        for (int i = 0; i < 8; i++) {
            emptyrooms.add("");
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        date = findViewById(R.id.datestartend);
        startDate = String.valueOf(reverse(bundle.getInt("startDate")));
        endDate = String.valueOf(reverse(bundle.getInt("endDate")));
        people = bundle.getInt("count");

        myRef.child("database").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                search(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Log.d(TAG, "onCreate: "+ startDate +endDate +String.valueOf(people));
//        Log.d(TAG, "onCreate: "+ reverse(bundle.getInt("startDate")));
//        Log.d(TAG, "onCreate: "+ reverse(bundle.getInt("endDate")));
//        Log.d(TAG, "onCreate: "+ bundle.getInt("count"));


    }

    private int reverse(int date) {  //20210318

        String x = String.valueOf(date);
        String year = x.substring(0, 4);
        String month = x.substring(4, 6);
        String day = x.substring(6);

        String newDate = day + month + year;
        return Integer.valueOf(newDate);

    }

    private boolean search(DataSnapshot snapshot) {
        Iterator<DataSnapshot> items = snapshot.getChildren().iterator();
        //now we have all the entries in the items ka iterator
//                Toast.makeText(BookingActivity2.this, "items" + snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

        while (items.hasNext()) {
            DataSnapshot date = items.next();
//                    Log.d(TAG, "onDataChange: "+ date.getKey());
            if (date.getKey().toString().equals(startDate)) {
//                        Toast.makeText(BookingActivity2.this, "found: " + date.getKey().toString(), Toast.LENGTH_SHORT).show();
                String h1r1 = date.child("H1R1").getValue().toString();
                String h1r2 = date.child("H1R2").getValue().toString();
                String h1r3 = date.child("H1R3").getValue().toString();
                String h1r4 = date.child("H1R4").getValue().toString();
                String h2r1 = date.child("H2R1").getValue().toString();
                String h2r2 = date.child("H2R2").getValue().toString();
                String h2r3 = date.child("H2R3").getValue().toString();
                String h2r4 = date.child("H2R4").getValue().toString();
                ArrayList<String> rooms = new ArrayList<>();

                rooms.add(h1r1);
                rooms.add(h1r2);
                rooms.add(h1r3);
                rooms.add(h1r4);
                rooms.add(h2r1);
                rooms.add(h2r2);
                rooms.add(h2r3);
                rooms.add(h2r4);
                if (rooms == emptyrooms) {
                    continue;
                }
            }if(date.getKey().toString().equals(endDate)){

            }


        }
        return true;
    }
}