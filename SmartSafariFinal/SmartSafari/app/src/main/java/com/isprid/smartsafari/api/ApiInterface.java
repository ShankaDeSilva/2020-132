package com.isprid.smartsafari.api;

import com.isprid.smartsafari.api.LocDir;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/routes")
    Call<LocDir> postLocDir(@Body LocDir locDir);
}
