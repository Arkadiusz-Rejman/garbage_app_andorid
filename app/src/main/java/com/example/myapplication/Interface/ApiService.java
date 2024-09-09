package com.example.myapplication.Interface;

import com.example.myapplication.entity.Client;
import com.example.myapplication.entity.Disposal;
import com.example.myapplication.entity.Sensor;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/clients/login")
    Call<ResponseBody> login(@Body Client client);

    @POST("/api/clients/register")
    Call<Void> registerClient(@Body Client client);

    @POST("/sensors/addSensorToClient")
    Call<Void> addSensorToClient(@Query("sensorId") String sensorId, @Query("clientLogin") String clientLogin);

    @GET("/sensors/{id}")
    Call<Sensor> getSensorById(@Path("id")String sensorId);

    @GET("/sensors/clientSensors")
    Call<List<Sensor>> getClientSensors(@Query("clientLogin") String clientLogin);

    @POST("/extraDisposals/{sensorId}")
    Call<Void> saveExtraDisposal(@Body Disposal extraDisposal, @Path("sensorId") Long sensorId);

    @GET("/disposals/sensor/{sensorId}")
    Call<List<Disposal>> getDisposalsBySensorId(@Path("sensorId") Long sensorId);
}
