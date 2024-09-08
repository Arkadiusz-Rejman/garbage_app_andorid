package com.example.myapplication.Interface;

import com.example.myapplication.entity.Client;
import com.example.myapplication.entity.Sensor;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/clients/login")
    Call<ResponseBody> login(@Body Client client);

    @POST("/api/clients/register")
    Call<Void> registerClient(@Body Client client);

    @POST("/api/clients/addSensor")
    Call<Void> addSensorToClient(String sensorId);

    @GET("/sensors/{id}")
    Call<Sensor> getSensorById(@Path("id")String sensorId);
}
