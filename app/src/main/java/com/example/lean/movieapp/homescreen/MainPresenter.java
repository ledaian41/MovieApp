package com.example.lean.movieapp.homescreen;

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

    }
}
