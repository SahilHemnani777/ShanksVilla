package com.example.shanksvilla.home.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.example.shanksvilla.model.dbRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DetailsActivity extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private EditText name, age, phone, peoplesDetails;
    private Button btnDone;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = myAuth.getCurrentUser();
        name = findViewById(R.id.name_details);
        age = findViewById(R.id.age_details);
        phone = findViewById(R.id.contact_details);
        peoplesDetails = findViewById(R.id.peoples_detail);
        btnDone = findViewById(R.id.done_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Integer number = bundle.getInt("people");
        ArrayList<Integer> list = (ArrayList<Integer>) bundle.get("dates");
        Log.d(TAG, "onCreate: " + list + "aa gayi");

        btnDone.setOnClickListener(v -> {
            assert currentUser != null;
            String UID = currentUser.getUid();
            String mName = name.getText().toString();
            String mAge = age.getText().toString();
            String mPhone = phone.getText().toString();
            String mpeoplesDetails = peoplesDetails.getText().toString();

            if (!UID.equals("") && !mName.equals("") && !mAge.equals("") && !mPhone.equals("") && !mpeoplesDetails.equals("")) {
                dbRef user = new dbRef(mName, Integer.parseInt(mAge), 0, mPhone, UID);


                if (bundle.getString("location").equals("kihim")) {

                    for (int i = 0; i < list.size(); i++) {
                        AtomicReference<Integer> old = new AtomicReference<>(999);
                        Log.d(TAG, "onClick: " + list.get(i));
                        int finalI = i;
                        myRef.child("database1").child(String.format("%08d", list.get(i))).child("vacancies").get().addOnCompleteListener(task -> {
                            //finding no. of vacancies
                            old.set(Integer.valueOf(task.getResult().getValue().toString()));
                            myRef.child("database1").child(String.format("%08d", list.get(finalI))).get().addOnCompleteListener(task1 -> {
                                HashMap<String, String> map = (HashMap<String, String>) task1.getResult().getValue();
                                //finding no. of booking
                                Integer no_of_bookings = map.size();
                                myRef.child("database1").child(String.format("%08d", list.get(finalI))).child("booking"+no_of_bookings).setValue(user);
                                myRef.child("database1").child(String.format("%08d", list.get(finalI))).child("booking"+no_of_bookings).child("no_of_members").setValue(number);
                                myRef.child("database1").child(String.format("%08d", list.get(finalI))).child("booking"+no_of_bookings).child("details").setValue(mpeoplesDetails);
                                myRef.child("database1").child(String.format("%08d", list.get(finalI))).child("vacancies").setValue(old.get() - number);
                                Toast.makeText(this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                            });

                        });
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        AtomicReference<Integer> old = new AtomicReference<>(999);
                        Log.d(TAG, "onClick: " + list.get(i));
                        int finalI = i;
                        myRef.child("database2").child(String.format("%08d", list.get(i))).child("vacancies").get().addOnCompleteListener(task -> {
                            //finding no. of vacancies
                            old.set(Integer.valueOf(task.getResult().getValue().toString()));
                            myRef.child("database2").child(String.format("%08d", list.get(finalI))).get().addOnCompleteListener(task1 -> {
                                HashMap<String, String> map = (HashMap<String, String>) task1.getResult().getValue();
                                //finding no. of booking
                                Integer no_of_bookings = map.size();
                                myRef.child("database2").child(String.format("%08d", list.get(finalI))).child("booking"+no_of_bookings).setValue(user);
                                myRef.child("database2").child(String.format("%08d", list.get(finalI))).child("booking"+no_of_bookings).child("no_of_members").setValue(number);
                                myRef.child("database2").child(String.format("%08d", list.get(finalI))).child("booking"+no_of_bookings).child("details").setValue(mpeoplesDetails);
                                myRef.child("database2").child(String.format("%08d", list.get(finalI))).child("vacancies").setValue(old.get() - number);
                                Toast.makeText(this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                            });

                        });
                    }
                }
            } else {
                Toast.makeText(this, "Please enter all the details..", Toast.LENGTH_SHORT).show();
            }
        });


    }
}









