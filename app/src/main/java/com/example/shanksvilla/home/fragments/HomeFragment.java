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


public class HomeFragment extends Fragment {

    private Button locationButton, dateButton, viewDetailsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        locationButton=v.findViewById(R.id.locationbtn);
        dateButton= v.findViewById(R.id.datesbtn);
        viewDetailsButton= v.findViewById(R.id.viewDetailsButton);

        dateButton.setOnClickListener(v13 -> startActivity(new Intent(getActivity(), BookingActivity.class)));

        locationButton.setOnClickListener(v12 -> new AlertDialog.Builder(getActivity())
                .setTitle("Locations")
                .setMessage("1. Kihim Beach\n2.Pune")
                .setCancelable(true).show());

        return v;
    }
}