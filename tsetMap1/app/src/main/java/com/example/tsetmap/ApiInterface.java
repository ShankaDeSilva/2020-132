package com.example.tsetmap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

//    @GET("/todos")
//    Call<List<LocDir>> getLocDir();

    @POST("/routes")
    Call<LocDir> postLocDir(@Body LocDir locDir);
}
