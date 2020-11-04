package com.example.smartsafari;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoutingRepository {
    private static RoutingRepository instance;

    private RoutingService routingService;

    public static RoutingRepository getInstance() {
        if (instance == null) {
            instance = new RoutingRepository();
        }
        return instance;
    }

    public RoutingRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:5000")
//                .baseUrl("http://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        routingService = retrofit.create(RoutingService.class);
    }
    public RoutingService getRoutingService() {return routingService;}
}
