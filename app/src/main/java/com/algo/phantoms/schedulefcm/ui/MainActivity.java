package com.algo.phantoms.schedulefcm.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.algo.phantoms.schedulefcm.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonSubscribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Subscribing to offers");

                FirebaseMessaging.getInstance()
                        .subscribeToTopic("offers")
                        .addOnCompleteListener(task -> {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Subscribed! You will get all offers notifications",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            if (!task.isSuccessful()) {
                                Toast.makeText(
                                        MainActivity.this,
                                        "Failed! Try again.",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

            }
        });
    }
}