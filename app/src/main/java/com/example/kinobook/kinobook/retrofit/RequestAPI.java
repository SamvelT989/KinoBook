package com.example.kinobook.kinobook.retrofit;

import com.example.kinobook.kinobook.model.Docs;
import com.example.kinobook.kinobook.model.ModelDataCinema;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface RequestAPI {

    @Headers("Content-Type: application/json")
    @GET("/movie")
    Observable<ModelDataCinema> getDataCinema(@Query("token") String token, @Query("search") String search, @Query("field") String field);

    @Headers("Content-Type: application/json")
    @GET("/movie")
    Observable<Docs> getDataCinemaMonth(@Query("token") String token, @Query("search") String search, @Query("field") String field, @Query("limit") String limit);
}
