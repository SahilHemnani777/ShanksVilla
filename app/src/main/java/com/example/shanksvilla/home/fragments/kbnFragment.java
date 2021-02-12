package com.example.shanksvilla.home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.shanksvilla.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

public class kbnFragment extends Fragment {

    private KenBurnsView kbn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_kbn, container, false);
        kbn=v.findViewById(R.id.kbvLocation);
        kbn.setImageResource(R.drawable.logo_new);
        return v;
    }
}