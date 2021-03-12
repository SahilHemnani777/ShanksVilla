package com.example.shanksvilla.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
    private int days;


    private ArrayList<String> is_available = new ArrayList<>(days);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        date = findViewById(R.id.datestartend);
        startDate = String.valueOf(reverse(bundle.getInt("startDate")));
        // start date and end date in String format--> ddMMyyyy
        endDate = String.valueOf(reverse(bundle.getInt("endDate")));
        people = bundle.getInt("count");
        days= bundle.getInt("days");



        myRef.child("database").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (search(snapshot)){
                    Toast.makeText(BookingActivity2.this, "Found", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BookingActivity2.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


//        Log.d(TAG, "onCreate: "+ startDate +endDate +String.valueOf(people));
//        Log.d(TAG, "onCreate: "+ reverse(bundle.getInt("startDate")));
//        Log.d(TAG, "onCreate: "+ reverse(bundle.getInt("endDate")));
//        Log.d(TAG, "onCreate: "+ bundle.getInt("count"));
//        Log.d(TAG, "onCreate: "+ bundle.getInt("days"));


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
            if (date.getKey().toString().equals(startDate)) {
                if (Integer.valueOf(date.child("vacancies").getValue().toString())>=people && items.hasNext()){
                    while(!items.next().getKey().toString().equals(endDate)){
                        DataSnapshot x= items.next();
                        if (Integer.valueOf(x.child("vacancies").getValue().toString())>= people){
                                continue;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

}