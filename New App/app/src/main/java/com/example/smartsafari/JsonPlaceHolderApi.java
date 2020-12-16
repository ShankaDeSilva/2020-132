package com.example.smartsafari;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

//      @GET("routes")
//      Call<List<Directions>> getDirections();

    @POST("routes")
    Call<Locations> createLocation(@Body Locations locations);
}
