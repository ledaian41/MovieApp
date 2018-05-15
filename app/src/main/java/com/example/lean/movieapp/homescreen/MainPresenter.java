package com.example.lean.movieapp.homescreen;

import android.annotation.SuppressLint;

import com.example.lean.movieapp.api.API;
import com.example.lean.movieapp.api.APIManager;
import com.example.lean.movieapp.model_server.request.SearchRequest;
import com.example.lean.movieapp.model_server.response.DataResponse;
import com.example.lean.movieapp.model_server.response.MovieResponse;
import com.example.lean.movieapp.model_server.response.Trailers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainInterface.presenter {

    private MainInterface.View view;

    MainPresenter(MainInterface.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getTopRated(int page) {
        APIManager.getTopRatedMovies(page)
                .map(DataResponse::getResults)
                .flatMap(Observable::fromIterable)
                .filter(movieResponse -> movieResponse.getVote_average() >= 8.5)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((movieResponses, throwable) -> {
                    if (view != null) {
                        if (movieResponses != null && !movieResponses.isEmpty()) {
                            view.getTopRatedSuccess(movieResponses);
                        } else {
                            view.getTopRatedSuccess(null);
                        }
                    }
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void getPopular(int page) {
        APIManager.getPopularMovies(page)
                .map(DataResponse::getResults)
                .flatMap(Observable::fromIterable)
                .filter(movieResponse -> movieResponse.getVote_average() >= 7)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((movieResponses, throwable) -> {
                    if (view != null) {
                        if (movieResponses != null && !movieResponses.isEmpty()) {
                            view.getPopularSuccess(movieResponses);
                        } else {
                            view.getPopularSuccess(null);
                        }
                    }
                });
    }

    @Override
    public void getSearchResult(SearchRequest searchRequest) {
        APIManager.searchMovie(searchRequest.toQueryMap())
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResponse dataResponse) {
                        if (view != null) {
                            if (dataResponse != null) {
                                if (dataResponse.getResults() != null && !dataResponse.getResults().isEmpty()) {
                                    view.getSearchResultSuccess(dataResponse.getResults());
                                } else {
                                    view.getSearchResultSuccess(null);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.getSearchResultFailed(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void getTrailerMovie(String id) {
        APIManager.getTrailerMovie(id)
                .map(trailers -> trailers.getYoutube().get(0))
                .filter(youtube -> youtube != null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(youtube -> {
                    if (view != null) {
                        view.getTrailerMovieSuccess(youtube);
                    }
                });
    }
}
