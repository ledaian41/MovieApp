package com.example.lean.movieapp.homescreen;

import com.example.lean.movieapp.model_server.response.MovieResponse;

import java.util.List;

public class MainInterface {
    interface View {
        void initPresenter();

        void getTopRatedSuccess();

        void getTopRatedFailed(String error);

        void getPopularSuccess(List<MovieResponse> movieResponses);

        void getPopularFailed(String error);
    }

    interface presenter {
        void getTopRated(int page);

        void getPopular(int page);
    }
}
