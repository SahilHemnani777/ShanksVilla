package com.example.shanksvilla.home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.shanksvilla.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DescriptionFragment extends Fragment {

    private TextView tvName;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth= FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        View v=  inflater.inflate(R.layout.fragment_description, container, false);
        tvName= v.findViewById(R.id.greetUserTextView);

        tvName.setText("Hello "+currentUser.getDisplayName());

        return v;
    }
}