package com.example.shanksvilla.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;

import java.text.DecimalFormat;

public class BookingActivity extends AppCompatActivity {
    private static final String TAG = "BookingActivity";
    private Button btnSet;
    private CalendarView calender;
    private TextView dateFrom;
    private TextView dateTo;
    private int startDate, endDate;
    private TextView displayText;
    public int counter=0;
    public         int x=0,y=0,z=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btnSet= findViewById(R.id.buttonSet);
        calender= findViewById(R.id.calendarView);
        dateFrom= findViewById(R.id.datefrom);
        dateTo= findViewById(R.id.dateto);
        displayText= findViewById(R.id.textDisplay);

        displayText.setText("Select the starting date");

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // i2 -> day
                // i1 -> month - 1
                // i -> year
                if (counter==0){

//                    Log.d(TAG, "onSelectedDayChange: date"+ i2 +"-"+ (i1+1) +"-"+ i);
                    dateFrom.setText(i2 +"-"+ (i1+1) +"-"+ i);
                    setDate(i2, (i1+1), i, startDate);
                    x=i2;
                    y=i1;
                    z=i;
                    counter++;
                    displayText.setText("Now select the end date");
                }else{
//                    Log.d(TAG, "onSelectedDayChange: "+x+y+z);
                    if (i2>x && i1 >=y && i >=z){
                        dateTo.setText(i2 +"-"+ (i1+1) +"-"+ i);
                        setDate(i2, (i1+1), i, endDate);
                        displayText.setText("Select no. of people");
                    }else{
                        Toast.makeText(BookingActivity.this, "End date can't be before start date", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    public void setDate(int d, int m, int y, int date){
        //hashing method...
        DecimalFormat formatter = new DecimalFormat("00");
//        String dnew = formatter.format(d);
        String mnew = formatter.format(m);
        date= (y*10000) + (Integer.valueOf(mnew) * 100) + d;
        Log.d(TAG, "setDate: "+ date);
    }
}


//        dateFrom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startDate= calender.getDate();
////                Log.d(TAG, "onClick: "+ getDate(date,"dd/MM/yyyy"));
//
//                dateFrom.setText(getDate(startDate,"dd/MM/yyyy"));
//            }
//        });
//
//        dateTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                endDate=calender.getDate();
//                dateTo.setText(getDate(endDate,"dd/MM/yyyy"));
//            }
//        });


//    public String getDate(long milliSeconds, String dateFormat)
//    {
//        // Create a DateFormatter object for displaying date in specified format.
//        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
//
//        // Create a calendar object that will convert the date and time value in milliseconds to date.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(milliSeconds);
//        return formatter.format(calendar.getTime());
//    }
