package com.railways.ecsoket.services;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.railways.ecsoket.pojo.FailureResponse;
import com.railways.ecsoket.pojo.LoginResponse;
import com.railways.ecsoket.pojo.ReadSocketResponse;
import com.railways.ecsoket.pojo.ReasonsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {


    @Headers("Content-Type:application/json")
    @POST("login.php")
    Call<LoginResponse> Login(
            @Body JsonObject request);

    @Headers("Content-Type:application/json")
    @POST("ecsocket/createoffline.php")
    Call<JsonObject> CreateSocket(
            @Body JsonObject request);

    @Headers("Content-Type:application/json")
    @POST("failures/update.php")
    Call<JsonObject> CreateFailure(
            @Body JsonObject request);

    @Headers("Content-Type:application/json")
    @POST("testreport/createoffline.php")
    Call<JsonObject> CreateTest(
            @Body JsonObject request);


    @Headers("Content-Type:application/json")
    @GET("hierarchy/read_ec.php")
    Call<List<ReadSocketResponse>> GetSockets();

    @Headers("Content-Type:application/json")
    @GET("remarks.php")
    Call<ReasonsResponse> GetReasons();


    @Headers("Content-Type:application/json")
    @POST("failures/read.php")
    Call<FailureResponse> GetFailure(
            @Body JsonObject request);
}
