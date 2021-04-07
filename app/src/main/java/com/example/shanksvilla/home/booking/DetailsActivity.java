package com.example.shanksvilla.home.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.example.shanksvilla.model.dbRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private EditText name, age, phone;
    private Button btnDone;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private static final String TAG = "DetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser= myAuth.getCurrentUser();
        name= findViewById(R.id.name_details);
        age= findViewById(R.id.age_details);
        phone= findViewById(R.id.contact_details);
        btnDone= findViewById(R.id.done_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Integer number = bundle.getInt("people");
        ArrayList<Integer> list = (ArrayList<Integer>) bundle.get("dates");
        Log.d(TAG, "onCreate: "+ list+ "aa gayi");
        if (bundle.getString("location").equals("kihim")){
            btnDone.setOnClickListener(v -> {
                assert currentUser != null;
                String UID = currentUser.getUid();
                String mName= name.getText().toString();
                String mAge= age.getText().toString();
                String mPhone= phone.getText().toString();
                dbRef user = new dbRef(mName, Integer.parseInt(mAge), 0, mPhone, UID);
                for (int i = 0; i<list.size(); i++){
                    Log.d(TAG, "onClick: "+String.valueOf(list.get(i)));
                    myRef.child("database1").child(String.valueOf(list.get(i))).child("bookings").setValue(user);
                    myRef.child("database1").child(String.valueOf(list.get(i))).child("bookings").child("no_of_members").setValue(number);
//                    myRef.child("database").child("vacancies")
                }

            });
        }else{
            btnDone.setOnClickListener(v -> {
                assert currentUser != null;
                String UID = currentUser.getUid();
                String mName= name.getText().toString();
                String mAge= age.getText().toString();
                String mPhone= phone.getText().toString();
                dbRef user = new dbRef(mName, Integer.parseInt(mAge), 0, mPhone, UID);
                for (int i = 0; i<list.size(); i++){
                    Log.d(TAG, "onClick: "+String.valueOf(list.get(i)));
                    myRef.child("database2").child(String.valueOf(list.get(i))).child("bookings").setValue(user);
                    myRef.child("database2").child(String.valueOf(list.get(i))).child("bookings").child("no_of_members").setValue(number);
//                    myRef.child("database").child("vacancies")
                }

            });
        }



    }
}