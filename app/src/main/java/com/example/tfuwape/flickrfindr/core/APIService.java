package com.example.tfuwape.flickrfindr.core;

import com.example.tfuwape.flickrfindr.models.containers.PhotoSearchContainer;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * API Service
 */
public interface APIService {

    @POST("?method=flickr.photos.search")
    Call<PhotoSearchContainer> searchTerm(final @QueryMap Map<String, String> params);

}
