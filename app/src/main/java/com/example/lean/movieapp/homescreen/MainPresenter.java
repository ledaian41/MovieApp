package com.example.lean.movieapp.homescreen;

import com.example.lean.movieapp.api.API;
import com.example.lean.movieapp.api.APIManager;
import com.example.lean.movieapp.model_server.request.SearchRequest;
import com.example.lean.movieapp.model_server.response.DataResponse;
import com.example.lean.movieapp.model_server.response.MovieResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainInterface.presenter {

    private MainInterface.View view;

    MainPresenter(MainInterface.View view) {
        this.view = view;
    }

    @Override
    public void getTopRated(int page) {
        APIManager.getTopRatedMovies(page)
                .map(DataResponse::getResults)
                .flatMap(new Function<List<MovieResponse>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<MovieResponse> movieResponses) throws Exception {
                        return Observable.fromArray(movieResponses);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void getPopular(int page) {
        APIManager.getPopularMovies(page)
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
                                    view.getPopularSuccess(dataResponse.getResults());
                                } else {
                                    view.getPopularSuccess(null);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.getPopularFailed(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getSearchResult(SearchRequest searchRequest) {
        APIManager.searchMovie(searchRequest.toQueryMap())
                .debounce(300, TimeUnit.MILLISECONDS)
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
                                    view.getPopularSuccess(dataResponse.getResults());
                                } else {
                                    view.getPopularSuccess(null);
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
}
