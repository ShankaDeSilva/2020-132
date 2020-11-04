package com.example.smartsafari;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RoutingService {
    @POST("routing")
    Call<Routing> createRoute(@Body Routing routing);

    @FormUrlEncoded
    @POST("routing")
    Call<Routing> createRoute(@Field("id") Integer id, @Field("startLat") Float startLat, @Field("startLon") Float startLon, @Field("endLat") Float endLat, @Field("endLon") Float endLon);

    @FormUrlEncoded
    @POST("routing")
    Call<Routing> createRoute(@FieldMap Map<String, Float> fields);
}
