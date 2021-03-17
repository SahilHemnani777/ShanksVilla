package com.example.shanksvilla.home.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class BookingActivity2 extends AppCompatActivity {
    private static final String TAG = "BookingActivity2";
    private String startDate;
    private String endDate;
    private int people;
    private int days;
    private ImageView shanksVilla;
    private TextView error_msg;
    private Button btn_back;
    private FrameLayout fl;

    private ArrayList<Integer> extracted_dates = new ArrayList<>();


    private ArrayList<String> is_available = new ArrayList<>(days);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        shanksVilla= findViewById(R.id.shanks_error);
        error_msg= findViewById(R.id.error_text);
        btn_back= findViewById(R.id.btn_go_back);
        fl= findViewById(R.id.frame_not_found);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingActivity2.this, BookingActivity.class));
                finish();
            }
        });



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        startDate = String.format("%08d", reverse(bundle.getInt("startDate")));
        // start date and end date in String format--> ddMMyyyy
        endDate = String.format("%08d",reverse(bundle.getInt("endDate")));
        people = bundle.getInt("count");
        days= bundle.getInt("days");
        Log.d(TAG, "onCreate: "+ startDate+"------------" +endDate );
        extract_date(startDate,endDate);
        ArrayList<Integer> list_to_send = extracted_dates;
//        Log.d(TAG, "onCreate: unsorted" + extracted_dates);
        Collections.sort(extracted_dates);
//        Log.d(TAG, "onCreate: "+ extracted_dates);
        // so we have sorted arry of dates
        //noe purify the string array to a new array
        ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i<extracted_dates.size();i++){
            dates.add(String.format("%08d", extracted_dates.get(i)));
        }
        Log.d(TAG, "onCreate: purified"+ dates);



        myRef.child("database").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> items = snapshot.getChildren().iterator();
//                String keyDate= dates.get(0);
                while (items.hasNext()){
                    DataSnapshot date = items.next();
                    for (int i =0; i<dates.size();i++){
                        if(dates.get(i).equals(date.getKey().toString()) &&
                                Integer.valueOf(date.child("vacancies").getValue().toString())>=people){
                            dates.remove(dates.get(i));
                        }
                    }
                }
                if (dates.isEmpty()){
                    Toast.makeText(BookingActivity2.this, "Found", Toast.LENGTH_LONG).show();
                    btn_back.setVisibility(View.GONE);
                    shanksVilla.setVisibility(View.GONE);
                    error_msg.setVisibility(View.GONE);
                    fl.setVisibility(View.GONE);
                    Intent intent = new Intent(BookingActivity2.this, LocationSelector.class);
                    intent.putExtra("list", list_to_send);
                    intent.putExtra("number", people);
//                    Log.d(TAG, "onDataChange: "+ list_to_send + "bhej raha hu");
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(BookingActivity2.this, "Not Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });





    }

    private int reverse(int date) {  //20210318

        String x = String.valueOf(date);
        String year = x.substring(0, 4);
        String month = x.substring(4, 6);
        String day = x.substring(6);

        String newDate = day + month + year;
        return Integer.valueOf(newDate);

    }




    private void extract_date(String startDate, String endDate){


        //extraction of date, month from the date:
        String startDay = String.format("%02d",Integer.valueOf(startDate.substring(0,2)));
        String  startMonth = String.format("%02d",Integer.valueOf(startDate.substring(2,4)));

        Log.d(TAG, "extract_date: start" +startDay+"-"+startMonth );

        String endDay = String.format("%02d",Integer.valueOf(endDate.substring(0,2)));
        String  endMonth=String.format("%02d",Integer.valueOf(endDate.substring(2,4)));
        Log.d(TAG, "extract_date: start" +endDay+"-"+endMonth );

        if (startDate.equals(endDate)){
            //add the start date in the list;
            Log.d(TAG, "extract_date: dates are identical");
            extracted_dates.add(Integer.valueOf(startDate));
        }if (startMonth.equals(endMonth)){
            Log.d(TAG, "extract_date: months are equal");
            //same month
            int tempStartDate= Integer.valueOf(startDate);
//            Log.d(TAG, "extract_date: "+ tempStartDate + " yaha se shuru kiya hiai bhai ");
            //adding the starting date
            extracted_dates.add(Integer.valueOf(startDate));
            int daysz= (Integer.valueOf(endDate)-Integer.valueOf(startDate))/1000000+1;
            for(int i = 1 ; i<daysz; i++){
                tempStartDate+=1000000;
                extracted_dates.add((tempStartDate));
            }
        }else{
            //diff month
            Log.d(TAG, "extract_date: different month");
           if (Integer.valueOf(startMonth)==1||Integer.valueOf(startMonth)==3||Integer.valueOf(startMonth)==5||
                   Integer.valueOf(startMonth)==7||Integer.valueOf(startMonth)==8||Integer.valueOf(startMonth)==10||
                   Integer.valueOf(startMonth)==12){
               Log.d(TAG, "extract_date: 31 ka mahina");
               //months with 31 days
               extracted_dates.add(Integer.valueOf(startDate));
               int tempStartdate =Integer.valueOf(startDate);
               int tempEndDate = Integer.valueOf(endDate);
               for(int i = 0; i<(31-Integer.valueOf(startDay));i++){
                   tempStartdate+=1000000;
                   extracted_dates.add(tempStartdate);
                   Log.d(TAG, "extract_date: pehle for loop" + tempStartdate);
               }
               extracted_dates.add(Integer.valueOf(endDate));
               for (int i =1 ; i<Integer.valueOf(endDay);i++){
                   tempEndDate-=1000000;
                   extracted_dates.add(tempEndDate);
                   Log.d(TAG, "extract_date: doosra for loop" + tempEndDate);

               }
           }else if(Integer.valueOf(startMonth)==2){
                //months with 28 days
                Log.d(TAG, "extract_date: 28 ka mahina");
                extracted_dates.add(Integer.valueOf(startDate));
                int tempStartdate =Integer.valueOf(startDate);
                int tempEndDate = Integer.valueOf(endDate);
                for(int i = 0; i<(28-Integer.valueOf(startDay));i++){
                    tempStartdate+=1000000;
                    extracted_dates.add(tempStartdate);
                }
                extracted_dates.add(Integer.valueOf(endDate));
                for (int i =1 ; i<Integer.valueOf(endDay);i++){
                    tempEndDate-=1000000;
                    extracted_dates.add(tempEndDate);
                }
           }else{
               //months with 30 days
                Log.d(TAG, "extract_date: 30 ka mahina");
                extracted_dates.add(Integer.valueOf(startDate));
                int tempStartdate =Integer.valueOf(startDate);
                int tempEndDate = Integer.valueOf(endDate);
                for(int i = 0; i<(30- Integer.valueOf(startDay));i++){
                    tempStartdate+=1000000;
                    extracted_dates.add(tempStartdate);
                }
                extracted_dates.add(Integer.valueOf(endDate));
                for (int i =1 ; i<Integer.valueOf(endDay);i++){
                    tempEndDate-=1000000;
                    extracted_dates.add(tempEndDate);
                }
            }
        }
    }











}