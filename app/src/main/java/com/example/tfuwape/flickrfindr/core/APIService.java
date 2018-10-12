package com.example.tfuwape.flickrfindr.core;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * API Service
 */
public interface APIService {

    @POST("v1/login/")
    Call<Object> search(@QueryMap Map<String, String> params);

}
