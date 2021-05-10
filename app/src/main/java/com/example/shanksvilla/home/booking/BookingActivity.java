package com.example.shanksvilla.home.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class BookingActivity extends AppCompatActivity {
    private static final String TAG = "BookingActivity";
    private Button btnSearch;
    private CalendarView calender;
    private TextView dateFrom;
    private TextView dateTo;
    private int startDate, endDate;
    private TextView displayText;
    public int counter = 0;
    public int x = 0, y = 0, z = 0;

    private TextView peoples;
    private Button btnClear;

    //added to solve the issue with staring date less than current date
    private SimpleDateFormat dateFormat;
    private String CurrentDate;
    private TextView days;


    //Integer array for selecting the number of seats
    String[] persons = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        spinner = findViewById(R.id.spinner);

        //Array adapter to bind array to spinner
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, persons);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                peoples.setText(persons[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch = findViewById(R.id.buttonSet);
        calender = findViewById(R.id.calendarView);
        dateFrom = findViewById(R.id.datefrom);
        dateTo = findViewById(R.id.dateto);
        displayText = findViewById(R.id.textDisplay);
        peoples = findViewById(R.id.peoples);

        displayText.setText("Select the starting date");


        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        CurrentDate = dateFormat.format(calender.getDate());
        Log.d(TAG, "onCreate: date" + CurrentDate);
//        Log.d(TAG, "onCreate: getDate" + calender.getDate());

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // i2 -> day
                // i1 -> month - 1
                // i -> year

                String CurrentDay = CurrentDate.substring(0, 2);
                int intCurrentDay = Integer.valueOf(CurrentDay);

                if (counter == 0) {
                    if (i2 < intCurrentDay) {
                        //if the date selected is less then the todays date
                        Toast.makeText(BookingActivity.this, "Staring date should not be less the Today's Date", Toast.LENGTH_SHORT).show();
                    } else {
                        dateFrom.setText(i2 + "-" + (i1 + 1) + "-" + i);
                        startDate = setDate(i2, (i1 + 1), i, startDate);
                        x = i2;
                        y = i1;
                        z = i;
                        counter++;
                        displayText.setText("Now select the end date");
                    }

                } else {
//                    Log.d(TAG, "onSelectedDayChange: "+x+y+z);
                    if (i2 >= x && i1 >= y && i >= z) {
                        dateTo.setText(i2 + "-" + (i1 + 1) + "-" + i);
                        endDate = setDate(i2, (i1 + 1), i, endDate);
                        displayText.setText("Select no. of people");
                    } else {
                        if (i1 > y) {
                            dateTo.setText(i2 + "-" + (i1 + 1) + "-" + i);
                            endDate = setDate(i2, (i1 + 1), i, endDate);
                            displayText.setText("Select no. of people");
                        } else {
                            if (i > z) {
                                dateTo.setText(i2 + "-" + (i1 + 1) + "-" + i);
                                endDate = setDate(i2, (i1 + 1), i, endDate);
                                displayText.setText("Select no. of people");
                            } else {
                                Toast.makeText(BookingActivity.this, "End date can't be before start date", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        btnClear = findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(v -> {
            counter = 0;
            x = 0;
            y = 0;
            z = 0;
            displayText.setText("Select the starting date");
            dateFrom.setText("From");
            dateTo.setText("To");
            Toast.makeText(BookingActivity.this, "Cleared date", Toast.LENGTH_SHORT).show();

        });

        btnSearch.setOnClickListener(view -> {
            Intent intent = new Intent(BookingActivity.this, BookingActivity2.class);
            intent.putExtra("startDate", startDate);
            intent.putExtra("endDate", endDate);
            intent.putExtra("count", Integer.valueOf(peoples.getText().toString()));
            intent.putExtra("days", Integer.valueOf(days.getText().toString()));
            startActivity(intent);
            finish();
        });


    }

    public int setDate(int d, int m, int y, int date) {
        //hashing method...
        DecimalFormat formatter = new DecimalFormat("00");
        String mnew = formatter.format(m);
        date = (y * 10000) + (Integer.valueOf(mnew) * 100) + d;
        Log.d(TAG, "setDate: " + date);
        return date;
    }
}
