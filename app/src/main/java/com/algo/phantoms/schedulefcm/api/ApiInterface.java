package com.algo.phantoms.schedulefcm.api;

import com.algo.phantoms.schedulefcm.model.RequestNotificaton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({"Authorization: key=AAAAaMKedns:APA91bHAmHCNAjCDJaVv7193WWLpn2jrC0m0qekn1EitSISZy8ZZkVEiA4ZBcgQiOmqChrjrKSsfkCa3lnOExO91kv1dP28FKUj81Q8-eEBDF9J2T4ddvvBSHqLOy4cgWTS92_Am1uzm",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendChatNotification(@Body RequestNotificaton requestNotificaton);

}
