package com.example.lean.movieapp.homescreen;

import com.example.lean.movieapp.model_server.request.SearchRequest;
import com.example.lean.movieapp.model_server.response.MovieResponse;

import java.util.List;

class MainInterface {
    interface View {
        void initPresenter();

        void getTopRatedSuccess(List<MovieResponse> movieResponses);

        void getTopRatedFailed(String error);

        void getPopularSuccess(List<MovieResponse> movieResponses);

        void getPopularFailed(String error);

        void getSearchResultSuccess(List<MovieResponse> movieResponses);

        void getSearchResultFailed(String error);
    }

    interface presenter {
        void getTopRated(int page);

        void getPopular(int page);

        void getSearchResult(SearchRequest searchRequest);
    }
}
