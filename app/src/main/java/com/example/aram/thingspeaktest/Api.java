package com.example.aram.thingspeaktest;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("/update")
    Call<Integer> saveData(@Query("api_key") String write_api_key, @Query("field1") String data);


    @GET("/channels/{channel}/feeds.json")
    Call<ResultSchema> gateData(@Path("channel") String channel,@Query("api_key") String write_api_key, @Query("results") int result);
}
