package com.example.shanksvilla.home.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shanksvilla.R;
import com.example.shanksvilla.adapters.CardAdapter;

import java.util.ArrayList;

public class LocationSelector extends AppCompatActivity {
    private static final String TAG = "LocationSelector";
    private RecyclerView cardView;
    private CardAdapter cardAdapter;

    private Button buttonK , buttonP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);
        ArrayList<String> names= new ArrayList<>();
        names.add("Kihim Beach");
        names.add("Pune");
        ArrayList<Boolean> isAvailable= new ArrayList<>();
        Intent intent = getIntent();
        if(intent==null){
            Log.d(TAG, "onCreate: intent is null");
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null){
            Log.d(TAG, "onCreate: bundle is null");
        }
        isAvailable.add(bundle.getString("database1").equals("found"));
        isAvailable.add(bundle.getString("database2").equals("found"));
        Log.d(TAG, "onCreate: peoples= " + bundle.getInt("people"));
        Log.d(TAG, "onCreate: dates=" + bundle.get("dates").toString());

        cardView= findViewById(R.id.recyclerCardView);
        cardView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(getLayoutInflater(), names, isAvailable);
        cardView.setAdapter(cardAdapter);

        buttonK= findViewById(R.id.buttonK);
        buttonP= findViewById(R.id.buttonP);

         if(!bundle.getString("database1").equals("found"))buttonK.setVisibility(View.GONE);
         if(!bundle.getString("database2").equals("found"))buttonP.setVisibility(View.GONE);

         buttonK.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(LocationSelector.this, DetailsActivity.class);
                 intent.putExtra("location", "kihim");
                 intent.putExtra("people", bundle.getInt("people"));
                 intent.putExtra("dates", bundle.getIntegerArrayList("dates"));
                 startActivity(intent);
                 finish();
             }
         });

         buttonP.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(LocationSelector.this, DetailsActivity.class);
                 intent.putExtra("location", "pune");
                 intent.putExtra("people", bundle.getInt("people"));
                 intent.putExtra("dates",bundle.getIntegerArrayList("dates"));
                 startActivity(intent);
                 finish();
             }
         });

    }
}