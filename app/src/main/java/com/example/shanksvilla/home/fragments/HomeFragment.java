package com.example.shanksvilla.home.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.shanksvilla.R;
import com.example.shanksvilla.home.booking.BookingActivity;
import com.example.shanksvilla.signin.GoogleSignInActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {

    private Button locationButton, dateButton, viewDetailsButton;
    private FirebaseAuth mAuth;
    private Snackbar snackBar;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference docRef = firebaseFirestore.collection("details").document("info");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        locationButton = v.findViewById(R.id.locationbtn);
        dateButton = v.findViewById(R.id.datesbtn);
        viewDetailsButton = v.findViewById(R.id.viewDetailsButton);
        mAuth = FirebaseAuth.getInstance();
        //setting up the snack bar for the login
        snackBar = Snackbar.make(v.findViewById(R.id.fragment_home_layout), "Please login for the action", BaseTransientBottomBar.LENGTH_SHORT);
        snackBar.setAction("Login", new LoginListener());

        dateButton.setOnClickListener(v13 -> {
            if (mAuth.getCurrentUser() != null) {
                startActivity(new Intent(getActivity(), BookingActivity.class));
            } else {
                snackBar.show();
            }
        });

        locationButton.setOnClickListener(v12 -> new AlertDialog.Builder(getActivity())
                .setTitle("Locations")
                .setMessage("1. Kihim Beach\n2. Pune")
                .setCancelable(true).show());

        viewDetailsButton.setOnClickListener(v1 -> {
            docRef.get().addOnCompleteListener(z->{

                new AlertDialog.Builder(getActivity())
                        .setTitle("ShanksVilla")
                        .setMessage(z.getResult().get("tagline1").toString()+"\n"
                                        +z.getResult().get("tagline2").toString()+"\n"+
                                "News\n"+z.getResult().get("news").toString()+
                                "\nPrice per person: "+z.getResult().get("price").toString()
                                )
                        .setCancelable(true).show();
            });

        });

        return v;
    }
    //class for the listener on the snackBar
    class LoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent= new Intent(getActivity(), GoogleSignInActivity.class);
            startActivity(intent);
        }
    }
}