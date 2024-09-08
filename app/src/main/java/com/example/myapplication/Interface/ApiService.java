package com.example.myapplication.Interface;

import com.example.myapplication.entity.Client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/clients/login")
    Call<ResponseBody> login(@Body Client client);

    @POST("/api/clients/register")
    Call<Void> registerClient(@Body Client client);
}
