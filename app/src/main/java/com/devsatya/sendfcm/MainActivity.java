package com.devsatya.sendfcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    Button sendNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendNot = findViewById(R.id.send_noti);
        sendNot.setOnClickListener(v -> {
            startActivity(new Intent(this, SendNotification.class));
        });

        // Get the FCM token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("FCM Token", "Fetching FCM token failed", task.getException());
                return;
            }

            // Get the token
            String token = task.getResult();
            Log.d("FCM Token", token);
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}