package com.example.lean.movieapp.homescreen;

import com.example.lean.movieapp.api.APIManager;
import com.example.lean.movieapp.model_server.response.DataResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainInterface.presenter {

    private MainInterface.View view;

    public MainPresenter(MainInterface.View view) {
        this.view = view;
    }

    @Override
    public void getTopRated(int page) {

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
}
