package com.example.smartsafari;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("/routes")
    Call<List<routes>> getRoutes();

    @POST("/routes")
    Call<Locations> postLocations(@Body Locations locations);
}
