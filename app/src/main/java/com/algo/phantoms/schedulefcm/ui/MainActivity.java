package com.algo.phantoms.schedulefcm.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.algo.phantoms.schedulefcm.R;
import com.algo.phantoms.schedulefcm.api.ApiClient;
import com.algo.phantoms.schedulefcm.api.ApiInterface;
import com.algo.phantoms.schedulefcm.fcm.MyFirebaseMessagingService;
import com.algo.phantoms.schedulefcm.model.RequestNotificaton;
import com.algo.phantoms.schedulefcm.model.SendNotificationModel;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonSubscribe).setOnClickListener(v -> {
            Log.d(TAG, "Subscribing to offers");


            sendNotificationToPatner(MyFirebaseMessagingService.getToken(this));

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

        });
    }

    private void sendNotificationToPatner(String token) {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SendNotificationModel sendNotificationModel = new SendNotificationModel("check", "i miss you");
                RequestNotificaton requestNotificaton = new RequestNotificaton();
                requestNotificaton.setSendNotificationModel(sendNotificationModel);
                //token is id , whom you want to send notification ,
                Log.e(TAG, token);
                requestNotificaton.setToken(token);

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendChatNotification(requestNotificaton);

                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        Log.d("kkkk","done "+response);
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        Log.d("kkkk","failure");
                    }
                });
            }
        },2000L);
    }

}