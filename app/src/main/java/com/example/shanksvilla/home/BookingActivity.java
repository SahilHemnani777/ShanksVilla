package com.example.shanksvilla.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;

public class BookingActivity extends AppCompatActivity {
    private static final String TAG = "BookingActivity";
    private Button btnSet;
    private CalendarView calender;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btnSet= findViewById(R.id.buttonSet);
        calender= findViewById(R.id.calendarView);
        date= findViewById(R.id.date);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // i2 -> day
                // i1 -> month - 1
                // i -> year
                Log.d(TAG, "onSelectedDayChange: date"+ i2 +"-"+ (i1+1) +"-"+ i);
                date.setText(i2 +"-"+ (i1+1) +"-"+ i);
            }
        });
    }
}