package com.example.lean.movieapp.api;

import android.graphics.Movie;

import com.example.lean.movieapp.model_server.response.MovieRespone;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface API {
    @GET("movie/top_rated")
    Observable<List<MovieRespone>> getTopRatedMovies(@Query("page") int page);

    @GET("movie/popular")
    Observable<List<MovieRespone>> getPopularMovies(@Query("page") int page);

    @GET("search")
    Observable<List<MovieRespone>> getSearchResult(@QueryMap Map<String, String> map);

}
