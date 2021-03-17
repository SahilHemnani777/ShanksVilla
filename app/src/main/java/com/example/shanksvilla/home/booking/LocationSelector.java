package com.example.shanksvilla.home.booking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shanksvilla.R;
import com.example.shanksvilla.adapters.CardAdapter;

import java.util.ArrayList;

public class LocationSelector extends AppCompatActivity {
    private RecyclerView cardView;
    private CardAdapter cardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);
        ArrayList<String> names= new ArrayList<>();
        names.add("Kihim Beach");
        names.add("Pune");
        cardView= findViewById(R.id.recyclerCardView);
        cardView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(getLayoutInflater(), names);
        cardView.setAdapter(cardAdapter);

    }
}