package com.example.lean.movieapp.homescreen;

import com.example.lean.movieapp.model_server.request.SearchRequest;
import com.example.lean.movieapp.model_server.response.MovieResponse;
import com.example.lean.movieapp.model_server.response.Trailers;

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

        void getTrailerMovieSuccess(Trailers.Youtube trailers);
    }

    interface presenter {
        void getTopRated(int page);

        void getPopular(int page);

        void getSearchResult(SearchRequest searchRequest);

        void getTrailerMovie(String id);
    }
}
