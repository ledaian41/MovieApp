package com.example.lean.movieapp.api;

import android.support.annotation.NonNull;

import com.example.lean.movieapp.BuildConfig;
import com.example.lean.movieapp.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    public static Retrofit getAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(setAPIKey())
                .build();
    }

    private static OkHttpClient setAPIKey() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl httpUrl = request.url().newBuilder()
                                .addQueryParameter("api_key", BuildConfig.API_KEY)
                                .build();
                        request = request.newBuilder().url(httpUrl).build();
                        return chain.proceed(request);
                    }
                }).build();
    }
}
