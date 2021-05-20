package com.example.shanksvilla.home.booking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.example.shanksvilla.model.booking;
import com.example.shanksvilla.model.dbRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DetailsActivity extends AppCompatActivity implements PaymentResultListener {

    private FirebaseAuth myAuth;
    private EditText name, age, phone, peoplesDetails;
    private Button btnDone;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private static final String TAG = "DetailsActivity";
//    Checkout checkout;


    /*
    Firestore works here
     */
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Checkout.preload(getApplicationContext());

//        checkout.setKeyID("rzp_test_HXSpml2T1yVSld");

        name = findViewById(R.id.name_details);
        age = findViewById(R.id.age_details);
        phone = findViewById(R.id.contact_details);
        peoplesDetails = findViewById(R.id.peoples_detail);
        btnDone = findViewById(R.id.done_details);


        btnDone.setOnClickListener(v -> {
            if (!name.getText().toString().equals("") && !age.getText().toString().equals("") && !phone.getText().toString().equals("") && !peoplesDetails.getText().toString().equals("")) {
                if (!(phone.getText().toString().length() == 10)) {
                    Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onCreate: Razor Pay");
                    startPayment();
                }
            } else {
                Toast.makeText(this, "Please enter all the details..", Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    public void onPaymentSuccess(String s) {
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = myAuth.getCurrentUser();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Integer number = bundle.getInt("people");
        ArrayList<Integer> list = (ArrayList<Integer>) bundle.get("dates");
        Log.d(TAG, "onCreate: " + list + "aa gayi");
        Integer startDate = list.get(0);
        Integer endDate = list.get(list.size() - 1);


        assert currentUser != null;
        String UID = currentUser.getUid();
        String mName = name.getText().toString();
        String mAge = age.getText().toString();
        String mPhone = phone.getText().toString();
        String mpeoplesDetails = peoplesDetails.getText().toString();


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
                        myRef.child("database1").child(String.format("%08d", list.get(finalI))).child("booking" + no_of_bookings).setValue(user);
                        myRef.child("database1").child(String.format("%08d", list.get(finalI))).child("booking" + no_of_bookings).child("no_of_members").setValue(number);
                        myRef.child("database1").child(String.format("%08d", list.get(finalI))).child("booking" + no_of_bookings).child("details").setValue(mpeoplesDetails);
                        myRef.child("database1").child(String.format("%08d", list.get(finalI))).child("vacancies").setValue(old.get() - number);

                        //saving the bookings globally for admins with some booking ID (serialWise)
                        booking booking = new booking(mName + "|" + mPhone + "|" + currentUser.getUid(), startDate.toString(), endDate.toString(), currentUser.getUid(),
                                mName, mAge, mPhone, currentUser.getEmail(), 0, mpeoplesDetails, "kihim");
                        firebaseFirestore.collection("bookings-kihim").document(mName + "|" + mPhone + "|" + currentUser.getUid()).set(booking);

                        firebaseFirestore.collection("bookings-global").document(mName + "|" + mPhone + "|" + currentUser.getUid()).set(booking);


                        firebaseFirestore.collection("users").document(currentUser.getUid()).collection("bookings").document(mName + "|" + mPhone + "|" + currentUser.getUid()).set(booking);

                        Toast.makeText(this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                        finish();
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
                        myRef.child("database2").child(String.format("%08d", list.get(finalI))).child("booking" + no_of_bookings).setValue(user);
                        myRef.child("database2").child(String.format("%08d", list.get(finalI))).child("booking" + no_of_bookings).child("no_of_members").setValue(number);
                        myRef.child("database2").child(String.format("%08d", list.get(finalI))).child("booking" + no_of_bookings).child("details").setValue(mpeoplesDetails);
                        myRef.child("database2").child(String.format("%08d", list.get(finalI))).child("vacancies").setValue(old.get() - number);

                        //saving the bookings globally for admins with some booking ID (serialWise)
                        booking booking = new booking(mName + "|" + mPhone + "|" + currentUser.getUid(), startDate.toString(), endDate.toString(), currentUser.getUid(),
                                mName, mAge, mPhone, currentUser.getEmail(), 0, mpeoplesDetails, "pune");
                        firebaseFirestore.collection("bookings-pune").document(mName + "|" + mPhone + "|" + currentUser.getUid()).set(booking);

                        firebaseFirestore.collection("bookings-global").document(mName + "|" + mPhone + "|" + currentUser.getUid()).set(booking);


                        firebaseFirestore.collection("users").document(currentUser.getUid()).collection("bookings").document(mName + "|" + mPhone + "|" + currentUser.getUid()).set(booking);

                        Toast.makeText(this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                        finish();
                    });

                });
            }
        }

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to: " + s, Toast.LENGTH_SHORT).show();
    }


    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_HXSpml2T1yVSld");

        /**
         * Set your logo here
         */
//        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "ECGIT pvt. ltd.");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", "9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }
}









