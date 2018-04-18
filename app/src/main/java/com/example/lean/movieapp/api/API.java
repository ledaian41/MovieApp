package com.example.lean.movieapp.api;

import com.example.lean.movieapp.model_server.response.DataResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface API {
    @GET("movie/top_rated")
    Observable<DataResponse> getTopRatedMovies(@Query("page") int page);

    @GET("movie/popular")
    Observable<DataResponse> getPopularMovies(@Query("page") int page);

    @GET("search")
    Observable<DataResponse> getSearchResult(@QueryMap Map<String, String> map);

}
