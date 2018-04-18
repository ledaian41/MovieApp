package com.example.lean.movieapp.api;

import android.support.annotation.NonNull;

import com.example.lean.movieapp.BuildConfig;
import com.example.lean.movieapp.model_server.response.DataResponse;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";

    private static Retrofit getAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(setAPIKey())
                .build();
    }

    private static OkHttpClient setAPIKey() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl httpUrl = request.url().newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .build();
                    request = request.newBuilder().url(httpUrl).build();
                    return chain.proceed(request);
                }).build();
    }

    public static Observable<DataResponse> getPopularMovies(int page) {
        return getAPI().create(API.class).getPopularMovies(page);
    }

    public static Observable<DataResponse> getTopRatedMovies(int page) {
        return getAPI().create(API.class).getTopRatedMovies(page);
    }
}
