package com.eurotech.samik.guesscapital.DataBase;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Created by samik on 09.11.2016.
 */

public class Retrofit {
    private static final String ENDPOINT = "http://restcountries.eu/rest";
    private static ApiReference apiReference;

    static {
        initialize();
    }

    static void initialize() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        apiReference = restAdapter.create(ApiReference.class);
    }
    interface ApiReference {
        @GET("/v1/all")
        void getContries(Callback<List<Country>> callback);
    }

    public static void getContries(Callback<List<Country>> callback) {
        apiReference.getContries(callback);
    }

}
