package com.example.shanksvilla.home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.shanksvilla.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.ArrayList;

public class kbnFragment extends Fragment {

    private KenBurnsView kbn;
    private int number;
    private String aChar;

    public kbnFragment(int number) {
        this.number = number;
    }

    ArrayList<Integer> arrayList = new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        arrayList.add(R.drawable.a);
        arrayList.add(R.drawable.b);
        arrayList.add(R.drawable.c);
        arrayList.add(R.drawable.d);
        arrayList.add(R.drawable.e);
        arrayList.add(R.drawable.f);
        arrayList.add(R.drawable.g);
        arrayList.add(R.drawable.h);
        arrayList.add(R.drawable.i);
        arrayList.add(R.drawable.j);
        arrayList.add(R.drawable.k);
        arrayList.add(R.drawable.l);
        arrayList.add(R.drawable.m);
        arrayList.add(R.drawable.n);




        View v = inflater.inflate(R.layout.fragment_kbn, container, false);
        kbn=v.findViewById(R.id.kbvLocation);
        kbn.setImageResource(arrayList.get(number));
        return v;
    }
    private String getCharForNumber(int i) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        if (i > 25) {
            return null;
        }
        return Character.toString(alphabet[i]);
    }
}