package com.example.shanksvilla.mailing_system;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.shanksvilla.R;

public class MailSenderActivity extends Activity {
    private static final String TAG = "da";
    private Button send;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailtest);

        send =findViewById(R.id.mailSend);
        send.setOnClickListener(v -> {

                GMailSender sender = new GMailSender("sahil@ecgit.com", "internshipwithecgit");
            try {
                sender.sendMail("fakfgafhafhfhsif",
                        "nnsgndsngsdgnogngnsognsgnsg",
                        "sahil@ecgit.com",
                        "hemnanisahil777@gmail.com");
            } catch (Exception e) {
                System.out.println((e.toString()));
                e.printStackTrace();
            }
            Log.d(TAG, "onCreate: sent");


        });


    }
}